#include <jni.h>
#include <iostream>
#include <string>
#include "DataArray.h"
#include "Heap.h"
#include "SMAAnalyzer.h"
#include "GraphTrend.h"
#include "HashRuleTable.h"
#include "AVLDecisionEngine.h"

// Global data storage
static DataArray* globalDataArray = nullptr;
static double globalMaxPrice = 0.0;
static double globalMinPrice = 0.0;
static const char* globalTrend = "UNKNOWN";
static double globalVolatility = 0.0;
static const char* globalRisk = "UNKNOWN";

// Helper function to generate sample data
void generateSampleData(DataArray* arr, const char* symbol, int numRecords) {
    double basePrice = 100.0;

    if (symbol[0] == 'A') basePrice = 150.0;
    else if (symbol[0] == 'G') basePrice = 200.0;
    else if (symbol[0] == 'M') basePrice = 250.0;
    else if (symbol[0] == 'T') basePrice = 180.0;
    else if (symbol[0] == 'E') basePrice = 1.20;
    else if (symbol[0] == 'G' && symbol[1] == 'B') basePrice = 1.30;

    for (int i = 0; i < numRecords; i++) {
        double variation = ((i % 10) - 5) * 0.5;
        double trend = i * 0.1;
        double price = basePrice + trend + variation;
        arr->insert(price);
    }
}

// Helper to build result string
void buildString(char* dest, const char* src, int& pos, int maxLen) {
    int i = 0;
    while (src[i] != '\0' && pos < maxLen - 1) {
        dest[pos++] = src[i++];
    }
}

void appendDouble(char* dest, double value, int& pos, int maxLen) {
    char buffer[50];
    int intPart = (int)value;
    double fracPart = value - intPart;

    int len = 0;
    int temp = intPart;
    if (temp == 0) {
        buffer[len++] = '0';
    }
    else {
        char digits[20];
        int digitCount = 0;
        while (temp > 0) {
            digits[digitCount++] = '0' + (temp % 10);
            temp /= 10;
        }
        for (int i = digitCount - 1; i >= 0; i--) {
            buffer[len++] = digits[i];
        }
    }

    buffer[len++] = '.';

    fracPart *= 100;
    int fracInt = (int)fracPart;
    buffer[len++] = '0' + (fracInt / 10);
    buffer[len++] = '0' + (fracInt % 10);
    buffer[len] = '\0';

    buildString(dest, buffer, pos, maxLen);
}

void appendInt(char* dest, int value, int& pos, int maxLen) {
    char buffer[20];
    int len = 0;

    if (value == 0) {
        buffer[len++] = '0';
    }
    else {
        char digits[20];
        int digitCount = 0;
        while (value > 0) {
            digits[digitCount++] = '0' + (value % 10);
            value /= 10;
        }
        for (int i = digitCount - 1; i >= 0; i--) {
            buffer[len++] = digits[i];
        }
    }
    buffer[len] = '\0';

    buildString(dest, buffer, pos, maxLen);
}

