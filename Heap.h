#ifndef HEAP_H
#define HEAP_H

#include <iostream>

class MinHeap {
private:
    double* heap;
    int capacity;
    int size;

    int parent(int i);
    int leftChild(int i);
    int rightChild(int i);

    void heapifyDown(int i);
    void heapifyUp(int i);
    void resize();

public:
    MinHeap(int initialCapacity = 1000);
    ~MinHeap();

    void insert(double value);
    double extractMin();
    double getMin() const;
    int getSize() const;
    bool isEmpty() const;
};

class MaxHeap {
private:
    double* heap;
    int capacity;
    int size;

    int parent(int i);
    int leftChild(int i);
    int rightChild(int i);

    void heapifyDown(int i);
    void heapifyUp(int i);
    void resize();

public:
    MaxHeap(int initialCapacity = 1000);
    ~MaxHeap();

    void insert(double value);
    double extractMax();
    double getMax() const;
    int getSize() const;
    bool isEmpty() const;
};

#endif