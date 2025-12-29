#include "DataArray.h"

DataArray::DataArray(int initialCapacity) {
    capacity = initialCapacity;
    size = 0;
    data = new double[capacity];
}

DataArray::~DataArray() {
    delete[] data;
}

void DataArray::resize() {
    capacity *= 2;
    double* newData = new double[capacity];

    for (int i = 0; i < size; i++) {
        newData[i] = data[i];
    }

    delete[] data;
    data = newData;
}

void DataArray::insert(double value) {
    if (size >= capacity) {
        resize();
    }
    data[size++] = value;
}

double DataArray::get(int index) const {
    if (index >= 0 && index < size) {
        return data[index];
    }
    return 0.0;
}

int DataArray::getSize() const {
    return size;
}

void DataArray::clear() {
    size = 0;
}

double* DataArray::getData() const {
    return data;
}

void DataArray::printData() const {
    std::cout << "Data Array (" << size << " elements):" << std::endl;
    for (int i = 0; i < size && i < 10; i++) {
        std::cout << data[i] << " ";
    }
    if (size > 10) {
        std::cout << "... (" << (size - 10) << " more)";
    }
    std::cout << std::endl;
}