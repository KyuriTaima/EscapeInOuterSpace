package spaceescape.gamemechanic;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import spaceescape.model.Player;
import spaceescape.model.alien.*;
import spaceescape.model.character.*;
import spaceescape.model.boardmap.BoardMap;
import spaceescape.model.boardmap.Coordinate;
import spaceescape.model.boardmap.RoomType;
import spaceescape.service.GameService;
import spaceescape.service.GameServiceCLI;
import spaceescape.model.boardmap.RoomType;

public class Game {

    private String name;
    private BoardMap map;
    private List<Player> players = new ArrayList<>();
    private GameServiceCLI cli;

    public Game(String name, BoardMap map, List<Player> activePlayers, GameServiceCLI cli) {
        this.name = name;
        this.map = map;
        this.players.addAll(activePlayers);
        this.cli = cli;
    }


    /**
     * Assign a unique Character for each player randomly.
     */
    private void initGame() {
    	
    	//Allouement des characters aux joueurs
    	boolean gameIsValid = false;
    	while(!gameIsValid) {
	    	for(int i=0; i<players.size(); i++) {
	    		boolean isInit = false;
	    		while(!isInit) {
			    	Random rand = new Random(); 
			    	int nombreAleatoire = rand.nextInt(players.size()) + 1;
			    	
			    	switch(nombreAleatoire) {
				    	case 1:
				    		if(!lurkExists()) {
					    		players.get(i).setCharacter(new Lurker());
					    		isInit = true;
				    		}
				    		break;
				    	case 2:
				    		if(!praetExists()) {
					    		players.get(i).setCharacter(new Praetorian());
					    		isInit = true;
				    		}
				    		break;
				    	case 3:
				    		if(!soldierExists()) {
					    		players.get(i).setCharacter(new Soldier());
					    		isInit = true;
				    		}
				    		break;
				    	case 4:
				    		if(!engineerExists()) {
					    		players.get(i).setCharacter(new Engineer());
					    		isInit = true;
				    		}
				    		break;
			    		default:
				    		break;
			    	}
	    		}
	    	}
	    	if(gameIsValid()) {
	    		gameIsValid = true;
	    	}
	    	else {
	    		emptyPlayers();
	    	}
    	}
    	
    	
		//Placement des personnages sur la map
		for(int j=0;j<players.size();j++) {
			if(players.get(j).getCharacter() instanceof Alien) {
				players.get(j).getCharacter().setCoordinate(map.getAlienSpawn());
			}
			else {
				players.get(j).getCharacter().setCoordinate(map.getMarineSpawn());
			}
		}
    }
    
    private void emptyPlayers() {
    	for(int i=0;i<players.size();i++) {
    		players.get(i).setCharacter(null);
    	}
		
	}


	private boolean gameIsValid() {
    	int alienCount = 0;
    	int marineCount = 0;
    	for(int i=0;i<players.size();i++) {
    		if( (players.get(i).getCharacter() instanceof Lurker) || (players.get(i).getCharacter() instanceof Praetorian)) {
    			alienCount++;
    		}
    		else {
    			marineCount++;
    		}
    	}
    	if(alienCount >= marineCount) {
    		return true;
    	}
		return false;
	}


	private boolean lurkExists() {
    	for (int i=0; i<4; i++) {
    		if(this.players.get(i).getCharacter() instanceof Lurker) {
    			return true;
    		}
    	}
		return false;
    }
    private boolean praetExists() {
    	for (int i=0; i<4; i++) {
    		if(this.players.get(i).getCharacter() instanceof Praetorian) {
    			return true;
    		}
    	}
		return false;
    }
    private boolean soldierExists() {
    	for (int i=0; i<4; i++) {
    		if(this.players.get(i).getCharacter() instanceof Soldier) {
    			return true;
    		}
    	}
		return false;
    }
    private boolean engineerExists() {
    	for (int i=0; i<4; i++) {
    		if(this.players.get(i).getCharacter() instanceof Engineer) {
    			return true;
    		}
    	}
		return false;
    }

