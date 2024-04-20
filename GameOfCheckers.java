package gp;

import java.awt.Color;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.Queue;

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
	private Color winner;
	private boolean gameOver = false;
	private CheckerBoard board;
	Queue<Coordinates> movesToTake;
	private Color whoseTurn;
	CheckersGUI gui;

	/**
	 * Constructs a new Game of Checkers.
	 */
	public GameOfCheckers()
	{
		winner = null;
		board = new CheckerBoard();
		gameOver = false;
		movesToTake = new Queue<>();
		whoseTurn = Draw.RED;
		gui = new CheckersGUI(board, this);
	}
	
	
	/**
	 * Sets the game's board to the one specified 
	 * @param board Board to have the game use
	 */
	public void setBoard(CheckerBoard board)
	{
		this.board = board;
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

	/**
	 * Returns which color's turn it is
	 * @return Returns color who's turn it is
	 */
	public Color getWhoseTurn()
	{
		return whoseTurn;
	}
	
	/**
	 * Checks to see if a player has lost all of their checkers, if they have the game is over
	 * @return Returns true if the game is over
	 */
	public void checkIfGameOver()
	{
		if (board.getRedCheckers().size() == 0 )
		{
			winner = Draw.WHITE;
			gameOver = true;
		}
		else if(board.getWhiteCheckers().size() == 0)
		{
			winner = Draw.RED;
			gameOver = true;
			
		}
		else
		{
			gameOver = false;
		}
		
	}
	
	/**
	 * Returns if the game is over or not
	 * @return Returns if game over
	 */
	public boolean getGameOverStatus()
	{
		return gameOver;
	}

	/**
	 * Sets the winning color
	 * @param color Color that won
	 */
	public void setWinner(Color color)
	{
		winner = color;
	}
	
	/**
	 * Returns the color that won
	 * @return Returns winning color
	 */
	public Color getWinner()
	{
		return winner;
	}
	
	/**
	 * Returns a message saying who's turn it is based on the color place in the parameter
	 * @param color Color who's turn it will be
	 * @return Returns message saying which color's turn it is
	 */
	public String announceTurn(Color color)
	{
		String message;

		if (color == Draw.RED)
		{
			
			message = "Red's Turn";
		}
		else
		{
			message = "White's Turn";
		}
		
		return message;
	}

	/**
	 * Plays a game of checkers until the game is over
	 */
	public void playGame()
	{
		gui.drawPlayArea();
	
	//		while(isPlaying == true)
		{
			// check if game over
			checkIfGameOver();
		
			
		}
		

	}

	/**
	 * Submits the queue of spaces a player has clicked on and goes through them one by one and moves the 
	 * checker to those spaces.
	 * If there are not at least 2 spaces queued up no move of the checker is attempted.
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

		//set the checker we are moving by seeing what color it is
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
 		gui.drawPlayArea();

	}

	/**
	 * Resets the queue of moves we are going to take so that it is empty
	 */
	public void resetMovesToTake()
	{
		movesToTake = new Queue<>();
		gui.drawCheckerBoard();
	}

	/**
	 * Updates whoseTurn so that the opposite color of the one who is currently taking a turn is set
	 */
	public void updateWhoseTurn()
	{
		if (whoseTurn == Draw.RED)
		{
			setWhoseTurn(Draw.WHITE);
		}
		else
		{
			setWhoseTurn(Draw.RED);
		}
	}
	
	/**
	 * Sets whose turn to specific color
	 * @param color Color whose turn it will be
	 */
	public void setWhoseTurn(Color color)
	{
		whoseTurn = color;
	}

	/**
	 * Allows a player to queue up places on the board they want to move a checker piece
	 * The player must first select one of their checkers.  Subsequent choices must be 
	 * spots on the board that that checker is able to move to.
	 * @param coor
	 */
	public void pickPlacesToMove(Coordinates coor)
	{

		Checker checkerToMove;

		// if this is the first list we are picking then make sure we are picking our
		// own checker
		if (movesToTake.isEmpty())
		{
			checkerToMove = getCheckerAtCoordinate(coor);

			//if we didn't select one of our checkers
			if (checkerToMove == null)
			{
				resetMovesToTake();
				return;
			}
			//if we clicked on an opposing player's checkers
			if (checkerToMove.getColor() != whoseTurn)
			{
				resetMovesToTake();
				return;
			}

			movesToTake.enqueue(coor);
			board.buildPossibleMoveGraph(coor);
			gui.drawPossibleMoves(coor);
			return;
		}

		// make sure the coordinates are in our possible moves
		if (movesToTake.size() > 0)
		{
			checkerToMove = getCheckerAtCoordinate(movesToTake.peek());
			if (checkerToMove == null)
			{
				resetMovesToTake();
				return;
			}
			//if we click to move to on spot where ther is an opposing player's checker
			if(whoseTurn == Draw.RED)
			{
				if(board.getWhiteCheckers().get(coor) != null)
				{
					resetMovesToTake();
					return;
				}
			}
			else
			{
				if(board.getRedCheckers().get(coor) != null)
				{
					resetMovesToTake();
					return;
				}
				
			}

			boolean wasInGraph = false;
			Digraph dg = checkerToMove.getPossibleMoves();

			for (int v = 0; v < dg.V(); v++)
			{
				for (Integer c : dg.adj(v))
				{
					if (board.coordinateToInteger(coor) == c)
					{
						movesToTake.enqueue(coor);
						wasInGraph = true;
					}
				}
			}
			
			if(!wasInGraph)
			{
				resetMovesToTake();
			}

		}

	}

	/**
	 * Returns the checker at a given coordinate if it exists
	 * @param coor Coordinate we think there is a checker at
	 * @return Returns a checker at given coordinate
	 */
	public Checker getCheckerAtCoordinate(Coordinates coor)
	{
		Checker checker;
		
		if (board.getRedCheckers().get(coor) != null)
		{
			checker = board.getRedCheckers().get(coor);
		}
		else if (board.getWhiteCheckers().get(coor) != null)
		{
			checker = board.getWhiteCheckers().get(coor);
		}
		else
		{
			return null;
		}
		return checker;
	}

	
	//play our game of checkers
		public static void main(String[] args)
		{
			GameOfCheckers game = new GameOfCheckers();
			game.playGame();
			
		}
	
}