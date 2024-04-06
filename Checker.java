package gp;

public class Checker {

	private boolean isKing;
	//private Std.Color color;
	//private Graph possibleMoves;
	int key;
	String name;
	
	/**
	* Constructs a new Checker	
	*/
	public Checker(int key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public void kingMe() {
		
	}
	
	public void getPossibleMoves() {
		
	}
	
	@Override
    public String toString() {
        return name + "(" + key + ")";
    }
	
	
}
