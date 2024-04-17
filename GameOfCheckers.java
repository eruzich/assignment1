package gp;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Creates a game of checkers and includes the main method for running the
 * program. Determines when a size of the symbol table for white checkers or red
 * checkers is empty in order to declare the game over and display a box asking
 * if the user wants to play again.
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 */
public class GameOfCheckers
{

	int mouseX = 0;
	int mouseY = 0;
	private Player winner;
	private static boolean isPlaying = true;
	private CheckerBoard board;
	private Player redPlayer;
	private Player whitePlayer;
	Queue<Coordinates> movesToTake;
	private Player whoseTurn;
	CheckersGUI gui;

	/**
	 * Constructs a new Game of Checkers.
	 */
	public GameOfCheckers()
	{
		winner = null;
		board = new CheckerBoard();
		redPlayer = new Player(board.getRedCheckers());
		whitePlayer = new Player(board.getWhiteCheckers());
		isPlaying = true;
		movesToTake = new Queue<>();
		whoseTurn = redPlayer;
		gui = new CheckersGUI(board, this);
	}

	/**
	 * Returns board for the game
	 * 
	 * @return Returns game board
	 */
	public CheckerBoard getBoard()
	{
		return board;
	}

	public void checkIfGameOver()
	{
		// determines end of game
//		if (redPlayer.getPlayersCheckers().size() == 0 || whitePlayer.getPlayersCheckers().size() == 0)
//		{
//			isPlaying = false;
//
//			// print winner announcement
//
//			// ask if they want to play again
//			Draw playAgain = new Draw();
//			playAgain.setPenColor(Draw.YELLOW);
//			playAgain.filledSquare(0.5, 0.4, 0.3);
//			playAgain.setPenColor(Draw.BLACK);
//			playAgain.square(0.5, 0.4, 0.3);
//			playAgain.text(0.5, .4, "Play Again?  Press to start again.");
//			while (isPlaying == false)
//			{
//				if (playAgain.isMousePressed())
//				{
//					winner = null;
//					board = new CheckerBoard();
//					redPlayer = new Player(board.getRedCheckers());
//					whitePlayer = new Player(board.getWhiteCheckers());
//					isPlaying = true;
//				}
//			}
//		}
	}

	private void announceTurn(Player player)
	{
		String message;

		if (player.equals(redPlayer))
		{
			message = "Red's Turn";
		}
		else
		{
			message = "White's Turn";
		}

		// need to draw text box and put message in it

	}

	public void playGame()
	{
		gui.drawPlayArea();
		isPlaying = true;

		// drawPlayArea(redPlayer);

		while (isPlaying == true)
		{
			// see if we need to take turns some how

			// check if game over
			checkIfGameOver();

		}
	}

	/**
	 * Allows the player to click on multiple spaces to make a move When they do the
	 * submitMove method the spaces are submitted and their piece moves This method
	 * does not validate whether the moves in the movesToTake queue are valid
	 */
	public void submitMove()
	{
		Coordinates start;
		Coordinates end;
		Checker movingChecker;

		// need initial and ending spot at least
		if (movesToTake.size() <= 1)
		{
			return;
		}

		start = movesToTake.dequeue();

		if (board.getRedCheckers().get(start) != null)
		{
			movingChecker = board.getRedCheckers().get(start);
		}
		else
		{
			movingChecker = board.getWhiteCheckers().get(start);
		}

		while (!movesToTake.isEmpty())
		{
			end = movesToTake.dequeue();
			board.move(start, end);
			start = end;

		}

		movingChecker.resetPossibleMoves(); // may need to update to make sure drawing right thing
		updateWhoseTurn();
		checkIfGameOver();
		board.printBoard();
		gui.drawCheckerBoard();
	}

	public void resetMovesToTake()
	{
		movesToTake = new Queue<>();
	}

	public void updateWhoseTurn()
	{
		if (whoseTurn.equals(redPlayer))
		{
			whoseTurn = whitePlayer;
		}
		else
		{
			whoseTurn = redPlayer;
		}
	}

	public void pickPlacesToMove(Coordinates coor)
	{
		Checker checkerToMove;

		// if this is the first list we are picking then make sure we are picking our
		// own checker
		if (movesToTake.isEmpty())
		{
			if (board.getRedCheckers().get(coor) != null)
			{
				checkerToMove = board.getRedCheckers().get(coor);
			}
			else if (board.getWhiteCheckers().get(coor) != null)
			{
				checkerToMove = board.getWhiteCheckers().get(coor);
			}
			else
			{
				return;
			}

			if (checkerToMove.getColor() != whoseTurn.getColor())
			{
				return;
			}
			movesToTake.enqueue(coor);
			board.getAllPossibleMoves(coor);
			return;
		}

		// make sure the coordinates are in our possible moves

		movesToTake.enqueue(coor);

		for (Coordinates c : movesToTake)
		{
			System.out.println(c.toString());
		}
	}

	public static void main(String[] args)
	{
		GameOfCheckers game = new GameOfCheckers();
		game.playGame();
	}
}