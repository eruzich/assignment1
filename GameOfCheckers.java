package gp;

import edu.princeton.cs.algs4.StdDraw;

public class GameOfCheckers 
{

	private Player winner;
	private static boolean isPlaying = true;
	private CheckerBoard board;
	private Player redPlayer;
	private Player whitePlayer;
	
	
	private static boolean mousePressed = false;
	
	/**
	* Constructs a new Game of Checkers.
	*
	*/
	public GameOfCheckers() 
	{
		winner = null;
		board = new CheckerBoard();
		redPlayer = new Player(board.getRedCheckers());
		whitePlayer = new Player(board.getWhiteCheckers());
		
	
	}
	
	/**
	 * Returns board for the game
	 * @return Returns game board
	 */
	public CheckerBoard getBoard()
	{
		return board;
	}
	
	public void playGame() 
	{
		//isPlaying = true;
	}

	public static void main(String[] args) 
	{
		GameOfCheckers game = new GameOfCheckers();
		CheckerBoard cb = game.getBoard();
				
		cb.printBoard();

		cb.drawCheckerBoard();
	
		
		while (isPlaying == true) 
		{
			
			//to do - needs to be conditioned on being a valid location for one of the checkers
			if (StdDraw.isMousePressed() && !mousePressed) 
			{
				
				mousePressed = true;
				int x = (int)StdDraw.mouseX();
				int y = (int)StdDraw.mouseY();
				Coordinates coord = new Coordinates(x, y);
				
				StdDraw.setPenColor(StdDraw.YELLOW);
				double radius = 0.4;
		        double offSet = 0.5;
				StdDraw.filledCircle(x + offSet, y + offSet, radius);
				cb.findAllPossibleMoves(coord);
				cb.printPossibleMoves(coord);	
				isPlaying = false;
			}
			
		

		}
		
		//show how moves work
		cb.test1Board();
		
		StdDraw.pause(3000);
		cb.drawCheckerBoard();
		StdDraw.pause(3000);
		cb.findAllPossibleMoves(new Coordinates(2,5));
		cb.move(new Coordinates(2,5), new Coordinates(2,1));		

		
		
		//let player play again if all red checkers gone
		if (cb.getRedCheckers().size() == 0) 
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
