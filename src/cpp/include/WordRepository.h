#ifndef WORDREPOSITORY_H
#define WORDREPOSITORY_H

#include <vector>
#include <string>
#include <fstream>
#include <ctime>
#include <cstdlib>

class WordRepository {
private:
    std::vector<std::string> words;

public:
    WordRepository() {
        std::srand(std::time(0)); // Seed random number generator
    }

    bool loadWords(const std::string& filename) {
        std::ifstream file(filename);
        if (!file.is_open()) return false;

        std::string word;
        while (file >> word) {
            words.push_back(word);
        }
        return !words.empty();
    }

    std::string getRandomWord() {
        if (words.empty()) return "ERROR";
        int index = std::rand() % words.size();
        return words[index];
    }
};

#endif