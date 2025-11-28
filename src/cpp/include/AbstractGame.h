#ifndef ABSTRACTGAME_H
#define ABSTRACTGAME_H

#include <string>
#include <vector>
#include "GuessValidator.h"
#include "Statistics.h"

class AbstractGame
{
protected:
    std::string currentTarget;
    int attemptsRemaining;
    bool isGameOver;
    Statistics stats;

    virtual void decreaseAttempts() = 0;
    
    bool isValidGuess(std::string& guess){
        if (!GuessValidator::isLengthCorrect(guess)){
            return false;
        }
        if (GuessValidator::hasInvalidChars(guess)){
            return false;
        }
        return true;
    }

public:

    virtual ~AbstractGame(){}

    virtual void startNewGame(const std::string& dictPath) = 0;

    virtual std::vector<int> processGuess(std::string guess) = 0;

    bool getIsGameOver(){
        return isGameOver;
    }

    std::string getStatsString(){
        return stats.getStatisticsString();
    }

    // FOR DEBUG
    std::string getDebugTarget(){
        return currentTarget;
    }

};




#endif