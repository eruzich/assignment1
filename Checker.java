package gp;

import java.awt.Color;

/*
 * Defines checkers as having coordinates, being a color, having
 * possible moves, and being kinged/not kinged.
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 */
public class Checker {

	private boolean isKing;
	private Color color;
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
	
	public Color getColor() {
		return color;
	}
	
	public void getPossibleMoves() {
		
	}
	
	@Override
    public String toString() {
        return name + "(" + key + ")";
    }
	
	
}
