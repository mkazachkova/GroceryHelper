/* Team Project Part B 600.120 Spring 2015
 
 Emily Brahma - 9492327727 - ebrahma1
 Mariya Kazachkova - 4407150989 - mkazach1
 Jonathan Liu - 2039138618 - jliu118
 William Yao - 4436917571 - wyao7
 600.120
 Due May 1, 2015
 
 */

#ifndef GAME_H
#define GAME_H

#include <iostream>
#include "Computer.h"
#include "Human.h"
#include "BuildPile.h"
#include "Deck.h"
#include "Player.h"
#include "Pile.h"

using std::vector;
using std::string;

class Game {
    
    int numHumans;
    int numComputers;
    int numInStockPile;
    vector<BuildPile*> *  buildpiles;
    vector<string> names;
    vector<int> finished;
    Deck *deck;
    Deck *temp;
    
public:
    Game(int numHumans, int numComps, vector<string> names, int cardsPerStock);
    Game();
    ~Game();
    vector<Player*> players;
    int getFirstPlayer();
    void saveGame(std::string name, vector<string> names); //parameter is the name of file to write to
    void loadGame(std::string name); //parameter is name of file to read from
    void takeTurn(int playerNum, int choice);
    void printInfoOfPlayer(int num);
    int  playGame();
    void printMenu();
    
    friend std::ostream& operator<<(std::ostream &, const Game &);
    
    // returns numHumans numComputers numInStockPile\n players->names for tester file
    std::string basicInfo();
    
} ;











#endif
