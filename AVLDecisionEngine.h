#ifndef AVL_DECISION_ENGINE_H
#define AVL_DECISION_ENGINE_H

#include <iostream>

struct DecisionNode {
    int score;
    char decision[20];
    char reasoning[500];
    int confidence;
    int height;
    DecisionNode* left;
    DecisionNode* right;

    DecisionNode(int s, const char* dec, const char* reason, int conf);
};

class AVLDecisionEngine {
private:
    DecisionNode* root;

    int getHeight(DecisionNode* node);
    int getBalance(DecisionNode* node);
    int max(int a, int b);

    DecisionNode* rotateRight(DecisionNode* y);
    DecisionNode* rotateLeft(DecisionNode* x);

    DecisionNode* insertNode(DecisionNode* node, int score, const char* decision,
        const char* reasoning, int confidence);
    DecisionNode* findBestDecision(DecisionNode* node, int targetScore);

    void inorderTraversal(DecisionNode* node);
    void destroyTree(DecisionNode* node);

    void copyString(char* dest, const char* src, int maxLen);

public:
    AVLDecisionEngine();
    ~AVLDecisionEngine();

    void insertDecision(int score, const char* decision, const char* reasoning, int confidence);
    DecisionNode* getBestDecision(int score);

    void printTree();
};

#endif