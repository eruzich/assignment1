package gp;

import java.util.ArrayList;

import javax.swing.JFrame;
import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.Digraph;
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
		drawnBoard.filledRectangle(x, y, .5, .5);
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

	public void mouseClicked(double x, double y)
	{
		System.out.println(x + ", " + y);

		if (x < 8 && y < 8)
		{
			game.pickPlacesToMove(new Coordinates((int) x, (int) y));
		}

		if (x >= 8.5 && x <= 9.5 && y >= 3.5 && y <= 4.5)
		{
			System.out.println("Clicking submit button");
			game.submitMove();

		}

	}

	public void drawPossibleMoves(Coordinates coord)
	{
		Checker checkerWithMoves;

		if (board.getRedCheckers().contains(coord) || board.getWhiteCheckers().contains(coord))
		{
			// color selected checker yellow
			drawnBoard.setPenColor(Draw.YELLOW);
			double radius = 0.4;
			double offSet = 0.5;
			drawnBoard.filledCircle(coord.getX() + offSet, coord.getY() + offSet, radius);

			// color possible moves with yellow outline
			board.getAllPossibleMoves(coord);
			board.printPossibleMoves(coord);
			board.getPossibleMoves(coord);
			ArrayList<Integer> possibleMoves = board.getPossibleMoves(coord);
			for (int i = 0; i < possibleMoves.size(); i++)
			{
				Coordinates coord2 = board.integerToCoordinate(possibleMoves.get(i));
				drawnBoard.circle(coord2.getX() + offSet, coord2.getY() + offSet, radius);
			}
		}
		// else print an error message
		else
		{
			StdOut.print("There's no checker there.  Select again.");
		}

	}
}
