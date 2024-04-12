package gp;

import edu.princeton.cs.algs4.BinarySearchST;

/**
 * Player class for Checkers game
 * @author Erin Mortensen and Elizabeth Ruzich
 *
 */
public class Player
{

	private BinarySearchST<Coordinates, Checker> checkerPieces;

	/**
	 * Sets the checkers this player uses
	 * @param checkerPieces
	 */
	public Player(BinarySearchST<Coordinates, Checker> checkerPieces)
	{
		this.checkerPieces = checkerPieces;
	}

}
