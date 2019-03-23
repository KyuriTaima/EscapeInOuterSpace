package spaceescape.service;
import java.util.List;
import java.util.Scanner;

import spaceescape.exception.BadParseException;
import spaceescape.model.Player;
import spaceescape.model.boardmap.BoardMap;
import spaceescape.model.boardmap.Coordinate;

public class GameServiceCLI implements GameService {



    @Override
    public void displayPlayerMap(Player player, BoardMap map){
        displayColumn(map);
        for (int y = 0 ; y < map.getHeight() ; y++){
            displayLine(y);
            for(int x = 0 ; x < map.getWidth() ; x++){
                if(player.getCharacter().getCoordinate().equals(new Coordinate(x,y))){
                    System.out.print("X  ");
                } else {
                    System.out.print(map.getMap().get(new Coordinate(x,y)));
                    System.out.print("  ");
                }
            }
            System.out.println();

        }
        System.out.println("You are a " + player.getCharacter().toString());
    }

    @Override
    public Coordinate askPlayerWhereMove(Player player, List<Coordinate> possibleMove) {
        Scanner entry;
        Coordinate choosingMove = null;
        do{
            System.out.println("Where do you want to displace? you are on: " + player.getCharacter().getCoordinate().toString());
            System.out.println(possibleMove);
            entry = new Scanner(System.in);
            try{
                choosingMove = parseToCoordinate(entry.next());
            } catch (BadParseException e){
                System.out.println("Don't forget ':' between coordinate.");
            } catch (NumberFormatException e){
                System.out.println("Only numbers please.");
            }
        } while (choosingMove == null || !possibleMove.contains(choosingMove));
        return choosingMove;
    }

    @Override
    public Coordinate askPlayerWhereMakeNoise(int mapHeight, int mapWidth) {
        Scanner entry;
        Coordinate choosingRoom = null;
        do{
            System.out.println("Pick a room where make noise (all map):");
            entry = new Scanner(System.in);
            try{
                choosingRoom = parseToCoordinate(entry.next());
            } catch (BadParseException e){
                System.out.println("Don't forget ':' between coordinate.");
            } catch (NumberFormatException e){
                System.out.println("Only numbers please.");
            }
        } while (choosingRoom == null || choosingRoom.getX() < 0 || choosingRoom.getX() >= mapWidth || choosingRoom.getX() < 0 || choosingRoom.getY() >= mapWidth);
        return choosingRoom;
    }
    
    public int askPlayerAction() {
        Scanner entry = new Scanner(System.in);
        int choice = 0;
        do {
        System.out.println("What will you do with your turn:\n1. Move\n2. Move and attack !\n");
        choice = entry.nextInt();
        }while(!(choice == 1 || choice == 2));
        return choice;
    }
    
    public void capsuleNotWorking() {
    	System.out.println("Launching error, ionic boosters are deficient... czzz...czzz");
    }
    
    public void capsuleWorking() {
    	System.out.println("You managed to flee to the Earth !");
    }
    
    public void unluckyCase(Coordinate coord) {
    	System.out.println("A strange noise is coming from room : " + coord.toString());
    }
    
    public void deathCase(Player killer, Player victim) {
    	System.out.println(killer.getName() + " killed " + victim.getName() + " without mercy !");
    }
    
    public void safeCase(Player player) {
    	System.out.println(player.getName() + " just moved in a safe room");
    }
    
    public void silentCase() {
    	System.out.println("*Silence*");
    }
    public void startGame() {
    	System.out.println("\n				The hunt has started !\n");
    }
    public void endGame() {
    	System.out.println("			End of the hunt !");
    }
    public void playerEscaped(Player player) {
    	System.out.println(player.getName() + " managed to escape !");
    }
    public void playerDead(Player player) {
    	System.out.println(player.getName() + " is dead !");
    }
    public void playerIsPlaying(Player player) {
    	System.out.println(player.getName() + " is still lurking in the shadows...");
    }
    public void playerOpensCapsule(Player player) {
    	System.out.println(player.getName() + " est en train d'ouvrir une capsule en :" + player.getCharacter().getCoordinate().toString());

    }

    /////////////////////
     // Private Section //
    /////////////////////

    private void displayColumn(BoardMap map){
        System.out.print("   ");
        for (int i = 0 ; i < map.getWidth() ; i++){
            if(i < 10){
                System.out.print(i  + "  ");

            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private void displayLine(int line){
        if(line < 10){
            System.out.print(line + "  ");

        } else {
            System.out.print(line + " ");
        }
    }

    private static Coordinate parseToCoordinate (String coordinateString) throws BadParseException {
        String[] parse = coordinateString.split(":");
        if (parse.length != 2 ){
            throw new BadParseException();
        } else {
            return new Coordinate(Integer.valueOf(parse[0]), Integer.valueOf(parse[1]));
        }
    }

	@Override
	public void endGame(List<Player> winners) {
		
		
	}
    

}
