package spaceescape.model.boardmap;

public class Room {

	private RoomType type;
	private Placeable placeables;
	
	
	
	public Room(RoomType type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return type.toString();
	}



	public void setType(RoomType type) {
		this.type = type;
	}
	
	public RoomType getType() {
		return this.type;
	}
	
	
}
