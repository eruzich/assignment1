package gp;

import java.awt.Color;

import edu.princeton.cs.algs4.Digraph;


public class Checker {
	
	private Color color;
	private boolean isKing;
	private Digraph possibleMoves;
	private boolean isSelected;
	
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
