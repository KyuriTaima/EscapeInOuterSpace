package spaceescape.service;


import java.util.List;

import spaceescape.model.Player;
import spaceescape.model.boardmap.BoardMap;
import spaceescape.model.boardmap.Coordinate;

public interface GameService {

    void displayPlayerMap(Player player, BoardMap map);
    Coordinate askPlayerWhereMove(Player player, List<Coordinate> possibleMove);
    Coordinate askPlayerWhereMakeNoise(int mapHeight, int mapWidth);
	void startGame();
	void endGame(List<Player> winners);
	int askPlayerAction();

}
