package gp;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.DrawListener;
import edu.princeton.cs.algs4.RedBlackBST;

public class CheckersGUI implements DrawListener

{
	private Draw drawnBoard;
	private RedBlackBST<Coordinates, Checker> whiteCheckers;
	private RedBlackBST<Coordinates, Checker> redCheckers;
	private CheckerBoard board;
	private GameOfCheckers game;

	/**
	 * Constructs the graphic user interface we will use for the player to play the
	 * game
	 * 
	 * @param board The checkerboard used in the game
	 * @param game  The current game we are playing
	 */
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

	/**
	 * Draw the play area for the game. This includes the checker board who's turn
	 * it is, and our submit move button
	 */
	public void drawPlayArea()
	{
		drawBackground();
		drawSubmit();
		drawCheckerBoard();
		drawWhoseTurn();

	}
	
	public void drawBackground()
	{
		drawnBoard.setPenColor(Draw.WHITE);
		drawnBoard.rectangle(5, 5, 5, 5);
		
	}

	/**
	 * Draw the button we use to submit queued moves for the player
	 */
	public void drawSubmit()
	{
		int x = 9;
		int y = 4;

		drawnBoard.setPenColor(Draw.LIGHT_GRAY);
		drawnBoard.filledRectangle(x, y, .7, .5);
		drawnBoard.setPenColor(Draw.BLACK);
		drawnBoard.text(x, y, "Submit Move");

	}

	/**
	 * Draw whose turn it is.
	 */
	public void drawWhoseTurn()
	{
		if (game.getWhoseTurn() == Draw.RED)
		{

			drawnBoard.setPenColor(Draw.CYAN);
			drawnBoard.filledRectangle(9, 1, .7, .5);
			drawnBoard.filledRectangle(9, 1, .7, .5);
			drawnBoard.setPenColor(Draw.BLACK);
			drawnBoard.text(9, 1, game.announceTurn(Draw.RED));
		}
		else
		{
			drawnBoard.setPenColor(Draw.CYAN);
			drawnBoard.filledRectangle(9, 1, .7, .5);
			drawnBoard.filledRectangle(9, 1, .7, .5);
			drawnBoard.setPenColor(Draw.BLACK);
			drawnBoard.text(9, 1, game.announceTurn(Draw.WHITE));
		}
	}

	/**
	 * Draw the checker board and all the checker pieces
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

			if (redCheckers.get(coor).getIsKing())
			{
				drawKingedCheckers(coor);
				drawnBoard.setPenColor(Draw.RED);
			}


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
			if (whiteCheckers.get(coor).getIsKing())
			{
				drawKingedCheckers(coor);
				drawnBoard.setPenColor(Draw.WHITE);
			}

		}

	}

	/**
	 * Performs different actions based on where on the play area we click If a user
	 * clicks on the checker board they are allowed to select places to move on the
	 * board.
	 * 
	 * If they click the area of the board that has the submit button a move is
	 * submitted
	 */
	public void mouseClicked(double x, double y)
	{
		Coordinates coord = new Coordinates((int) x, (int) y);

		// if mouse click is within the checker board area
		if (x < 8 && y < 8)
		{
			game.pickPlacesToMove(new Coordinates((int) x, (int) y));
			// color selected checker yellow

		}

		// if mouse click is within the submit button area
		if (x >= 8.5 && x <= 9.5 && y >= 3.5 && y <= 4.5)
		{
			game.submitMove();
			game.checkIfGameOver();
		}

		// if game ends, ask if player wants to start new game
		if (game.getGameOverStatus() == true)	
		{
			Font smallFont = drawnBoard.getFont();
			String winnerName = "";
			if(game.getWinner() == Draw.RED)
			{
				winnerName = "RED";
			}
			else if(game.getWinner() == Draw.WHITE)
			{
				winnerName = "WHITE";
			}
		
			drawnBoard.setPenColor(Draw.BLACK);
			drawnBoard.setFont(new Font("SansSerif", Font.BOLD, 50));
			drawnBoard.text(2, 9, winnerName);
			drawnBoard.text(8, 9, "WON!");
			
			drawnBoard.setFont(smallFont);
			drawnBoard.setPenColor(Draw.YELLOW);
			drawnBoard.filledRectangle(5.0, 8.7, 1.0, 0.5);
			drawnBoard.setPenColor(Draw.BLACK);
			drawnBoard.rectangle(5.0, 8.7, 1.0, 0.5);
			drawnBoard.text(5.0, 8.7, "Play Again?");
			drawnBoard.text(5.0, 8.5, "Press to start again.");
			if (x >= 4.0 && x <= 6.0 && y >= 8.0 && y <= 9.0)
			{
				board = null;
				board = new CheckerBoard();
				game.setBoard(board);
				game.setWinner(null);
				game.setWhoseTurn(Draw.RED);
				game.resetMovesToTake();
				whiteCheckers = board.getWhiteCheckers();
				redCheckers = board.getRedCheckers();
				game.checkIfGameOver();
				drawnBoard.clear();
				drawPlayArea();
			

			}
		}
	}

	/**
	 * Draw the possible moves that a checker can go to on the board
	 * 
	 * @param coord Coordinate of the checker we will draw the possible moves for
	 */
	public void drawPossibleMoves(Coordinates coord)
	{
		drawnBoard.setPenColor(Draw.YELLOW);
		double radius = 0.4;
		double offSet = 0.5;
		drawnBoard.filledCircle(coord.getX() + offSet, coord.getY() + offSet, radius);

		// color possible moves with yellow outline
		CheckerBoard board = game.getBoard();
		board.buildPossibleMoveGraph(coord);
		ArrayList<Integer> possibleMoves = board.getPossibleMoves(coord);
		for (int i = 0; i < possibleMoves.size(); i++)
		{
			Coordinates coord2 = board.integerToCoordinate(possibleMoves.get(i));
			drawnBoard.circle(coord2.getX() + offSet, coord2.getY() + offSet, radius);
		}
	}

	/**
	 * Draw a Kinged Checker
	 * 
	 * @param c Coordinate of the Kinged Checker
	 */
	public void drawKingedCheckers(Coordinates c)
	{
		double radius = 0.4;
		double offSet = 0.5;
		int x = c.getX();
		int y = c.getY();

		drawnBoard.setPenColor(Draw.BLACK);
		drawnBoard.text(x + offSet, y + offSet, "K");
	}

}
