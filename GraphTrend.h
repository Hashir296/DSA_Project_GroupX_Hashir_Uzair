#ifndef GRAPH_TREND_H
#define GRAPH_TREND_H

#include <iostream>

struct Node {
    int vertex;
    Node* next;

    Node(int v) : vertex(v), next(nullptr) {}
};

class GraphTrend {
private:
    Node** adjacencyList;
    int vertices;
    bool* visited;
    int uptrendCount;
    int downtrendCount;
    int sidewaysCount;

    void DFSUtil(int v);
    void analyzeEdge(int from, int to, double* prices);

public:
    GraphTrend(int v);
    ~GraphTrend();

    void addEdge(int from, int to);
    void buildGraphFromPrices(double* prices, int size);
    void analyzeTrend(double* prices);

    const char* getTrendDirection();
    const char* getRiskLevel(double volatility);

    void printGraph();
};

#endif