//
//  Pile.cpp
//  BuildPile
//
//  Created by William Yao on 2015-04-27.
//  Copyright (c) 2015 William Yao. All rights reserved.
//

#include "Pile.h"
#include <algorithm>
#include <random>
#include <chrono>
#include <sstream>
#include <iostream>
Pile::Pile(){
    
}

void Pile::shuffle() {
    unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
    
    std::shuffle(cards.begin(), cards.end(), std::default_random_engine(seed));
}

int Pile::getSize() {
    return cards.size();
}

std::string Pile::returnsPileNums() {
    std::ostringstream oss;
    for (unsigned int i = 0; i < cards.size(); i++) {
        oss << cards[i] << " ";
    }
    if (cards.size() == 0) {
        int x = -1; //repesents that it's empty
        oss << x << " ";
    }
    return oss.str();
}
