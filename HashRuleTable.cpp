#include "HashRuleTable.h"

HashRuleTable::HashRuleTable(int size) {
    tableSize = size;
    table = new Rule * [tableSize];

    for (int i = 0; i < tableSize; i++) {
        table[i] = nullptr;
    }
}

HashRuleTable::~HashRuleTable() {
    for (int i = 0; i < tableSize; i++) {
        Rule* current = table[i];
        while (current != nullptr) {
            Rule* temp = current;
            current = current->next;
            delete temp;
        }
    }
    delete[] table;
}

void HashRuleTable::copyString(char* dest, const char* src, int maxLen) {
    int i = 0;
    while (src[i] != '\0' && i < maxLen - 1) {
        dest[i] = src[i];
        i++;
    }
    dest[i] = '\0';
}

bool HashRuleTable::compareStrings(const char* str1, const char* str2) {
    int i = 0;
    while (str1[i] != '\0' && str2[i] != '\0') {
        if (str1[i] != str2[i]) {
            return false;
        }
        i++;
    }
    return str1[i] == str2[i];
}

int HashRuleTable::hashFunction(const char* key) {
    int hash = 0;
    int i = 0;

    while (key[i] != '\0') {
        hash = (hash * 31 + key[i]) % tableSize;
        i++;
    }

    return hash < 0 ? -hash : hash;
}

void HashRuleTable::insertRule(const char* key, const char* decision, const char* explanation, int confidence) {
    int index = hashFunction(key);

    Rule* newRule = new Rule();
    copyString(newRule->key, key, 50);
    copyString(newRule->decision, decision, 20);
    copyString(newRule->explanation, explanation, 500);
    newRule->confidence = confidence;
    newRule->next = table[index];

    table[index] = newRule;
}

Rule* HashRuleTable::searchRule(const char* key) {
    int index = hashFunction(key);
    Rule* current = table[index];

    while (current != nullptr) {
        if (compareStrings(current->key, key)) {
            return current;
        }
        current = current->next;
    }

    return nullptr;
}

