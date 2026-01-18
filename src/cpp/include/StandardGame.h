#ifndef STANDARDGAME_H
#define STANDARDGAME_H

#include <iostream>
#include "WordRepository.h"
#include "AbstractGame.h"

class StandardGame : public AbstractGame{
private:
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

            this->logDebug("New Game Started. Word selected.");
        }
        else{
            this->currentTarget = "ERROR";
            this->logDebug("Critical Error: Dictionary failed.");
        }
    }

    std::vector<int> processGuess(std::string guess) override {
        
        if (!this->isValidGuess(guess)){
            return std::vector<int>(5, -1);
        }
        
        if (!repo.contains(guess)){
            return std::vector<int>(5, -2);
        }
        
        std::vector<int>checkedGuess(5, 0);
        std::string tempTarget = currentTarget;

        // GREEN
        for (int i = 0; i < 5; i++){
            if (guess[i]==currentTarget[i]){
                checkedGuess[i]=static_cast<int>(TileState::GREEN);
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
                checkedGuess[i]=static_cast<int>(TileState::YELLOW);
                tempTarget[found_position]='*';
            }    
        }

        bool isWin = true;
        for (int x : checkedGuess){
            if (x != static_cast<int>(TileState::GREEN)){
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
};




#endif