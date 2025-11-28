#ifndef STANDARDGAME_H
#define STANDARDGAME_H

#include <iostream>
#include "WordRepository.h"
#include "AbstractGame.h"

class StandardGame : public AbstractGame{
private:
    bool hardModeActive; //TODO
    WordRepository repo;

protected:
    void decreaseAttempts() override {
        this->attemptsRemaining--;
        if (this->attemptsRemaining <= 0){
            this->isGameOver = true;
        }
    }

public:
    void startNewGame(const std::string& dictPath) override {
        if (repo.loadWords(dictPath)){
            this->currentTarget = repo.getRandomWord();
            this->attemptsRemaining = 6;
            this->isGameOver = false;
        }
        else{
            this->currentTarget = "ERROR";
        }
    }

    // 0 = Grey, 1 = Yellow, 2 = Green
    std::vector<int> processGuess(std::string guess) override {
        
        // VALIDATION CHECK
        if (!this->isValidGuess(guess)){
            return std::vector<int>(5, -1);
        }
        // EXISTANCE CHECK
        if (!repo.contains(guess)){
            return std::vector<int>(5, -2);
        }
        
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

        bool isWin = true;
        for (int x : checkedGuess){
            if (x != 2){
                isWin=false;
            }
        }
        if (isWin){
            this->isGameOver = true;
            this->stats.addWin();
        }
        else{
            decreaseAttempts();

            if (this->isGameOver){
                this->stats.addLoss();
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