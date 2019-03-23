package spaceescape.model.character;

public class Soldier extends Marine{
	
	private boolean canAttack;

	public Soldier() {
		super(true, "Soldier");
		this.canAttack = true;
	}
	
	public void setCanAttack(boolean bool) {
		this.canAttack = bool;
	}
	
	public boolean getCanAttack() {
		return canAttack;
	}
	
	public String toString() {
		return "Soldier";
	}
}
