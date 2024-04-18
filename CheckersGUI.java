package gp;

import java.util.ArrayList;

import javax.swing.JFrame;
import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.DrawListener;
import edu.princeton.cs.algs4.StdOut;

public class CheckersGUI implements DrawListener

{
	private Draw drawnBoard;
	private BinarySearchST<Coordinates, Checker> whiteCheckers;
	private BinarySearchST<Coordinates, Checker> redCheckers;
	private CheckerBoard board;
	private GameOfCheckers game;

	public CheckersGUI(CheckerBoard board, GameOfCheckers game)
	{
		drawnBoard = new Draw();
		drawnBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawnBoard.setCanvasSize(800, 800);
		drawnBoard.setScale(0, 10);
		drawnBoard.addListener(this);
		whiteCheckers = board.getWhiteCheckers();
		redCheckers = board.getRedCheckers();
		this.game = game;
	}

	public void drawPlayArea()
	{
		drawSubmit();
		drawCheckerBoard();
	}

	public void drawSubmit()
	{
		int x = 9;
		int y = 4;

		drawnBoard.setPenColor(Draw.CYAN);
		drawnBoard.filledRectangle(x, y, .7, .5);
		drawnBoard.setPenColor(Draw.BLACK);
		drawnBoard.text(x, y, "submit move");
		
	}

	/**
	 * Use Draw to draw the checkerboard and all the checker pieces
	 */
	public void drawCheckerBoard()
	{
		int n = 8;

		for (int i = n - 1; i >= 0; i--)
		{
			for (int j = n - 1; j >= 0; j--)
			{
				if ((i + j) % 2 != 0)
				{
					drawnBoard.setPenColor(Draw.BLACK);
				}
				else
				{
					drawnBoard.setPenColor(Draw.RED);
				}
				drawnBoard.filledSquare(i + 0.5, j + 0.5, 0.5);
			}
		}
		drawWhiteCheckers();
		drawRedCheckers();
	}

	/**
	 * Draw red checkers
	 */
	public void drawRedCheckers()
	{
		if (redCheckers.isEmpty())
		{
			return;
		}
		double radius = 0.4;
		double offSet = 0.5;

		drawnBoard.setPenColor(Draw.RED);

		for (Coordinates coor : redCheckers.keys())
		{
			int x = coor.getX();
			int y = coor.getY();

			drawnBoard.filledCircle(x + offSet, y + offSet, radius);
		}
	}

	/**
	 * Draw white checkers
	 */
	public void drawWhiteCheckers()
	{
		if (whiteCheckers.isEmpty())
		{
			return;
		}
		drawnBoard.setPenColor(Draw.WHITE);
		double radius = 0.4;
		double offSet = 0.5;

		for (Coordinates coor : whiteCheckers.keys())
		{
			int x = coor.getX();
			int y = coor.getY();

			drawnBoard.filledCircle(x + offSet, y + offSet, radius);
		}
	}

	/**
	 * Identifies location of mouse click and prints out x and y values7
	 */
	public void mouseClicked(double x, double y)
	{
		Coordinates coord = new Coordinates((int) x, (int) y);
		System.out.println(x + ", " + y);

		//if mouse click is within the checker board area
		if (x < 8 && y < 8)
		{
			game.pickPlacesToMove(new Coordinates((int) x, (int) y));
			// color selected checker yellow
			drawnBoard.setPenColor(Draw.YELLOW);
			double radius = 0.4;
			double offSet = 0.5;
			drawnBoard.filledCircle(coord.getX() + offSet, coord.getY() + offSet, radius);

			// color possible moves with yellow outline
			CheckerBoard board = game.getBoard();
			board.getAllPossibleMoves(coord);
			ArrayList<Integer> possibleMoves = board.getPossibleMoves(coord);
			for (int i = 0; i < possibleMoves.size(); i++)
			{
				Coordinates coord2 = board.integerToCoordinate(possibleMoves.get(i));
				drawnBoard.circle(coord2.getX() + offSet, coord2.getY() + offSet, radius);
			}
		}

		//if mouse click is within the submit button area
		if (x >= 8.5 && x <= 9.5 && y >= 3.5 && y <= 4.5)
		{
			System.out.println("Clicking submit button");
			game.submitMove();
		}

		//if game ends, ask if player wants to start new game
		//I don't know why this is triggered when game.pickPlacesToMove is activated
		if (game.checkIfGameOver() == true)	
		{
			drawnBoard.setPenColor(Draw.YELLOW);
			drawnBoard.filledRectangle(5.0, 8.7, 1.0, 0.5);
			drawnBoard.setPenColor(Draw.BLACK);
			drawnBoard.rectangle(5.0, 8.7, 1.0, 0.5);
			drawnBoard.text(5.0, 8.7, "Play Again?");
			drawnBoard.text(5.0, 8.5, "Press to start again.");
			if (x >= 4.0 && x <= 6.0 && y >= 8.0 && y <= 9.0)
			{
				Draw playAgain = new Draw();
				board = new CheckerBoard();
				//winner = null;	
				//redPlayer = new Player(board.getRedCheckers());
				//whitePlayer = new Player(board.getWhiteCheckers());
				//isPlaying = true;				
			}	
		}
	}

	public void drawPossibleMoves()
	{
		Checker checkerWithMoves;
	}
	
	public void drawKingedCheckers(Coordinates c)
	{
		double radius = 0.4;
		double offSet = 0.5;
		int x = c.getX();
		int y = c.getY();
		
		drawnBoard.filledCircle(x + offSet, y + offSet, radius);
		drawnBoard.text(x, y, "K");
	}
	
	
	
}