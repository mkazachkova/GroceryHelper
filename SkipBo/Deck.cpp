//
//  Deck.cpp
//  BuildPile
//
//  Created by William Yao on 2015-04-27.
//  Copyright (c) 2015 William Yao. All rights reserved.
//

#include "Deck.h"

Deck::Deck(){
    for (int x = 1; x < 13; x++){
        for (int y = 1; y < 13; y++){
            cards.push_back(y);
        }
    }
    
    for (int z = 0; z < 18; z++){
        cards.push_back(13);
    }
    
    shuffle();
}

Deck::Deck(std::vector<int> temp){
    this -> cards = temp;
}




Deck::~Deck(){
}


int Deck::removeOneCard(){
    int x = cards[0];
    cards.erase(cards.begin());
    return x;
}

void Deck::addBuildPile(std::vector<int> & finishedPile){
    for (unsigned int i = 0; i < finishedPile.size(); i++){
        cards.push_back(finishedPile[i]);
    }
    shuffle();
    finishedPile.clear();
}

