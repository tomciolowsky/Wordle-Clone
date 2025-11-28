#ifndef GUESSVALIDATOR_H
#define GUESSVALIDATOR_H

#include <string>
#include <cctype>

class GuessValidator {
    public:
        static bool isLengthCorrect(const std::string& word){
            return word.length() == 5;
        }

        static bool hasInvalidChars(const std::string& word){
            for (char c : word){
                if (!std::isalpha(c)){
                    return true;
                }
            }
            return false;
        }
};


#endif