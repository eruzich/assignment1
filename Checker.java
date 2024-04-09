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
	
	public void selectChecker()
	{
		isSelected = true;	
	}
	
	public boolean getIsKing()
	{
		return isKing;
	}
	
	public void kingMe()
	{
		isKing = true;
	}
	
	public Digraph getPossibleMoves()
	{
		return possibleMoves;
	}
	
	public void setPossibleMoves(int start, int end)
	{
		possibleMoves.addEdge(start, end);
	}

	public Color getColor()
	{
		return color;
	}
}