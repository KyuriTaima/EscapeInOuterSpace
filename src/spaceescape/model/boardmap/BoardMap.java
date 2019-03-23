package spaceescape.model.boardmap;

import java.util.HashMap;
import java.util.Map;

import spaceescape.model.boardmap.Coordinate;

public class BoardMap {
    private final java.util.Map<Coordinate, Room> map = new HashMap<>();
    private final int height;
    private final int width;

    private final Coordinate alienSpawn;
    private final Coordinate marineSpawn;

    public BoardMap(){
        this.height = 15;
        this.width = 21;
        for(int x = 0 ; x < width ; x++){
                for(int y = 0 ; y < height  ; y++){
                    this.map.put(new Coordinate(x,y), new Room(RoomType.Unsafe));
                }
            }
        this.map.get(new Coordinate(2,2)).setType(RoomType.Capsule);
        this.map.get(new Coordinate(18,12)).setType(RoomType.Capsule);
        this.map.get(new Coordinate(2,12)).setType(RoomType.Capsule);
        this.map.get(new Coordinate(18,2)).setType(RoomType.Capsule);

        this.marineSpawn = new Coordinate(10,8);
        this.map.get(marineSpawn).setType(RoomType.MarineSpawn);
        this.alienSpawn = new Coordinate(10,6);
        this.map.get(alienSpawn).setType(RoomType.AlienSpawn);

        this.map.get(new Coordinate(8,7)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(9,7)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(10,7)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(11,7)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(12,7)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(6,5)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(5,0)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(0,6)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(0,7)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(0,8)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(1,7)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(20,6)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(20,7)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(20,8)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(19,7)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(2,4)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(3,4)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(4,4)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(3,5)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(15,2)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(15,3)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(15,4)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(16,4)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(12,1)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(12,2)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(16,14)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(15,14)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(3,13)).setType(RoomType.Condemned);


        this.map.get(new Coordinate(5,9)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(5,10)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(6,11)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(7,12)).setType(RoomType.Condemned);

        this.map.get(new Coordinate(16,9)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(16,8)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(15,9)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(15,8)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(14,9)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(14,10)).setType(RoomType.Condemned);
        this.map.get(new Coordinate(15,10)).setType(RoomType.Condemned);


        this.map.get(new Coordinate(9,10)).setType(RoomType.Safe);
        this.map.get(new Coordinate(10,10)).setType(RoomType.Safe);
        this.map.get(new Coordinate(11,10)).setType(RoomType.Safe);

        this.map.get(new Coordinate(19,8)).setType(RoomType.Safe);
        this.map.get(new Coordinate(19,9)).setType(RoomType.Safe);
        this.map.get(new Coordinate(20,9)).setType(RoomType.Safe);
        this.map.get(new Coordinate(20,10)).setType(RoomType.Safe);

        this.map.get(new Coordinate(9,3)).setType(RoomType.Safe);
        this.map.get(new Coordinate(10,3)).setType(RoomType.Safe);
        this.map.get(new Coordinate(11,3)).setType(RoomType.Safe);

        this.map.get(new Coordinate(10,11)).setType(RoomType.Safe);
        this.map.get(new Coordinate(3,10)).setType(RoomType.Safe);
        this.map.get(new Coordinate(17,10)).setType(RoomType.Safe);

        this.map.get(new Coordinate(9,14)).setType(RoomType.Safe);
        this.map.get(new Coordinate(8,14)).setType(RoomType.Safe);
        this.map.get(new Coordinate(10,14)).setType(RoomType.Safe);
        this.map.get(new Coordinate(11,14)).setType(RoomType.Safe);

        this.map.get(new Coordinate(13,14)).setType(RoomType.Safe);
        this.map.get(new Coordinate(14,14)).setType(RoomType.Safe);

        this.map.get(new Coordinate(4,13)).setType(RoomType.Safe);
        this.map.get(new Coordinate(5,13)).setType(RoomType.Safe);

        this.map.get(new Coordinate(0,9)).setType(RoomType.Safe);
        this.map.get(new Coordinate(0,10)).setType(RoomType.Safe);
        this.map.get(new Coordinate(0,11)).setType(RoomType.Safe);

        this.map.get(new Coordinate(3,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(4,0)).setType(RoomType.Safe);

        this.map.get(new Coordinate(0,2)).setType(RoomType.Safe);
        this.map.get(new Coordinate(0,3)).setType(RoomType.Safe);
        this.map.get(new Coordinate(0,4)).setType(RoomType.Safe);
        this.map.get(new Coordinate(0,5)).setType(RoomType.Safe);
        this.map.get(new Coordinate(1,4)).setType(RoomType.Safe);

        this.map.get(new Coordinate(17,4)).setType(RoomType.Safe);
        this.map.get(new Coordinate(16,5)).setType(RoomType.Safe);
        this.map.get(new Coordinate(17,5)).setType(RoomType.Safe);
        this.map.get(new Coordinate(15,5)).setType(RoomType.Safe);

        this.map.get(new Coordinate(14,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(15,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(16,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(17,0)).setType(RoomType.Safe);

        this.map.get(new Coordinate(6,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(7,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(8,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(8,1)).setType(RoomType.Safe);
        this.map.get(new Coordinate(9,1)).setType(RoomType.Safe);
        this.map.get(new Coordinate(10,1)).setType(RoomType.Safe);

        this.map.get(new Coordinate(13,12)).setType(RoomType.Safe);
        this.map.get(new Coordinate(14,12)).setType(RoomType.Safe);
        this.map.get(new Coordinate(15,12)).setType(RoomType.Safe);

        this.map.get(new Coordinate(4,6)).setType(RoomType.Safe);
        this.map.get(new Coordinate(5,6)).setType(RoomType.Safe);
        this.map.get(new Coordinate(4,7)).setType(RoomType.Safe);
        this.map.get(new Coordinate(5,7)).setType(RoomType.Safe);

        this.map.get(new Coordinate(13,9)).setType(RoomType.Safe);
        this.map.get(new Coordinate(6,9)).setType(RoomType.Safe);

        this.map.get(new Coordinate(20,0)).setType(RoomType.Safe);
        this.map.get(new Coordinate(20,1)).setType(RoomType.Safe);
        this.map.get(new Coordinate(20,2)).setType(RoomType.Safe);
        this.map.get(new Coordinate(20,3)).setType(RoomType.Safe);

    }


    public Map<Coordinate, Room> getMap() {
        return map;
    }
    
    

    public Coordinate getAlienSpawn() {
        return alienSpawn;
    }

    public Coordinate getMarineSpawn() {
        return marineSpawn;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
