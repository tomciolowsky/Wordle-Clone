#ifndef WORDREPOSITORY_H
#define WORDREPOSITORY_H

#include <vector>
#include <string>
#include <algorithm>
#include <fstream>
#include <ctime>
#include <cstdlib>

class WordRepository {
private:
    std::vector<std::string> wordList;

public:
    WordRepository() {
        std::srand(std::time(0)); // Seed random number generator
    }

    bool contains(const std::string& guess){
        if (std::find(wordList.begin(), wordList.end(), guess) != wordList.end())
        {
            return true;
        }
        else{
            return false;
        }
    }

    bool loadWords(const std::string& filename) {
        std::ifstream file(filename);
        if (!file.is_open()) return false;

        std::string word;
        while (file >> word) {
            wordList.push_back(word);
        }
        return !wordList.empty();
    }

    std::string getRandomWord() {
        if (wordList.empty()) return "ERROR";
        int index = std::rand() % wordList.size();
        return wordList[index];
    }

    int getWordCount(){
        return wordList.size();
    }
};

#endif