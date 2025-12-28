#ifndef SMA_ANALYZER_H
#define SMA_ANALYZER_H

#include <iostream>

class SMAAnalyzer {
private:
    double* prices;
    int dataSize;
    int windowSize;

    double calculateAverage(int start, int end);
    double calculateVariance();

public:
    SMAAnalyzer(double* priceData, int size, int window);
    ~SMAAnalyzer();

    double calculateSMA();
    double calculateVolatility();

    void printAnalysis();
};

#endif