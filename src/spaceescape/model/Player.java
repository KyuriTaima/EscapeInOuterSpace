package spaceescape.model;
import spaceescape.model.character.Character;

public class Player {

	private String surname;
	private boolean win;
	private Character character;
	
	public Player(String surname) {
		super();
		this.surname = surname;
		this.win = false;
		this.character = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	
	public Character getCharacter() {
		return this.character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public String getName() {
		return this.surname;
	}
	
	

	
	
}
