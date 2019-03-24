package spaceescape.web;


import java.util.*;
import java.util.stream.Collectors;

import spaceescape.model.Player;
import spaceescape.model.alien.Alien;
import spaceescape.model.alien.Lurker;
import spaceescape.model.alien.Praetorian;
import spaceescape.model.boardmap.BoardMap;
import spaceescape.model.boardmap.Coordinate;
import spaceescape.model.boardmap.RoomType;
import spaceescape.model.character.Engineer;
import spaceescape.model.character.Soldier;
import spaceescape.service.GameService;

public class WebGame {

    private String name;
    private BoardMap map;
    private static List<Player> players = new ArrayList<>();
    private static int activePlayerRank = 0;
    private int turn;
    private static WebGame webgame;
    private static List<String> messages = new ArrayList<>();


    public WebGame(String name, BoardMap map, List<Player> players) {
        this.name = name;
        this.map = map;
        this.players.addAll(players);
        this.turn = 0;
    }

    public WebGame(String name, List<Player> players) {
        this(name,new BoardMap(), players);
        initGame();
    }
    
    public static void config(String name, List<Player> players) {
    	webgame = new WebGame(name, players);
    }
    
    public static WebGame getInstance() {
    	return webgame;
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
    
	private void initGame() {
    	
    	//Allouement des characters aux joueurs
    	boolean gameIsValid = false;
    	while(!gameIsValid) {
	    	for(int i=0; i<players.size(); i++) {
	    		boolean isInit = false;
	    		while(!isInit) {
			    	Random rand = new Random(); 
			    	int nombreAleatoire = rand.nextInt(4) + 1;
			    	
			    	switch(nombreAleatoire) {
				    	case 1:
				    		if(!lurkExists()) {
					    		this.players.get(i).setCharacter(new Lurker());
					    		isInit = true;
				    		}
				    		break;
				    	case 2:
				    		if(!praetExists()) {
					    		this.players.get(i).setCharacter(new Praetorian());
					    		isInit = true;
				    		}
				    		break;
				    	case 3:
				    		if(!soldierExists()) {
					    		this.players.get(i).setCharacter(new Soldier());
					    		isInit = true;
				    		}
				    		break;
				    	case 4:
				    		if(!engineerExists()) {
					    		this.players.get(i).setCharacter(new Engineer());
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
 
	private boolean lurkExists() {
 	for (int i=0; i<players.size(); i++) {
 		if(players.get(i).getCharacter() instanceof Lurker) {
 			return true;
 		}
 	}
		return false;
 }
	private boolean praetExists() {
 	for (int i=0; i<players.size(); i++) {
 		if(players.get(i).getCharacter() instanceof Praetorian) {
 			return true;
 		}
 	}
		return false;
 }
	private boolean soldierExists() {
 	for (int i=0; i<players.size(); i++) {
 		if(players.get(i).getCharacter() instanceof Soldier) {
 			return true;
 		}
 	}
		return false;
 }
	private boolean engineerExists() {
 	for (int i=0; i<players.size(); i++) {
 		if(players.get(i).getCharacter() instanceof Engineer) {
 			return true;
 		}
 	}
		return false;
 }
	
    public String moveAction(Player player) { //Gère l'action de mouvement du joueur
    	String action = new String();
    	switch(map.getMap().get(player.getCharacter().getCoordinate()).getType()) {
    	case Capsule:
    		action = openCapsule(player);
    		break;
    	case Unsafe:
    		action = unsafeDraw(player);
    		break;
    	case Safe:
    		action = player.toString() + " just moved in a safe room";
    		addMessage(action);
    		break;
    	default:
    		break;
    	}
    	return action;
    }
    
    public List<Coordinate> whereCharacterCanGo(Player player){	//Renvoie la liste des coordonnées où le joueur peut se déplacer
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
    
    
	public static Player getActivePlayer(){
        return players.get(activePlayerRank);
    }

    public static void setNextPlayer(){
        activePlayerRank++;
        if(activePlayerRank >= players.size()){
            activePlayerRank = 0;
        }
    }

    public BoardMap getMap(){
        return this.map;
    }

    public boolean gameContinue() {
    	boolean endGame = true;
    	for(int j=0;j<players.size();j++) {
    		if((players.get(j).getCharacter().getPlaying() == true) && ( (players.get(j).getCharacter() instanceof Engineer) || (players.get(j).getCharacter() instanceof Soldier) )){
    			endGame = false;
    		}
    	}
    	return endGame;
    }
    
    public List<Player> getWinners(){
    	List<Player> winners = new ArrayList<>();
    	for(int k=0;k<players.size();k++) {
    		if(players.get(k).getCharacter().getEscaped() == true ) {
    			winners.add(players.get(k));
    		}
    	}
    	if(winners.isEmpty() == true) {
        	for(int k=0;k<players.size();k++) {
        		if(players.get(k).getCharacter() instanceof Alien) {
        			winners.add(players.get(k));
        		}
        	}
    	}
    	return winners;
    }
    
    public String unsafeDraw(Player player) {	//Utiliser random() pour effectuer les actions des unsafes room
    	String action = new String();
    	Random rand = new Random(); 
    	int nombreAleatoire = rand.nextInt(2) + 1;
    	switch(nombreAleatoire) {
    		case 1:	//Silence
    			action = "*Silence*";
    			break;
    		case 2:
    			action = "A strange noise is coming from room " + player.getCharacter().getCoordinate().toString();
    			addMessage(action);
    			break;
    			
    	}
    	return action;
    }
    
    public String openCapsule(Player player) {	//Résoudre l'ouverture d'une capsule
    	String action = new String();
    	//gameService.playerOpensCapsule(player);
    	Random rand = new Random(); 
    	int nombreAleatoire = rand.nextInt(5) + 1;
    	
    	if((nombreAleatoire == 1) && !(player.getCharacter() instanceof Engineer)) {
    		map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.Condemned);
    		action = "The ionic engines are dysfunctionning... czzz .... czzz";
    	}else {
    		player.getCharacter().setEscaped(true);
    		player.getCharacter().setPlaying(false);
    		map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.Condemned);
    		action = "You have escaped !";
    	}
    	addMessage(action);
    	return action;
    }
    
    public List<String> attackAction(Player player) { //Gère l'attaque du joueur
    	List<String> actions = new ArrayList<>();
    	actions.add(player.toString() + " attacked on room " + player.getCharacter().getCoordinate().toString());
    	for(int i=0;i<players.size();i++) {
    		if(players.get(i).getCharacter().getCoordinate().equals(player.getCharacter().getCoordinate())) {
    			if(!(players.get(i).getCharacter() instanceof Praetorian) && !(player.equals(players.get(i)))) {
    				players.get(i).getCharacter().setAlive(false);
    				players.get(i).getCharacter().setPlaying(false);
    				players.get(i).getCharacter().setCoordinate(new Coordinate(10,6));
    				actions.add(player.toString() + " killed " + players.get(i).toString());
    			}
    		}
    	}
    	if(player.getCharacter() instanceof Soldier) {
    		((Soldier) player.getCharacter()).setCanAttack(false);
    		actions.add(player.toString() + " cannot attack anymore...");
    	}
    	messages.addAll(actions);
    	return actions;
    }
    
    public void moveCharacterTo(Coordinate coord, Player player) {	//Déplace le personnage sur une case
    	//Si le joueur quitte un spawn, on condamne la salle
    	if(map.getMap().get(player.getCharacter().getCoordinate()).getType().equals(RoomType.AlienSpawn) || map.getMap().get(player.getCharacter().getCoordinate()).getType().equals(RoomType.MarineSpawn)) {
    		map.getMap().get(player.getCharacter().getCoordinate()).setType(RoomType.Condemned);
    	}
    	
    	//On change les coordonnées du joueur
    	player.getCharacter().setCoordinate(coord);
    }
    
    public List<Player> getPlayers(){
    	return players;
    }
    
    public void addMessage(String message) {
    	messages.add(message);
    }
    
    public List<String> getMessages(){
    	return messages;
    }
}
