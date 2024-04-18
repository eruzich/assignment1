package gp;

import java.awt.Color;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Player class for Checkers game
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 *
 */
public class Player
{

	private BinarySearchST<Coordinates, Checker> checkerPieces;
	private Color color;

	/**
	 * Sets the checkers this player uses
	 * 
	 * @param checkerPieces
	 */
	public Player(BinarySearchST<Coordinates, Checker> checkerPieces)
	{
		this.checkerPieces = checkerPieces;

		for (Coordinates c : checkerPieces.keys())
		{
			color = checkerPieces.get(c).getColor();

		}

	}

	public BinarySearchST<Coordinates, Checker> getPlayersCheckers()
	{
		return checkerPieces;
	}

	public Color getColor()
	{
		return color;
	}

}