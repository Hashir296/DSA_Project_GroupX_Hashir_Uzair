#ifndef HASH_RULE_TABLE_H
#define HASH_RULE_TABLE_H

#include <iostream>

struct Rule {
    char key[50];
    char decision[20];
    char explanation[500];
    int confidence;
    Rule* next;

    Rule() : next(nullptr), confidence(0) {
        key[0] = '\0';
        decision[0] = '\0';
        explanation[0] = '\0';
    }
};

class HashRuleTable {
private:
    Rule** table;
    int tableSize;

    int hashFunction(const char* key);
    void copyString(char* dest, const char* src, int maxLen);
    bool compareStrings(const char* str1, const char* str2);

public:
    HashRuleTable(int size = 100);
    ~HashRuleTable();

    void insertRule(const char* key, const char* decision, const char* explanation, int confidence);
    Rule* searchRule(const char* key);

    void initializeDefaultRules();
    void printTable();
};

#endif