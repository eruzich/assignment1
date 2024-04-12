package gp;

import java.awt.Color;

import edu.princeton.cs.algs4.Digraph;

/*
 * Used to create a Checker object
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 */
public class Checker {
	
	private Color color;
	private boolean isKing;
	private Digraph possibleMoves;
	private boolean isSelected;
	
	/**
	 * Constructs a new Checker that is either white or red.
	 * 
	 * @param color
	 */
	public Checker(Color color)
	{
		possibleMoves = new Digraph(64);
		this.color = color;
		isKing = false;
		isSelected = false;
	}
	
	/**
	 * Makes it so checker isSelected is true
	 */
	public void selectChecker()
	{
		isSelected = true;	
	}
	
	/**
	 * Returns true if the checker is selected
	 * @return if the checker is selected
	 */
	public boolean checkerIsSelected()
	{
		return isSelected;
	}
	
	/**
	 * Makes a checker a king
	 */
	public void kingMe()
	{
		isKing = true;
	}
	
	/**
	 * Return if checker is a king
	 * @return Returns true if checker is a king
	 */
	public boolean getIsKing()
	{
		return isKing;
	}
	
	/**
	 * Returns the Digraph for the checker indicating all of the possible
	 * moves it can currently take
	 * @return returns digraph of possible moves
	 */
	public Digraph getPossibleMoves()
	{
		return possibleMoves;
	}
	
	/**
	 * Add an edge to a checker's directed graph of possible moves
	 * @param start vertex to start at
	 * @param end edge we are adding
	 */
	public void setPossibleMoves(int start, int end)
	{
		possibleMoves.addEdge(start, end);
	}

	/**
	 * Resets the checker's directed graph so it is empty
	 */
	public void resetPossibleMoves()
	{
		possibleMoves = null;
		possibleMoves = new Digraph(64);
	}
	/**
	 * Get the checker's color
	 * @return Returns color of checker
	 */
	public Color getColor()
	{
		return color;
	}
	
	
}
