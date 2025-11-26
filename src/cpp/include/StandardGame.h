#ifndef STANDARDGAME_H
#define STANDARDGAME_H

#include <string>
#include <vector>
#include <algorithm>
#include "WordRepository.h"

class StandardGame
{
private:
    std::string currentTarget;
    WordRepository repo;

public:
    void startNewGame(const std::string& dictPath){
        repo.loadWords(dictPath);
        currentTarget = repo.getRandomWord();
    }

    // 0 = Grey, 1 = Yellow, 2 = Green
    std::vector<int> checkGuess(std::string guess){
        std::vector<int>checkedGuess(5, 0);
        std::string tempTarget = currentTarget;

        // GREEN
        for (int i = 0; i < 5; i++){
            if (guess[i]==currentTarget[i]){
                checkedGuess[i]=2;
                tempTarget[i]='*';
                guess[i]='#';
            }       
        }
        // YELLOW
        for (int i = 0; i < 5; i++){
            if (guess[i]=='#'){
                continue;
            }
            auto found_position = tempTarget.find(guess[i]);    
            if (found_position != std::string::npos){
                checkedGuess[i]=1;
                tempTarget[found_position]='*';
            }    
        }
        
        return checkedGuess;
    }
    // FOR DEBUG
    std::string getDebugTarget(){
        return currentTarget;
    }

};




#endif