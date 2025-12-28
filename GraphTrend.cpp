#include <iostream>
using namespace std;

// Node structure for adjacency list
struct Node
{
    int vertex;
    Node* next;

    Node(int v)
    {
        vertex = v;
        next = nullptr;
    }
};

class GraphTrend
{
private:
    int vertices;
    Node** adjacencyList;
    bool* visited;

    int uptrendCount;
    int downtrendCount;
    int sidewaysCount;

public:
    // Constructor
    GraphTrend(int v)
    {
        vertices = v;

        adjacencyList = new Node*[vertices];
        visited = new bool[vertices];

        for (int i = 0; i < vertices; i++)
        {
            adjacencyList[i] = nullptr;
            visited[i] = false;
        }

        uptrendCount = 0;
        downtrendCount = 0;
        sidewaysCount = 0;
    }

    // Destructor
    ~GraphTrend()
    {
        for (int i = 0; i < vertices; i++)
        {
            Node* current = adjacencyList[i];
            while (current != nullptr)
            {
                Node* temp = current;
                current = current->next;
                delete temp;
            }
        }
        delete[] adjacencyList;
        delete[] visited;
    }

    // Add edge
    void addEdge(int from, int to)
    {
        if (from >= 0 && from < vertices && to >= 0 && to < vertices)
        {
            Node* newNode = new Node(to);
            newNode->next = adjacencyList[from];
            adjacencyList[from] = newNode;
        }
    }

    // Build graph from prices
    void buildGraphFromPrices(double prices[], int size)
    {
        for (int i = 0; i < size - 1 && i < vertices - 1; i++)
        {
            addEdge(i, i + 1);
        }
    }

    // Analyze one edge
    void analyzeEdge(int from, int to, double prices[])
    {
        double diff = prices[to] - prices[from];
        double threshold = prices[from] * 0.005;

        if (diff > threshold)
            uptrendCount++;
        else if (diff < -threshold)
            downtrendCount++;
        else
            sidewaysCount++;
    }

    // DFS helper
    void DFSUtil(int v)
    {
        visited[v] = true;

        Node* current = adjacencyList[v];
        while (current != nullptr)
        {
            if (!visited[current->vertex])
            {
                DFSUtil(current->vertex);
            }
            current = current->next;
        }
    }

    // Analyze trend
    void analyzeTrend(double prices[])
    {
        uptrendCount = 0;
        downtrendCount = 0;
        sidewaysCount = 0;

        for (int i = 0; i < vertices; i++)
            visited[i] = false;

        for (int i = 0; i < vertices; i++)
        {
            Node* current = adjacencyList[i];
            while (current != nullptr)
            {
                analyzeEdge(i, current->vertex, prices);
                current = current->next;
            }
        }

        DFSUtil(0);
    }

    // Get trend direction
    const char* getTrendDirection()
    {
        int total = uptrendCount + downtrendCount + sidewaysCount;

        if (total == 0)
            return "SIDEWAYS";

        double upPercent = (double)uptrendCount / total * 100;
        double downPercent = (double)downtrendCount / total * 100;

        if (upPercent > 60)
            return "UPTREND";
        else if (downPercent > 60)
            return "DOWNTREND";
        else
            return "SIDEWAYS";
    }

    // Get risk level
    const char* getRiskLevel(double volatility)
    {
        if (volatility < 5)
            return "LOW";
        else if (volatility < 15)
            return "MEDIUM";
        else
            return "HIGH";
    }

    // Print graph
    void printGraph()
    {
        cout << "=== Graph Structure ===" << endl;

        for (int i = 0; i < vertices && i < 10; i++)
        {
            cout << "Vertex " << i << ": ";
            Node* current = adjacencyList[i];
            while (current != nullptr)
            {
                cout << current->vertex << " ";
                current = current->next;
            }
            cout << endl;
        }
    }
};

int main()
{
    double prices[] = {100, 102, 101, 105, 110};
    int size = 5;

    GraphTrend graph(size);

    graph.buildGraphFromPrices(prices, size);
    graph.analyzeTrend(prices);

    graph.printGraph();

    cout << "Trend Direction: " << graph.getTrendDirection() << endl;
    cout << "Risk Level (Volatility 10): " << graph.getRiskLevel(10) << endl;

    return 0;
}
