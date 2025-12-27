#include "SMAAnalyzer.h"

SMAAnalyzer::SMAAnalyzer(double* priceData, int size, int window) {
    dataSize = size;
    windowSize = window;

    if (windowSize > dataSize) {
        windowSize = dataSize;
    }

    prices = new double[dataSize];
    for (int i = 0; i < dataSize; i++) {
        prices[i] = priceData[i];
    }
}

SMAAnalyzer::~SMAAnalyzer() {
    delete[] prices;
}

double SMAAnalyzer::calculateAverage(int start, int end) {
    if (start < 0 || end > dataSize || start >= end) {
        return 0.0;
    }

    double sum = 0.0;
    int count = 0;

    for (int i = start; i < end; i++) {
        sum += prices[i];
        count++;
    }

    return count > 0 ? sum / count : 0.0;
}

double SMAAnalyzer::calculateSMA() {
    if (dataSize < windowSize) {
        return calculateAverage(0, dataSize);
    }

    int startIndex = dataSize - windowSize;
    return calculateAverage(startIndex, dataSize);
}

double SMAAnalyzer::calculateVariance() {
    double mean = calculateSMA();
    double sumSquaredDiff = 0.0;
    int count = 0;

    int startIndex = dataSize >= windowSize ? dataSize - windowSize : 0;

    for (int i = startIndex; i < dataSize; i++) {
        double diff = prices[i] - mean;
        sumSquaredDiff += diff * diff;
        count++;
    }

    return count > 0 ? sumSquaredDiff / count : 0.0;
}

double SMAAnalyzer::calculateVolatility() {
    double variance = calculateVariance();

    // Simple square root calculation without cmath
    double result = 0.0;
    if (variance > 0.0) {
        double x = variance;
        double guess = variance / 2.0;
        double epsilon = 0.00001;

        while ((guess * guess - x) > epsilon || (x - guess * guess) > epsilon) {
            guess = (guess + x / guess) / 2.0;
        }
        result = guess;
    }

    double sma = calculateSMA();
    if (sma > 0) {
        result = (result / sma) * 100.0;
    }

    return result;
}

void SMAAnalyzer::printAnalysis() {
    std::cout << "=== SMA Analysis ===" << std::endl;
    std::cout << "Data Size: " << dataSize << std::endl;
    std::cout << "Window Size: " << windowSize << std::endl;
    std::cout << "SMA: " << calculateSMA() << std::endl;
    std::cout << "Volatility: " << calculateVolatility() << "%" << std::endl;
}