    public void play() {
    	initGame();
        cli.startGame();
        boolean endGame = false;
        do {
        	for(int i=0;i<players.size();i++) {
        		if(players.get(i).getCharacter().getPlaying() == true) {
	        		cli.displayPlayerMap(players.get(i), map);
	        		playerTurn(players.get(i));
        		}
        	}
        	endGame = true;
        	for(int j=0;j<players.size();j++) {
        		if((players.get(j).getCharacter().getPlaying() == true) && ( (players.get(j).getCharacter() instanceof Engineer) || (players.get(j).getCharacter() instanceof Soldier) )){
        			endGame = false;
        		}
        	}
        }while(endGame != true);
        
        cli.endGame();
    	for(int k=0;k<players.size();k++) {
    		if(players.get(k).getCharacter().getEscaped() == true ) {
    			cli.playerEscaped(players.get(k));
    		}
    		if(players.get(k).getCharacter().getAlive() == false ) {
    			cli.playerDead(players.get(k));
    		}
    		if(players.get(k).getCharacter().getPlaying() == true ) {
    			cli.playerIsPlaying(players.get(k));
    		}
    	}
    }
    
    private void playerTurn(Player player) {
    	int choice = 1;
    	if(!(player.getCharacter() instanceof Engineer)) {
    		if(player.getCharacter() instanceof Soldier) {	//On teste si c'est un Soldier
    			if(((Soldier) player.getCharacter()).getCanAttack() == true) {	//On teste s'il a déjà attaqué
    				choice = cli.askPlayerAction();
    			}
			}else {
				choice = cli.askPlayerAction();
    		}
    	}
    	displace(player);
    	if(choice == 2) {
    		if(player.getCharacter() instanceof Soldier) {	//On teste si c'est un Soldier
    			if(((Soldier) player.getCharacter()).getCanAttack() == true) {	//On teste s'il a déjà attaqué
    				attackAction(player);
    			}
    		}else {
    			attackAction(player);
    		}
    	}
    }
    
    private Coordinate moveAction(Player player) { //Gère l'action de mouvement du joueur
    	List<Coordinate> charCanGo = whereCharacterCanGo(player);	//On récupère la liste des cases possibles
    	Coordinate coord = this.cli.askPlayerWhereMove(player, charCanGo);	//On demande où le joueur veut se rendre
    	return coord;
    }
    
    private void attackAction(Player player) { //Gère l'attaque du joueur
    	for(int i=0;i<players.size();i++) {
    		if(players.get(i).getCharacter().getCoordinate().equals(player.getCharacter().getCoordinate())) {
    			if(!(players.get(i).getCharacter() instanceof Praetorian) && !(player.equals(players.get(i)))) {
    				players.get(i).getCharacter().setAlive(false);
    				players.get(i).getCharacter().setPlaying(false);
    				players.get(i).getCharacter().setCoordinate(new Coordinate(10,6));
    				cli.deathCase(player, players.get(i));
    			}
    		}
    	}
    	if(player.getCharacter() instanceof Soldier) {
    		((Soldier) player.getCharacter()).setCanAttack(false);
    	}
    	
    }
    
    private void displace(Player player) {	//Gère les déplacements du joueur
    	
    	moveCharacterTo(moveAction(player), player); //On déplace le joueur sur la case demandée
    	switch(map.getMap().get(player.getCharacter().getCoordinate()).getType()) {
    	case Capsule:
    		openCapsule(player);
    		break;
    	case Unsafe:
    		unsafeDraw(player);
    		break;
    	case Safe:
    		cli.safeCase(player);
    		break;
    	default:
    		break;
    	}
    	
    	
    	
    }
    
