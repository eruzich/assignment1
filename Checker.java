package gp;

public class Checker {

	private boolean isKing;
	//private Std.Color color;
	//private Graph possibleMoves;
	int key;
	String name;
	Coordinates coord;
	
	/**
	* Constructs a new Checker	
	*/
	public Checker(int key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public void kingMe() {
		if ((name == "rC") & (coord.getY() == 0)) {
			isKing = true;
		}
		else if ((name == "wC") & (coord.getY() == 7)) {
			isKing = true;
		}
		else
			isKing = false;
	}
	
	public void getPossibleMoves() {
		
	}
	
	@Override
    public String toString() {
        return name + "(" + key + ")";
    }
	
	
}
