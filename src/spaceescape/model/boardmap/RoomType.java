package spaceescape.model.boardmap;

public enum RoomType {
	Condemned("*"), 
	Safe("_"),
	Unsafe("-"),
	Capsule("O"),
	UsedCapsule("H"),
	DeficientCapsule("#"),
	AlienSpawn("A"),
	MarineSpawn("M");
	
	private String signe = "";
	
	private RoomType(String signe) {
		this.signe = signe;
	}
	
	public String toString() {
		return this.signe;
	}
}