void HashRuleTable::initializeDefaultRules() {
    insertRule("UPTREND_LOW_RISK_HIGH", "BUY",
        "Strong uptrend with low risk and high risk appetite. Excellent buying opportunity. "
        "Market momentum is positive with minimal volatility.", 90);

    insertRule("UPTREND_LOW_RISK_MED", "BUY",
        "Uptrend with low risk and moderate appetite. Good buying signal. "
        "Stable growth pattern with controlled risk.", 85);

    insertRule("UPTREND_LOW_RISK_LOW", "BUY",
        "Uptrend with low risk. Conservative buy recommendation. "
        "Safe entry point for risk-averse investors.", 75);

    insertRule("UPTREND_MED_RISK_HIGH", "BUY",
        "Uptrend with moderate risk and high appetite. Buy with caution. "
        "Good potential but requires active monitoring.", 80);

    insertRule("UPTREND_MED_RISK_MED", "BUY",
        "Uptrend with moderate risk. Balanced buy opportunity. "
        "Reasonable risk-reward ratio for moderate investors.", 70);

    insertRule("UPTREND_MED_RISK_LOW", "HOLD",
        "Uptrend but moderate risk exceeds conservative appetite. Hold position. "
        "Wait for volatility to decrease.", 65);

    insertRule("UPTREND_HIGH_RISK_HIGH", "HOLD",
        "Uptrend but high volatility. Hold and monitor closely. "
        "Risk level requires careful consideration despite positive trend.", 60);

    insertRule("UPTREND_HIGH_RISK_MED", "HOLD",
        "Uptrend with high risk. Hold current position. "
        "Volatility too high for new entries at moderate risk appetite.", 55);

    insertRule("UPTREND_HIGH_RISK_LOW", "SELL",
        "High risk uptrend incompatible with low appetite. Consider selling. "
        "Volatility exceeds acceptable risk parameters.", 50);

    insertRule("DOWNTREND_LOW_RISK_HIGH", "HOLD",
        "Downtrend with low volatility. Hold and wait for reversal. "
        "Stable decline may present future opportunities.", 55);

    insertRule("DOWNTREND_LOW_RISK_MED", "HOLD",
        "Moderate downtrend. Hold position. "
        "Consider exit strategy if decline continues.", 50);

    insertRule("DOWNTREND_LOW_RISK_LOW", "SELL",
        "Downtrend incompatible with conservative strategy. Sell recommended. "
        "Preserve capital and wait for better opportunities.", 70);

    insertRule("DOWNTREND_MED_RISK_HIGH", "SELL",
        "Downtrend with moderate risk. Sell to limit losses. "
        "Exit before volatility increases further.", 75);

    insertRule("DOWNTREND_MED_RISK_MED", "SELL",
        "Moderate downtrend and risk. Strong sell signal. "
        "Risk-reward ratio unfavorable for holding.", 80);

    insertRule("DOWNTREND_MED_RISK_LOW", "SELL",
        "Downtrend with moderate risk. Immediate sell recommended. "
        "Cut losses and preserve capital.", 85);

    insertRule("DOWNTREND_HIGH_RISK_HIGH", "SELL",
        "Severe downtrend with high volatility. Urgent sell recommendation. "
        "Minimize exposure to prevent significant losses.", 90);

    insertRule("DOWNTREND_HIGH_RISK_MED", "SELL",
        "High risk downtrend. Strong sell signal. "
        "Exit position immediately to protect capital.", 90);

    insertRule("DOWNTREND_HIGH_RISK_LOW", "SELL",
        "Critical downtrend with high volatility. Immediate sell required. "
        "Unacceptable risk level for any investor profile.", 95);

    insertRule("SIDEWAYS_LOW_RISK_HIGH", "HOLD",
        "Sideways trend with low risk. Hold and monitor for breakout. "
        "Accumulation phase may precede significant movement.", 60);

    insertRule("SIDEWAYS_LOW_RISK_MED", "HOLD",
        "Stable sideways movement. Hold position. "
        "Market consolidation phase with low risk.", 65);

    insertRule("SIDEWAYS_LOW_RISK_LOW", "HOLD",
        "Sideways with low volatility. Safe to hold. "
        "Ideal holding pattern for conservative investors.", 70);

    insertRule("SIDEWAYS_MED_RISK_HIGH", "HOLD",
        "Sideways with moderate volatility. Hold with monitoring. "
        "Uncertain direction requires patience.", 55);

    insertRule("SIDEWAYS_MED_RISK_MED", "HOLD",
        "Balanced sideways movement. Hold current position. "
        "Wait for clear trend emergence.", 60);

    insertRule("SIDEWAYS_MED_RISK_LOW", "HOLD",
        "Sideways trend. Conservative hold. "
        "Maintain position until clearer signals emerge.", 65);

    insertRule("SIDEWAYS_HIGH_RISK_HIGH", "SELL",
        "Sideways with high volatility and high appetite. Consider selling. "
        "Unstable consolidation may indicate weakness.", 50);

    insertRule("SIDEWAYS_HIGH_RISK_MED", "SELL",
        "High volatility sideways movement. Sell recommended. "
        "Risk not justified without directional trend.", 60);

    insertRule("SIDEWAYS_HIGH_RISK_LOW", "SELL",
        "Sideways with unacceptable volatility. Sell to reduce risk. "
        "High volatility in consolidation is concerning signal.", 70);
}

void HashRuleTable::printTable() {
    std::cout << "=== Hash Rule Table ===" << std::endl;
    int count = 0;
    for (int i = 0; i < tableSize && count < 5; i++) {
        if (table[i] != nullptr) {
            std::cout << "Index " << i << ":" << std::endl;
            Rule* current = table[i];
            while (current != nullptr && count < 5) {
                std::cout << "  Key: " << current->key << std::endl;
                std::cout << "  Decision: " << current->decision << std::endl;
                std::cout << "  Confidence: " << current->confidence << "%" << std::endl;
                current = current->next;
                count++;
            }
        }
    }
}