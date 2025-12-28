#include <iostream>
using namespace std;

class SMAAnalyzer
{
private:
    double* prices;
    int dataSize;
    int windowSize;

public:
    // Constructor
    SMAAnalyzer(double priceData[], int size, int window)
    {
        dataSize = size;
        windowSize = window;

        if (windowSize > dataSize)
        {
            windowSize = dataSize;
        }

        prices = new double[dataSize];

        for (int i = 0; i < dataSize; i++)
        {
            prices[i] = priceData[i];
        }
    }

    // Destructor
    ~SMAAnalyzer()
    {
        delete[] prices;
    }

    // Calculate average
    double calculateAverage(int start, int end)
    {
        if (start < 0 || end > dataSize || start >= end)
        {
            return 0.0;
        }

        double sum = 0.0;

        for (int i = start; i < end; i++)
        {
            sum = sum + prices[i];
        }

        return sum / (end - start);
    }

    // Calculate SMA
    double calculateSMA()
    {
        int startIndex = dataSize - windowSize;
        return calculateAverage(startIndex, dataSize);
    }

    // Calculate variance
    double calculateVariance()
    {
        double mean = calculateSMA();
        double sum = 0.0;

        int startIndex = dataSize - windowSize;

        for (int i = startIndex; i < dataSize; i++)
        {
            double diff = prices[i] - mean;
            sum = sum + diff * diff;
        }

        return sum / windowSize;
    }

    // Square root without cmath
    double squareRoot(double x)
    {
        double guess = x / 2.0;
        double epsilon = 0.00001;

        while ((guess * guess - x) > epsilon || (x - guess * guess) > epsilon)
        {
            guess = (guess + x / guess) / 2.0;
        }

        return guess;
    }

    // Calculate volatility
    double calculateVolatility()
    {
        double variance = calculateVariance();
        double stdDev = squareRoot(variance);
        double sma = calculateSMA();

        return (stdDev / sma) * 100.0;
    }

    // Print result
    void printAnalysis()
    {
        cout << "=== SMA Analysis ===" << endl;
        cout << "Data Size: " << dataSize << endl;
        cout << "Window Size: " << windowSize << endl;
        cout << "SMA: " << calculateSMA() << endl;
        cout << "Volatility: " << calculateVolatility() << "%" << endl;
    }
};

int main()
{
    double data[] = {100, 102, 101, 105, 110};
    int size = 5;
    int window = 3;

    SMAAnalyzer analyzer(data, size, window);
    analyzer.printAnalysis();

    return 0;
}
