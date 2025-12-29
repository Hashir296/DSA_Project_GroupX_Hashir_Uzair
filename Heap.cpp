#include "Heap.h"

// MinHeap Implementation
MinHeap::MinHeap(int initialCapacity) {
    capacity = initialCapacity;
    size = 0;
    heap = new double[capacity];
}

MinHeap::~MinHeap() {
    delete[] heap;
}

int MinHeap::parent(int i) {
    return (i - 1) / 2;
}

int MinHeap::leftChild(int i) {
    return 2 * i + 1;
}

int MinHeap::rightChild(int i) {
    return 2 * i + 2;
}

void MinHeap::resize() {
    capacity *= 2;
    double* newHeap = new double[capacity];

    for (int i = 0; i < size; i++) {
        newHeap[i] = heap[i];
    }

    delete[] heap;
    heap = newHeap;
}

void MinHeap::heapifyUp(int i) {
    while (i > 0 && heap[parent(i)] > heap[i]) {
        double temp = heap[i];
        heap[i] = heap[parent(i)];
        heap[parent(i)] = temp;
        i = parent(i);
    }
}

void MinHeap::heapifyDown(int i) {
    int minIndex = i;
    int left = leftChild(i);
    int right = rightChild(i);

    if (left < size && heap[left] < heap[minIndex]) {
        minIndex = left;
    }

    if (right < size && heap[right] < heap[minIndex]) {
        minIndex = right;
    }

    if (i != minIndex) {
        double temp = heap[i];
        heap[i] = heap[minIndex];
        heap[minIndex] = temp;
        heapifyDown(minIndex);
    }
}

void MinHeap::insert(double value) {
    if (size >= capacity) {
        resize();
    }

    heap[size] = value;
    heapifyUp(size);
    size++;
}

double MinHeap::extractMin() {
    if (isEmpty()) {
        return 0.0;
    }

    double minValue = heap[0];
    heap[0] = heap[size - 1];
    size--;
    heapifyDown(0);

    return minValue;
}

double MinHeap::getMin() const {
    if (isEmpty()) {
        return 0.0;
    }
    return heap[0];
}

int MinHeap::getSize() const {
    return size;
}

bool MinHeap::isEmpty() const {
    return size == 0;
}

// MaxHeap Implementation
MaxHeap::MaxHeap(int initialCapacity) {
    capacity = initialCapacity;
    size = 0;
    heap = new double[capacity];
}

MaxHeap::~MaxHeap() {
    delete[] heap;
}

int MaxHeap::parent(int i) {
    return (i - 1) / 2;
}

int MaxHeap::leftChild(int i) {
    return 2 * i + 1;
}

int MaxHeap::rightChild(int i) {
    return 2 * i + 2;
}

void MaxHeap::resize() {
    capacity *= 2;
    double* newHeap = new double[capacity];

    for (int i = 0; i < size; i++) {
        newHeap[i] = heap[i];
    }

    delete[] heap;
    heap = newHeap;
}

void MaxHeap::heapifyUp(int i) {
    while (i > 0 && heap[parent(i)] < heap[i]) {
        double temp = heap[i];
        heap[i] = heap[parent(i)];
        heap[parent(i)] = temp;
        i = parent(i);
    }
}

void MaxHeap::heapifyDown(int i) {
    int maxIndex = i;
    int left = leftChild(i);
    int right = rightChild(i);

    if (left < size && heap[left] > heap[maxIndex]) {
        maxIndex = left;
    }

    if (right < size && heap[right] > heap[maxIndex]) {
        maxIndex = right;
    }

    if (i != maxIndex) {
        double temp = heap[i];
        heap[i] = heap[maxIndex];
        heap[maxIndex] = temp;
        heapifyDown(maxIndex);
    }
}

void MaxHeap::insert(double value) {
    if (size >= capacity) {
        resize();
    }

    heap[size] = value;
    heapifyUp(size);
    size++;
}

double MaxHeap::extractMax() {
    if (isEmpty()) {
        return 0.0;
    }

    double maxValue = heap[0];
    heap[0] = heap[size - 1];
    size--;
    heapifyDown(0);

    return maxValue;
}

double MaxHeap::getMax() const {
    if (isEmpty()) {
        return 0.0;
    }
    return heap[0];
}

int MaxHeap::getSize() const {
    return size;
}

bool MaxHeap::isEmpty() const {
    return size == 0;
}