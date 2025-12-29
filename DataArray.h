#ifndef DATA_ARRAY_H
#define DATA_ARRAY_H

#include <iostream>

class DataArray {
private:
    double* data;
    int capacity;
    int size;

    void resize();

public:
    DataArray(int initialCapacity = 1000);
    ~DataArray();

    void insert(double value);
    double get(int index) const;
    int getSize() const;
    void clear();

    double* getData() const;

    void printData() const;
};

#endif