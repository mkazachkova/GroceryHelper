# make variables let us avoid pasting these options in multiple places                                                                                        
CC = g++
CXXFLAGS = -std=c++11 -Wall -Wextra -pedantic -O -g         # for final build                                                                                   
#CXXFLAGS = -std=c++11 -Wall -Wextra -pedantic -O0 -g   # for debugging                                                                                        

bin: SkipBoMain                                                                                                                                                 

#Human.o: Human.cpp Human.h Player.o
#	$(CC) $(CXXFLAGS) -c Human.cpp


test: SkipBo
	echo "Running test..."
	./SkipBo

SkipBoMain: SkipBoMain.cpp Game.o Player.o BuildPile.o Deck.o Pile.o Computer.o Human.o
	$(CC) $(CXXFLAGS) SkipBoMain.cpp Player.cpp BuildPile.cpp Deck.cpp Pile.cpp Game.cpp Computer.cpp Human.cpp -pedantic -o SkipBoMain


Game.o: Game.cpp Player.o BuildPile.o Deck.o Pile.o Computer.o Human.o
	$(CC) $(CXXFLAGS) -c Game.cpp



SkipBo: SkipBo.cpp Player.o BuildPile.o Deck.o Pile.o Computer.o Human.o Game.o 
	$(CC) $(CXXFLAGS) Player.cpp BuildPile.cpp Deck.cpp Pile.cpp SkipBo.cpp Computer.cpp Human.cpp Game.cpp -pedantic -o SkipBo 


Human.o: Human.cpp  Player.o                                                                                              
	$(CC) $(CXXFLAGS) -c Human.cpp  


Computer.o: Computer.cpp Player.o                                                                                                                 
	$(CC) $(CXXFLAGS) -c Computer.cpp   

Player.o: Player.cpp BuildPile.o Deck.o Pile.o
	$(CC) $(CXXFLAGS) -c Player.cpp
# (short for) gcc -std=c99 -Wall -Wextra -pedantic -O -c functions.c                                                                                                                                                                                
BuildPile.o: BuildPile.cpp Pile.o
	$(CC) $(CXXFLAGS) -c BuildPile.cpp

Deck.o: Deck.cpp Pile.o
	$(CC) $(CXXFLAGS) -c Deck.cpp

#Test.o: Test.cpp Player.h BuildPile.h Deck.h
#	$(CC) $(CXXFLAGS) -c Test.cpp
# dont forget to add Computer                                                                                                                                


Pile.o: Pile.cpp
	$(CC) $(CXXFLAGS) -c Pile.cpp

#SkipBo.o: SkipBo.cpp                                                                                                                          
#	$(CC) $(CXXFLAGS) -pedantic -O -c SkipBo.cpp                                                                                                          

#Test: SkipBo.o Player.o BuildPile.o Deck.o Pile.o

# SkipBo: SkipBo.o functions.o                                                                                                                                
#       $(CC) $(CXXFLAGS) -O -o SkipBo SkipBo.o functions.o                                                                                                   

clean:
	rm -f *.o SkipBo
