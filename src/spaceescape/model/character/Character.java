package spaceescape.model.character;

import spaceescape.model.boardmap.Coordinate;
import spaceescape.model.boardmap.Placeable;

public abstract class Character implements Placeable{

	private int movement;
	private boolean canAttack;
	private boolean alive;
	private String name;
	private boolean escaped;
	private Coordinate coordinate;
	private boolean playing;
	
	public Character(int movement, boolean canAttack, String name) {
		super();
		this.movement = movement;
		this.canAttack = canAttack;
		this.name = name;
		this.alive = true;
		this.escaped = false;
		this.coordinate = null;
		this.playing = true;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public void setEscaped(boolean bool) {
		this.escaped = bool;
	}
	
	public void setAlive(boolean bool) {
		this.alive = bool;
	}
	
	public int getMovement() {
		return this.movement;
	}

	public void setPlaying(boolean b) {
		this.playing = b;
	}
	
	public boolean getPlaying() {
		return this.playing;
	}

	public boolean getEscaped() {
		return this.escaped;
	}

	public boolean getAlive() {
		return this.alive;
	}
	
	
	
	
}
