package gp;


/*
 * Represents the x and y coordinates of a checkerpiece in 2D space.
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 */

public class Coordinates {
	private int x;
	private int y;

	/**
	* Constructs a new Coordinate with specified x and y coordinates.
	*
	* @param x the x coordinate
	* @param y the y coordinate
	*/
	public Coordinates(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
	    return y;
	}
	
	/**
     * {@inheritDoc}
     * The string has the following format: (x, y).
     * 
     * @return a string representation of the Coordinates object
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


}
