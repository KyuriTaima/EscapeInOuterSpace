package spaceescape;

import java.util.ArrayList;
import java.util.List;

import spaceescape.gamemechanic.Game;
import spaceescape.model.Player;
import spaceescape.model.boardmap.BoardMap;
import spaceescape.service.GameServiceCLI;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player1 = new Player("Valentin");
		Player player2 = new Player("Matthieu");
		Player player3 = new Player("Thibault");
		Player player4 = new Player("Etienne");
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player4);
		players.add(player3);
		BoardMap map = new BoardMap();
		GameServiceCLI service = new GameServiceCLI();
		Game game = new Game("The Game", map, players, service);
		game.play();
	}

}
