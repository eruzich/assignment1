package gp;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Creates a game of checkers and includes the main method for running the program.  Determines 
 * when a size of the symbol table for white checkers or red checkers is empty in order to declare
 * the game over and display a box asking if the user wants to play again.
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 */
public class GameOfCheckers 
{

	private Player winner;
	private static boolean isPlaying = true;
	private Player redPlayer;
	private Player whitePlayer;
	
	private static boolean mousePressed = false;
	
	/**
	* Constructs a new Game of Checkers.
	*
	*/
	public GameOfCheckers() 
	{
	
	}
	
	/**
	 * Initiates the game
	 */
	public void playGame() 
	{
		isPlaying = true;
	}

	public static void main(String[] args) 
	{
		CheckerBoard cb = new CheckerBoard();

		cb.printBoard();

		System.out.println();

		//Coordinates c = cb.getCoordinate(4, 5);
		
		System.out.println();
//		cb.deleteChecker(0, 1);
		
		//cb.getAllPossibleMoves(c);
		
		//cb.printPossibleMoves(c);
		cb.drawCheckerBoard();
		
		//cb.getAllPossibleMoves(new Coordinates(2,5));
		//cb.printPossibleMoves(new Coordinates(2,5));
		
		//cb.move(new Coordinates(2,5), new Coordinates(2,1));
		//StdDraw.pause(1000);
		//cb.drawCheckerBoard();
	
		
		//colors a checker in response to receiving user input
		while (isPlaying == true) 
		{
			//conditioned on being a valid location for one of the checkers
			if (StdDraw.isMousePressed() && !mousePressed) 
			{
				mousePressed = true;
				int x = (int)StdDraw.mouseX();
				int y = (int)StdDraw.mouseY();
				Coordinates coord = new Coordinates(x, y);
				
				//if coordinate corresponds to a location of a checker, color checker and show next moves
				if (cb.redCheckers.contains(coord) || cb.whiteCheckers.contains(coord))
				{
					//color selected checker yellow
					StdDraw.setPenColor(StdDraw.YELLOW);
					double radius = 0.4;
				    double offSet = 0.5;
					StdDraw.filledCircle(x + offSet, y + offSet, radius);
					
					//color possible moves with yellow outline
					cb.getAllPossibleMoves(coord);
					cb.printPossibleMoves(coord);
					cb.getPossibleMoves(coord);
					ArrayList<Integer> possibleMoves = cb.getPossibleMoves(coord);
					System.out.println(possibleMoves);
					for (int i = 0; i < 10; i++) {
						//Coordinates coord2 = cb.integerToCoordinate(possibleMoves[i]);
						//StdDraw.circle(coord2.getX() + offSet, coord2.getY() + offSet, radius);
					}
				}
				//else print an error message
				else 
				{		
					StdOut.print("There's no checker there.  Select again.");
				}
			}
		}
		//determines end of game
		if (cb.getRedCheckers().size() == 0 || cb.getWhiteCheckers().size() == 0) 
		{
			isPlaying = false;
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.filledSquare(0.5, 0.4, 0.3);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.square(0.5, 0.4, 0.3);
			StdDraw.text(0.5, .4, "Play Again?  Press to start again.");
			while (isPlaying == false) 
			{
				if (StdDraw.isMousePressed() && !mousePressed) 
				{
					CheckerBoard cb1 = new CheckerBoard();
				}
			}
		}
	}
}