    private List<Coordinate> whereCharacterCanGo(Player player){	//Renvoie la liste des coordonnées où le joueur peut se déplacer
    	Coordinate currentCoord = player.getCharacter().getCoordinate();
    	int movement = player.getCharacter().getMovement();
    	List<Coordinate> possibleRooms = new ArrayList<Coordinate>();
    	
    	if(movement == 1) {
    		possibleRooms.addAll(adjacentRooms(currentCoord));
    	}
    	if(movement == 2) {
    		possibleRooms.addAll(adjacentRooms(currentCoord));
    		int taille = possibleRooms.size();
    		for(int i=0;i<taille;i++) {
    			possibleRooms.addAll(adjacentRooms(possibleRooms.get(i)));
    		}
    		
    		for(int j=0;j<possibleRooms.size();j++) {
    			for(int k=0;k<possibleRooms.size();k++) {
    				if(k != j) {
	    				if(possibleRooms.get(j).equals(possibleRooms.get(k))) {
	    					possibleRooms.remove(k);
	    				}
    				}
    			}
    			if( (possibleRooms.get(j).equals(currentCoord)) && !(player.getCharacter() instanceof Lurker) ) {
    				possibleRooms.remove(j);
    			}
    			if( (map.getMap().get(possibleRooms.get(j)).getType() == RoomType.Capsule) && (player.getCharacter() instanceof Alien)) {
    				possibleRooms.remove(j);
    			}
    		}
    	}
    	
    	return possibleRooms;
    }
    
    private List<Coordinate> adjacentRooms(Coordinate currentCoord){
    	List<Coordinate> adjacentRooms = new ArrayList<Coordinate>();
    	Coordinate tempCoord = new Coordinate(0,0);
    	
    	
		//Case de droite
    	tempCoord.setX(currentCoord.getX() + 1);
    	tempCoord.setY(currentCoord.getY());
		if(!(currentCoord.getX() == 21) && (map.getMap().get(tempCoord).getType() != RoomType.Condemned)){
			adjacentRooms.add(new Coordinate(tempCoord.getX(), tempCoord.getY()));
		}
		
		//Case de gauche
    	tempCoord.setX(currentCoord.getX() - 1);
		if(!(currentCoord.getX() == 0) && (map.getMap().get(tempCoord).getType() != RoomType.Condemned)) {
			adjacentRooms.add(new Coordinate(tempCoord.getX(), tempCoord.getY()));
		}
		
		//Case du haut
    	tempCoord.setX(currentCoord.getX());
    	tempCoord.setY(currentCoord.getY() - 1);
		if(!(currentCoord.getY() == 0) && (map.getMap().get(tempCoord).getType() != RoomType.Condemned)) {
			adjacentRooms.add(new Coordinate(tempCoord.getX(), tempCoord.getY()));
		}
		
		//Case du bas
    	tempCoord.setY(currentCoord.getY() + 1);
		if(!(currentCoord.getY() == 15) && (map.getMap().get(tempCoord).getType() != RoomType.Condemned)) {
			adjacentRooms.add(new Coordinate(tempCoord.getX(), tempCoord.getY()));
		}
    	return adjacentRooms;
    }
    
    private void moveCharacterTo(Coordinate coord, Player player) {	//Déplace le personnage sur une case
    	//Si le joueur quitte un spawn, on condamne la salle
    	if(map.getMap().get(player.getCharacter().getCoordinate()).getType().equals(RoomType.AlienSpawn) || map.getMap().get(player.getCharacter().getCoordinate()).getType().equals(RoomType.MarineSpawn)) {
    		map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.Condemned);
    	}
    	
    	//On change les coordonnées du joueur
    	player.getCharacter().setCoordinate(coord);
    }
    
    private void unsafeDraw(Player player) {	//Utiliser random() pour effectuer les actions des unsafes room
    	Random rand = new Random(); 
    	int nombreAleatoire = rand.nextInt(3) + 1;
    	switch(nombreAleatoire) {
    		case 1:	//Silence
    			cli.silentCase();
    			break;
    		case 2:
    			cli.unluckyCase(player.getCharacter().getCoordinate());	//Case malchance
    			break;
    		case 3:
    			cli.unluckyCase(cli.askPlayerWhereMakeNoise(map.getHeight(),map.getWidth()));	//Case chance
    			break;
    			
    	}
    }
    
    private void openCapsule(Player player) {	//Résoudre l'ouverture d'une capsule
    	cli.playerOpensCapsule(player);
    	Random rand = new Random(); 
    	int nombreAleatoire = rand.nextInt(5) + 1;
    	
    	if((nombreAleatoire == 1) && !(player.getCharacter() instanceof Engineer)) {
    		map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.Condemned);
    		cli.capsuleNotWorking();
    	}else {
    		player.getCharacter().setEscaped(true);
    		player.getCharacter().setPlaying(false);
    		map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.Condemned);
    		cli.capsuleWorking();
    	}
    }


}

