#ifndef STATISTICS_H
#define STATISTICS_H

#include <string>
#include <fstream>
#include <iostream>

class Statistics
{
private:
    int wonGames;
    int lostGames;
    int gamesPlayed;
    int winStreak;
    const std::string FILENAME = "gamestats.txt";

public:
    Statistics(){
        wonGames = 0;
        lostGames = 0;
        gamesPlayed = 0;
        winStreak = 0;
        loadFromFile();
    }

    void addWin(){
        this->gamesPlayed++;
        this->winStreak++;
        this->wonGames++;
        saveToFile();
    }

    void addLoss(){
        this->gamesPlayed++;
        this->winStreak=0;
        this->lostGames++;
        saveToFile();
    }

    std::string getStatisticsString(){
        std::string s = "Played: " + std::to_string(gamesPlayed) +
                        "\nWins: " + std::to_string(wonGames) +
                        "\nLoses: " + std::to_string(lostGames) +
                        "\nCurrent Streak: " + std::to_string(winStreak);
        return s;
    }

    void saveToFile(){
        std::ofstream file(FILENAME);
        if (file.is_open()){
            file << wonGames << " " << lostGames << " " << gamesPlayed << " " << winStreak;
            file.close();
        }
    }
    void loadFromFile(){
        std::ifstream file(FILENAME);
        if (file.is_open()){
            file >> wonGames >> lostGames >> gamesPlayed >> winStreak;
            file.close();
        }
    }
};

#endif