// JNI Implementation with EXACT signatures
extern "C" {

    JNIEXPORT jstring JNICALL Java_integration_JNIHandler_testConnection
    (JNIEnv* env, jobject obj) {
        std::cout << "C++ testConnection called successfully!" << std::endl;
        return env->NewStringUTF("JNI Connection Successful!");
    }

    JNIEXPORT jstring JNICALL Java_integration_JNIHandler_loadDataFromCpp
    (JNIEnv* env, jobject obj, jstring symbol, jstring startDate, jstring endDate, jstring dataType) {

        std::cout << "C++ loadDataFromCpp called!" << std::endl;

        const char* symbolStr = env->GetStringUTFChars(symbol, nullptr);
        const char* startDateStr = env->GetStringUTFChars(startDate, nullptr);
        const char* endDateStr = env->GetStringUTFChars(endDate, nullptr);
        const char* dataTypeStr = env->GetStringUTFChars(dataType, nullptr);

        // Clear previous data
        if (globalDataArray != nullptr) {
            delete globalDataArray;
        }
        globalDataArray = new DataArray(1000);

        // Generate sample data
        int numRecords = 100;
        generateSampleData(globalDataArray, symbolStr, numRecords);

        // Find min and max using Heap
        MinHeap minHeap;
        MaxHeap maxHeap;

        for (int i = 0; i < globalDataArray->getSize(); i++) {
            double value = globalDataArray->get(i);
            minHeap.insert(value);
            maxHeap.insert(value);
        }

        globalMinPrice = minHeap.getMin();
        globalMaxPrice = maxHeap.getMax();

        // Build result string
        char result[2000];
        int pos = 0;

        appendDouble(result, globalMaxPrice, pos, 2000);
        result[pos++] = '|';

        appendDouble(result, globalMinPrice, pos, 2000);
        result[pos++] = '|';

        appendInt(result, globalDataArray->getSize(), pos, 2000);
        result[pos++] = '|';

        buildString(result, "Loaded ", pos, 2000);
        appendInt(result, globalDataArray->getSize(), pos, 2000);
        buildString(result, " records for ", pos, 2000);
        buildString(result, symbolStr, pos, 2000);
        buildString(result, " (", pos, 2000);
        buildString(result, dataTypeStr, pos, 2000);
        buildString(result, ")\\nDate Range: ", pos, 2000);
        buildString(result, startDateStr, pos, 2000);
        buildString(result, " to ", pos, 2000);
        buildString(result, endDateStr, pos, 2000);
        buildString(result, "\\n\\nSample Data (first 5 records):\\n", pos, 2000);

        for (int i = 0; i < 5 && i < globalDataArray->getSize(); i++) {
            buildString(result, "Record ", pos, 2000);
            appendInt(result, i + 1, pos, 2000);
            buildString(result, ": $", pos, 2000);
            appendDouble(result, globalDataArray->get(i), pos, 2000);
            buildString(result, "\\n", pos, 2000);
        }

        result[pos] = '\0';

        env->ReleaseStringUTFChars(symbol, symbolStr);
        env->ReleaseStringUTFChars(startDate, startDateStr);
        env->ReleaseStringUTFChars(endDate, endDateStr);
        env->ReleaseStringUTFChars(dataType, dataTypeStr);

        std::cout << "Data loaded successfully!" << std::endl;

        return env->NewStringUTF(result);
    }

    JNIEXPORT jstring JNICALL Java_integration_JNIHandler_analyzeTrendFromCpp
    (JNIEnv* env, jobject obj, jint windowSize) {

        std::cout << "C++ analyzeTrendFromCpp called with window: " << windowSize << std::endl;

        if (globalDataArray == nullptr || globalDataArray->getSize() == 0) {
            return env->NewStringUTF("Error: No data loaded. Please load data first.");
        }

        // Calculate SMA and Volatility
        SMAAnalyzer smaAnalyzer(globalDataArray->getData(), globalDataArray->getSize(), windowSize);
        double sma = smaAnalyzer.calculateSMA();
        globalVolatility = smaAnalyzer.calculateVolatility();

        // Analyze Trend using Graph
        GraphTrend graph(globalDataArray->getSize());
        graph.buildGraphFromPrices(globalDataArray->getData(), globalDataArray->getSize());
        graph.analyzeTrend(globalDataArray->getData());
        globalTrend = graph.getTrendDirection();
        globalRisk = graph.getRiskLevel(globalVolatility);

        // Build result string
        char result[2000];
        int pos = 0;

        appendDouble(result, sma, pos, 2000);
        result[pos++] = '|';

        buildString(result, globalTrend, pos, 2000);
        result[pos++] = '|';

        appendDouble(result, globalVolatility, pos, 2000);
        result[pos++] = '|';

        buildString(result, globalRisk, pos, 2000);
        result[pos++] = '|';

        buildString(result, "Market Analysis Summary:\\n\\n", pos, 2000);
        buildString(result, "The ", pos, 2000);
        appendInt(result, windowSize, pos, 2000);
        buildString(result, "-day moving average shows a price level of $", pos, 2000);
        appendDouble(result, sma, pos, 2000);
        buildString(result, ".\\n\\nTrend Direction: ", pos, 2000);
        buildString(result, globalTrend, pos, 2000);
        buildString(result, "\\nVolatility Level: ", pos, 2000);
        appendDouble(result, globalVolatility, pos, 2000);
        buildString(result, "%\\nRisk Classification: ", pos, 2000);
        buildString(result, globalRisk, pos, 2000);
        buildString(result, "\\n\\nBased on graph analysis using DFS traversal, the market is showing ", pos, 2000);

        if (globalTrend[0] == 'U') {
            buildString(result, "positive momentum with upward price movement.", pos, 2000);
        }
        else if (globalTrend[0] == 'D') {
            buildString(result, "negative momentum with downward price movement.", pos, 2000);
        }
        else {
            buildString(result, "consolidation with no clear directional bias.", pos, 2000);
        }

        result[pos] = '\0';

        std::cout << "Trend analysis completed!" << std::endl;

        return env->NewStringUTF(result);
    }

    JNIEXPORT jstring JNICALL Java_integration_JNIHandler_getRecommendationFromCpp
    (JNIEnv* env, jobject obj, jint riskAppetite) {

        std::cout << "C++ getRecommendationFromCpp called with risk: " << riskAppetite << std::endl;

        if (globalDataArray == nullptr || globalDataArray->getSize() == 0) {
            return env->NewStringUTF("Error: No data loaded. Please load data and run trend analysis first.");
        }

        // Initialize Hash Table with Rules
        HashRuleTable hashTable(100);
        hashTable.initializeDefaultRules();

        // Build rule key
        char ruleKey[50];
        int pos = 0;
        buildString(ruleKey, globalTrend, pos, 50);
        ruleKey[pos++] = '_';
        buildString(ruleKey, globalRisk, pos, 50);
        ruleKey[pos++] = '_';

        if (riskAppetite >= 7) {
            buildString(ruleKey, "HIGH", pos, 50);
        }
        else if (riskAppetite >= 4) {
            buildString(ruleKey, "MED", pos, 50);
        }
        else {
            buildString(ruleKey, "LOW", pos, 50);
        }
        ruleKey[pos] = '\0';

        // Search for rule
        Rule* matchedRule = hashTable.searchRule(ruleKey);

        if (matchedRule == nullptr) {
            return env->NewStringUTF("HOLD|50%|No specific rule found for current market conditions. Hold position and monitor closely.");
        }

        // Use AVL Tree for decision scoring
        AVLDecisionEngine avlEngine;
        avlEngine.insertDecision(matchedRule->confidence, matchedRule->decision,
            matchedRule->explanation, matchedRule->confidence);

        // Build result string
        char result[1500];
        pos = 0;

        buildString(result, matchedRule->decision, pos, 1500);
        result[pos++] = '|';

        appendInt(result, matchedRule->confidence, pos, 1500);
        result[pos++] = '%';
        result[pos++] = '|';

        buildString(result, "AI Recommendation Analysis:\\n\\n", pos, 1500);
        buildString(result, "Decision: ", pos, 1500);
        buildString(result, matchedRule->decision, pos, 1500);
        buildString(result, "\\nConfidence Level: ", pos, 1500);
        appendInt(result, matchedRule->confidence, pos, 1500);
        buildString(result, "%\\n\\nMarket Conditions:\\n", pos, 1500);
        buildString(result, "- Trend: ", pos, 1500);
        buildString(result, globalTrend, pos, 1500);
        buildString(result, "\\n- Risk: ", pos, 1500);
        buildString(result, globalRisk, pos, 1500);
        buildString(result, "\\n- Volatility: ", pos, 1500);
        appendDouble(result, globalVolatility, pos, 1500);
        buildString(result, "%\\n- Your Risk Appetite: ", pos, 1500);
        appendInt(result, riskAppetite, pos, 1500);
        buildString(result, "/10\\n\\nReasoning:\\n", pos, 1500);
        buildString(result, matchedRule->explanation, pos, 1500);

        result[pos] = '\0';

        std::cout << "Recommendation generated!" << std::endl;

        return env->NewStringUTF(result);
    }

} // extern "C"