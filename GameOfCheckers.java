package gp;

import edu.princeton.cs.algs4.StdDraw;

public class GameOfCheckers {

	private Player winner;
	private static boolean isPlaying = true;	//I made this positive, because the double negative of not over was confusing me
	private CheckerBoard checkerBoard;
	private Player redPlayer;
	private Player whitePlayer;
	
	private static boolean mousePressed = false;
	
	/**
	* Constructs a new Game of Checkers.
	*
	* 	*/
	public GameOfCheckers() {
	}
	
	public void playGame() {
		
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
		
		while (isPlaying == true) {
			if (StdDraw.isMousePressed() && !mousePressed) {
				mousePressed = true;
				int x = (int)StdDraw.mouseX();
				int y = (int)StdDraw.mouseY();
				//System.out.println("x: " + x);
				//System.out.println("y: " + y);
				Coordinates coord = new Coordinates(x, y);
				
				StdDraw.setPenColor(StdDraw.YELLOW);
				double radius = 0.4;
		        double offSet = 0.5;
				StdDraw.filledCircle(x + offSet, y + offSet, radius);
				cb.getAllPossibleMoves(coord);
				cb.printPossibleMoves(coord);	
			}
		}
	}
}
