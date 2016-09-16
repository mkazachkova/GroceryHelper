//
//  BuildPile.cpp
//  BuildPile
//
//  Created by William Yao on 2015-04-27.
//  Copyright (c) 2015 William Yao. All rights reserved.
//

#include "BuildPile.h"
#include <vector>
#include <iostream>
BuildPile::BuildPile(){
    
}

BuildPile::~BuildPile(){
  
}


BuildPile::BuildPile(std::vector<int> & tempPile){

    for(unsigned int i = 0; i < tempPile.size(); i++) {
        this-> cards.push_back(tempPile[i]);
    }
    
}




unsigned long BuildPile::peek(){
    return cards.size();
}

void BuildPile::reset(std::vector<int> & finishedPile){
    for(int i = 0; i < 12; i++){
        finishedPile.push_back(cards[i]);
    }
    for (unsigned int i = 0; i < finishedPile.size(); i++) {
    }
    cards.clear();
}


bool BuildPile::addToBuildPile(int cardNum, std::vector<int>& finishedPile){
    std::vector<int>::iterator it;
    unsigned int x = cardNum;
    if (x != (cards.size() + 1) && cardNum != 13) {  // if the cardNum isn't correct
        return false;
    }
    
    it = cards.begin();
    it = cards.insert(it, cardNum);
    if (this -> peek() == 12) {
        reset(finishedPile);
        
    }

    return true;  
    
}


