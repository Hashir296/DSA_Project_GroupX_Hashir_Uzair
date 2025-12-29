#include "AVLDecisionEngine.h"

DecisionNode::DecisionNode(int s, const char* dec, const char* reason, int conf) {
    score = s;
    confidence = conf;
    height = 1;
    left = nullptr;
    right = nullptr;

    int i = 0;
    while (dec[i] != '\0' && i < 19) {
        decision[i] = dec[i];
        i++;
    }
    decision[i] = '\0';

    i = 0;
    while (reason[i] != '\0' && i < 499) {
        reasoning[i] = reason[i];
        i++;
    }
    reasoning[i] = '\0';
}

AVLDecisionEngine::AVLDecisionEngine() {
    root = nullptr;
}

AVLDecisionEngine::~AVLDecisionEngine() {
    destroyTree(root);
}

void AVLDecisionEngine::destroyTree(DecisionNode* node) {
    if (node != nullptr) {
        destroyTree(node->left);
        destroyTree(node->right);
        delete node;
    }
}

void AVLDecisionEngine::copyString(char* dest, const char* src, int maxLen) {
    int i = 0;
    while (src[i] != '\0' && i < maxLen - 1) {
        dest[i] = src[i];
        i++;
    }
    dest[i] = '\0';
}

int AVLDecisionEngine::getHeight(DecisionNode* node) {
    if (node == nullptr) {
        return 0;
    }
    return node->height;
}

int AVLDecisionEngine::getBalance(DecisionNode* node) {
    if (node == nullptr) {
        return 0;
    }
    return getHeight(node->left) - getHeight(node->right);
}

int AVLDecisionEngine::max(int a, int b) {
    return (a > b) ? a : b;
}

DecisionNode* AVLDecisionEngine::rotateRight(DecisionNode* y) {
    DecisionNode* x = y->left;
    DecisionNode* T2 = x->right;

    x->right = y;
    y->left = T2;

    y->height = max(getHeight(y->left), getHeight(y->right)) + 1;
    x->height = max(getHeight(x->left), getHeight(x->right)) + 1;

    return x;
}

DecisionNode* AVLDecisionEngine::rotateLeft(DecisionNode* x) {
    DecisionNode* y = x->right;
    DecisionNode* T2 = y->left;

    y->left = x;
    x->right = T2;

    x->height = max(getHeight(x->left), getHeight(x->right)) + 1;
    y->height = max(getHeight(y->left), getHeight(y->right)) + 1;

    return y;
}

DecisionNode* AVLDecisionEngine::insertNode(DecisionNode* node, int score, const char* decision,
    const char* reasoning, int confidence) {
    if (node == nullptr) {
        return new DecisionNode(score, decision, reasoning, confidence);
    }

    if (score < node->score) {
        node->left = insertNode(node->left, score, decision, reasoning, confidence);
    }
    else if (score > node->score) {
        node->right = insertNode(node->right, score, decision, reasoning, confidence);
    }
    else {
        return node;
    }

    node->height = 1 + max(getHeight(node->left), getHeight(node->right));

    int balance = getBalance(node);

    // Left Left Case
    if (balance > 1 && score < node->left->score) {
        return rotateRight(node);
    }

    // Right Right Case
    if (balance < -1 && score > node->right->score) {
        return rotateLeft(node);
    }

    // Left Right Case
    if (balance > 1 && score > node->left->score) {
        node->left = rotateLeft(node->left);
        return rotateRight(node);
    }

    // Right Left Case
    if (balance < -1 && score < node->right->score) {
        node->right = rotateRight(node->right);
        return rotateLeft(node);
    }

    return node;
}

void AVLDecisionEngine::insertDecision(int score, const char* decision, const char* reasoning, int confidence) {
    root = insertNode(root, score, decision, reasoning, confidence);
}

DecisionNode* AVLDecisionEngine::findBestDecision(DecisionNode* node, int targetScore) {
    if (node == nullptr) {
        return nullptr;
    }

    if (node->score == targetScore) {
        return node;
    }

    DecisionNode* leftResult = findBestDecision(node->left, targetScore);
    DecisionNode* rightResult = findBestDecision(node->right, targetScore);

    DecisionNode* closest = node;
    int minDiff = (node->score > targetScore) ? (node->score - targetScore) : (targetScore - node->score);

    if (leftResult != nullptr) {
        int leftDiff = (leftResult->score > targetScore) ?
            (leftResult->score - targetScore) : (targetScore - leftResult->score);
        if (leftDiff < minDiff) {
            closest = leftResult;
            minDiff = leftDiff;
        }
    }

    if (rightResult != nullptr) {
        int rightDiff = (rightResult->score > targetScore) ?
            (rightResult->score - targetScore) : (targetScore - rightResult->score);
        if (rightDiff < minDiff) {
            closest = rightResult;
        }
    }

    return closest;
}

DecisionNode* AVLDecisionEngine::getBestDecision(int score) {
    return findBestDecision(root, score);
}

void AVLDecisionEngine::inorderTraversal(DecisionNode* node) {
    if (node != nullptr) {
        inorderTraversal(node->left);
        std::cout << "Score: " << node->score << ", Decision: " << node->decision
            << ", Confidence: " << node->confidence << "%" << std::endl;
        inorderTraversal(node->right);
    }
}

void AVLDecisionEngine::printTree() {
    std::cout << "=== AVL Decision Tree ===" << std::endl;
    inorderTraversal(root);
}