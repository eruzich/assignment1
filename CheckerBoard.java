package gp;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Defines checkers as having coordinates, being a color, having possible moves,
 * tracks location of checkers, removes checkers when opponent moves to
 * coordinate occupied by opposing player's checker
 * 
 * @author Erin Mortensen and Elizabeth Ruzich
 */
public class CheckerBoard
{
	private Coordinates[][] board;
	public BinarySearchST<Coordinates, Checker> whiteCheckers;  //had to change to public to access in dif class
	public BinarySearchST<Coordinates, Checker> redCheckers; //had to change to public to access in dif class
	private int rows = 8;
	private int columns = 8;

	/**
	 * Creates white checkers, red checkers, and a checker board
	 */
	public CheckerBoard()
	{
		whiteCheckers = new BinarySearchST<>();
		redCheckers = new BinarySearchST<>();
		board = new Coordinates[rows][columns];

		for (int y = rows - 1; y >= 0; y--)
		{
			for (int x = columns - 1; x >= 0; x--)
			{
				board[x][y] = (new Coordinates(x, y));
			}
		}
		placeWhiteCheckers();
		placeRedCheckers();
	}

	/**
	 * getter for whiteCheckers, which is used to determine number of whiteCheckers
	 * by GameOfCheckers to identify when game is over
	 * 
	 * @return whiteCheckers
	 */
	public BinarySearchST getWhiteCheckers()
	{
		return whiteCheckers;
	}

	/**
	 * getter for redCheckers, which is used to determine number of redCheckers by
	 * GameOfCheckers to identify when game is over
	 * 
	 * @return redCheckers
	 */
	public BinarySearchST getRedCheckers()
	{
		return redCheckers;
	}

	/**
	 * Determines the coordinates of the checker board
	 * 
	 * @param x for x-coordinate
	 * @param y for y-coordinate
	 */
	public Coordinates getCoordinate(int x, int y)
	{
		return board[x][y];
	}

	/**
	 * Place the initial white checkers on the checker board
	 */
	private void placeWhiteCheckers()
	{
		int checkersToPlace = 12;

		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x < 8; x++)
			{
				if (checkersToPlace <= 0)
				{
					break;
				}
				if ((y % 2 == 1 && x % 2 == 0) || (y % 2 == 0 && x % 2 == 1))
				{
					Coordinates coor = board[x][y];
					whiteCheckers.put(coor, new Checker(StdDraw.WHITE));
					checkersToPlace--;
				}
			}
		}
	}

	/**
	 * Place the initial red checkers on the checker board
	 */
	private void placeRedCheckers()
	{
		int checkersToPlace = 12;

		for (int r = 0; r < 8; r++) 
		{
			for (int c = 0; c < 8; c++) 
			{
				if (checkersToPlace <= 0) 
				{
					break;
				}
				if ((r % 2 == 1 && c % 2 == 0) || (r % 2 == 0 && c % 2 == 1)) 
				{
					Coordinates coor = board[c][r];
					redCheckers.put(coor, new Checker(StdDraw.WHITE));
					checkersToPlace--;
				}
			}
		}

		Coordinates coor = board[3][4];
		redCheckers.put(coor, new Checker(StdDraw.RED));
		coor = board[3][2];
		redCheckers.put(coor, new Checker(StdDraw.RED));

//		Coordinate coor = board[7][1];
//		redCheckers.put(coor,new Checker(Color.RED));
//		 coor = board[2][3];
//		 redCheckers.put(coor,new Checker(Color.RED));
//		 coor = board[4][5];
//		 redCheckers.put(coor,new Checker(Color.RED));
//		 
	}

	/**
	 * Print the checker board and checkers in the console
	 */
	public void printBoard()
	{
		System.out.println("\n\nBoard");
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x < 8; x++)
			{
				if (x % 8 == 0)
				{
					System.out.println();
				}
				Coordinates c = board[x][y];

				if (whiteCheckers.get(c) != null)
				{
					System.out.print("  w   ");
				}
				else if (redCheckers.get(c) != null)
				{
					System.out.print("  r   ");
				}
				else
				{
					System.out.print(board[x][y].toString() + " ");
				}
			}
		}
	}

	/**
	 * Deletes a checker when opponent moves onto spot taken by checker
	 * 
	 * @param x for x-coordinate
	 * @param y for x-coordinate
	 */
	public void deleteChecker(int x, int y)
	{
		Coordinates c = board[x][y];

		BinarySearchST<Coordinates, Checker> checkers;

		if (whiteCheckers.get(c) != null)
		{
			checkers = whiteCheckers;
		}
		else if (redCheckers.get(c) != null)
		{
			checkers = redCheckers;
		}
		else
		{
			System.out.println("no checker to delete");
			return;
		}
		checkers.delete(c);
	}

	/**
	 * Determines the possible moves for white checkers and red checkers
	 * 
	 * @param c for coordinates
	 */
	public void getAllPossibleMoves(Coordinates c)
	{
		BinarySearchST<Coordinates, Checker> myCheckers;
		BinarySearchST<Coordinates, Checker> otherCheckers;

		if (whiteCheckers.get(c) != null)
		{
			myCheckers = whiteCheckers;
			otherCheckers = redCheckers;
		}
		else if (redCheckers.get(c) != null)
		{
			myCheckers = redCheckers;
			otherCheckers = whiteCheckers;
		}
		else
		{
			System.out.println("No checkers at those coordinates");
			return;
		}

		Checker checkerToMove = myCheckers.get(c);
		if (checkerToMove == null)
		{
			return;
		}
		if (checkerToMove.getIsKing())
		{

		}
		else
		{
			regularCheckerMove(myCheckers, otherCheckers, c);
		}
	}

	/**
	 * 
	 * @param myCheckers
	 * @param otherCheckers
	 * @param c
	 */
	public void regularCheckerMove(BinarySearchST<Coordinates, Checker> myCheckers,
			BinarySearchST<Coordinates, Checker> otherCheckers, Coordinates c)
	{
		Checker checkerToMove = myCheckers.get(c);
		// if I don't have a checker at that coordinate, return
		if (checkerToMove == null)
		{
			return;
		}
		int direction;

		// red will move up the board, white will move down
		if (checkerToMove.getColor() == StdDraw.RED)
		{
			direction = 1;
		}
		else
		{
			direction = -1;
		}
		int currentYPosition = c.getY();
		int currentXPosition = c.getX();

		// may not need this code if king will use a different kind of code
		if ((currentYPosition == 0 && checkerToMove.getColor() == StdDraw.WHITE)
				|| (currentYPosition == 7 && checkerToMove.getColor() == StdDraw.RED))
		{
			return;
		}

		// see if we can move to the left
		if (currentXPosition != 0)
		{
			Coordinates moveToLeft = board[currentXPosition - 1][currentYPosition + direction];

			if (myCheckers.get(moveToLeft) == null && otherCheckers.get(moveToLeft) == null)
			{
				addPossibleMove(checkerToMove, c, moveToLeft);
			}
			// if that square is taken by the opposing player's checker see if we can jump
			// it
			if (otherCheckers.get(moveToLeft) != null)
			{
				checkJumpMove(myCheckers, otherCheckers, c, checkerToMove);
			}
		}
		// make sure we won't fall off the board if we travel right
		if (currentXPosition != 7)
		{
			Coordinates moveToRight = board[currentXPosition + 1][currentYPosition + direction];

			if (myCheckers.get(moveToRight) == null && otherCheckers.get(moveToRight) == null)
			{
				addPossibleMove(checkerToMove, c, moveToRight);
			}
			// if that square is taken by the opposing player's checker see if we can jump
			// it
			if (otherCheckers.get(moveToRight) != null)
			{
				checkJumpMove(myCheckers, otherCheckers, c, checkerToMove);
			}
		}
	}

	/**
	 * 
	 * @param myCheckers
	 * @param otherCheckers
	 * @param c
	 * @param checkerToMove
	 */
	private void checkJumpMove(BinarySearchST<Coordinates, Checker> myCheckers,
			BinarySearchST<Coordinates, Checker> otherCheckers, Coordinates c, Checker checkerToMove)
	{
		int direction;

		if (checkerToMove.getColor() == StdDraw.RED)
		{
			direction = 1;
		}
		else
		{
			direction = -1;
		}
		int currentYPosition = c.getY();
		int currentXPosition = c.getX();

		// they are a king now and their turn is over and they will have different
		// functions to jump
		if ((currentYPosition <= 1 && checkerToMove.getColor() == StdDraw.WHITE)
				|| (currentYPosition >= 6 && checkerToMove.getColor() == StdDraw.RED))
		{
			return;
		}

		// can jump over piece to left only if won't fall of left side of board
		if (currentXPosition != 0 && currentXPosition != 1)
		{
			Coordinates toOurLeft = board[currentXPosition - 1][currentYPosition + direction];

			// spot on board can only have one checker at a time, so only need to check the
			// other checkers
			if (otherCheckers.get(toOurLeft) != null)
			{
				Coordinates behindChecker = board[toOurLeft.getX() - 1][toOurLeft.getY() + direction];

				// if no checker behind the one we intend to jump
				if (myCheckers.get(behindChecker) == null && otherCheckers.get(behindChecker) == null)
				{
					addPossibleMove(checkerToMove, c, toOurLeft);
					addPossibleMove(checkerToMove, toOurLeft, behindChecker);
					checkJumpMove(myCheckers, otherCheckers, behindChecker, checkerToMove);
				}
			}
		}
		// if we are not on the right edge of the board see what right moves we can make
		if (currentXPosition != 7 && currentXPosition != 6)
		{
			Coordinates toOurRight = board[currentXPosition + 1][currentYPosition + direction];
			if (otherCheckers.get(toOurRight) != null)
			{
				Coordinates behindChecker = board[toOurRight.getX() + 1][toOurRight.getY() + direction];

				// if no checker behind the one we intend to jump
				if (myCheckers.get(behindChecker) == null && otherCheckers.get(behindChecker) == null)
				{
					addPossibleMove(checkerToMove, c, toOurRight);
					addPossibleMove(checkerToMove, toOurRight, behindChecker);
					checkJumpMove(myCheckers, otherCheckers, behindChecker, checkerToMove);
				}
			}
		}
	}

	/**
	 * Add a two vertices to a checker's possible move directed graph
	 * 
	 * @param checker          Checker who has this move available
	 * @param startingPosition The position the checker is starting at
	 * @param endingPosition   The position the checker ends at
	 */
	private void addPossibleMove(Checker checker, Coordinates startingPosition, Coordinates endingPosition)
	{
		int start = coordinateToInteger(startingPosition);
		int end = coordinateToInteger(endingPosition);
		checker.setPossibleMoves(start, end);
	}

	/**
	 * Print an adjacency table in the console for the checker
	 * at those coordinates
	 * @param c Coordinate the checker is at
	 */
	public void printPossibleMoves(Coordinates c) 
	{	
		Checker checker;
		if(whiteCheckers.get(c) != null)
		{
			checker = whiteCheckers.get(c);
		}
		else if(redCheckers.get(c) != null)
		{
			checker = redCheckers.get(c);
		}
		else
		{
			System.out.println("no checker at that coordinate");
			return;
		}
		
		Digraph dg = checker.getPossibleMoves();

		// print adj table
		System.out.println("Adjacency List:");
		System.out.println("---------------");

		for (int v = 0; v < dg.V(); v++) {
			if(dg.adj(v) == null)
			{
				return;
			}
			Iterable<Integer> vertexAdjs = dg.adj(v);
			System.out.print(v + ": ");

			int adjsNumber = dg.outdegree(v); // help print adjacency list so no -> after last adj vertex

			// print each adjacent vertex to the one we are looking at
			for (Integer a : vertexAdjs) 
			{
				adjsNumber--;
				System.out.print(a);

				if (adjsNumber != 0) 
				{
					System.out.print(" -> ");
				}
			}

			System.out.println();
		}
	}
	
	/**
	 * Getter for possible moves
	 * @param c
	 * @return
	 */
	public ArrayList<Integer> getPossibleMoves(Coordinates c)
	{
		Checker checker;
		if(whiteCheckers.get(c) != null)
		{
			checker = whiteCheckers.get(c);
		}
		else if(redCheckers.get(c) != null)
		{
			checker = redCheckers.get(c);
		}
		else
		{
			System.out.println("no checker at that coordinate");
			return null;
		}
		
		Digraph dg = checker.getPossibleMoves();
		ArrayList<Integer> adjacencyList = new ArrayList<Integer>();
		for (int v = 0; v < dg.V(); v++) 
		{
			Iterable<Integer> vertexAdjs = dg.adj(v);
			int adjsNumber = dg.outdegree(v);
			for (Integer a : vertexAdjs) 
			{
				adjsNumber--;
				adjacencyList.add(a);
			}
		}
		return adjacencyList;
	}

	/**
	 * Given a starting Coordinate move a checker along the path of possible moves
	 * it has If no checker is selected return false. If the ending position isn't
	 * valid return false, else return true
	 * 
	 * @param start The starting position of a checker
	 * @param end   The ending position of the checker
	 * @return returns true if the starting and ending position are valid, otherwise
	 *         return false
	 */
	public boolean move(Coordinates start, Coordinates end)
	{
		Checker checker;
		BinarySearchST<Coordinates, Checker> otherCheckers;
		BinarySearchST<Coordinates, Checker> myCheckers;

		if (whiteCheckers.get(start) != null)
		{
			checker = whiteCheckers.get(start);
			myCheckers = getWhiteCheckers();
			otherCheckers = getRedCheckers();

		}
		else if (redCheckers.get(start) != null)
		{
			checker = redCheckers.get(start);
			myCheckers = getRedCheckers();
			otherCheckers = getWhiteCheckers();
		}
		else
		{
			checker = null;
			myCheckers = null;
			otherCheckers = null;
			return false;
		}

		Digraph availableMoves = checker.getPossibleMoves();
		int startingPosition = this.coordinateToInteger(start);
		DepthFirstDirectedPaths dfp = new DepthFirstDirectedPaths(availableMoves, startingPosition);

		int endingPosition = coordinateToInteger(end);
		Coordinates coordinateImOn = start;

		// if it's not in the realm of possible moves, don't move anywhere
		if (dfp.hasPathTo(endingPosition))
		{
			for (int path : dfp.pathTo(endingPosition))
			{
				// if the checker we mapped out on our path is the opposing player's checker
				// that means that we jumped it and it is gone.
				Coordinates c = integerToCoordinate(path);

				System.out.println(c.toString());

				if (otherCheckers.get(c) != null)
				{
					otherCheckers.delete(c);

				}
				else
				{
					myCheckers.put(c, checker);
//				drawWhiteCheckers();
//				drawRedCheckers();
//				StdDraw.pause(1000);
				}
				myCheckers.delete(coordinateImOn);
				coordinateImOn = c;
			}
		}
		else
		{
			return false;
		}
		return true;
	}

	/**
	 * Helps work with directed graph so that the position of the graph matches the
	 * coordinates of the board
	 * 
	 * @param c coordinates to match
	 * @return Returns an integer that represents a numerical value for a space on
	 *         the checker board.
	 * 
	 *         Starts at the bottom left at 0, then goes right and up with the other
	 *         numbers 8, 9, 10, 11, 12, 13, 14 e.g. 0, 1, 2, 3, 4, 5, 6, 7,
	 */
	public int coordinateToInteger(Coordinates c)
	{
		int x = c.getX();
		int y = c.getY();
		return (y * 8) + x;
	}

	/**
	 * Takes an integer and translates it to a Coordinate on the checkerboard
	 * Integers start at the bottom left of the board and increment from left to
	 * right, bottom to top. The top right corner of the checker board would be 63,
	 * and the bottom left corner would be 0.
	 * 
	 * @param c int to convert to a checkerboard Coordinate
	 * @return Returns a coordinate for the nth space on the checkerboard
	 * @throws IllegalArgumentException if c is greater than 63 or less than 0
	 */
	public Coordinates integerToCoordinate(int c)
	{
		if (c > 63 || c < 0)
		{
			throw new IllegalArgumentException("That number can't map to the checkerboard");
		}
		Coordinates coor;

		int x = c % 8;
		int y = c / 8;

		coor = new Coordinates(x, y);

		return coor;
	}

	/**
	 * Draws checker board
	 */
	public void drawCheckerBoard()
	{
		int n = 8;
		StdDraw.setXscale(0, n);
		StdDraw.setYscale(0, n);

		for (int i = n - 1; i >= 0; i--)
		{
			for (int j = n - 1; j >= 0; j--)
			{
				if ((i + j) % 2 != 0)
				{
					StdDraw.setPenColor(StdDraw.BLACK);
				}
				else
				{
					StdDraw.setPenColor(StdDraw.RED);
				}
				StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
			}
		}
		drawWhiteCheckers();
		drawRedCheckers();
	}

	/**
	 * Draws red checkers on checker board
	 */
	public void drawRedCheckers()
	{
		if (redCheckers.isEmpty())
		{
			return;
		}
		double radius = 0.4;
		double offSet = 0.5;
		// draw red checkers
		StdDraw.setPenColor(StdDraw.RED);

		for (Coordinates coor : redCheckers.keys())
		{
			int x = coor.getX();
			int y = coor.getY();

			StdDraw.filledCircle(x + offSet, y + offSet, radius);
		}
	}

	/**
	 * Draws white checkers on checker board
	 */
	public void drawWhiteCheckers()
	{
		if (whiteCheckers.isEmpty())
		{
			return;
		}
		StdDraw.setPenColor(StdDraw.WHITE);
		double radius = 0.4;
		double offSet = 0.5;

		for (Coordinates coor : whiteCheckers.keys())
		{
			int x = coor.getX();
			int y = coor.getY();

			StdDraw.filledCircle(x + offSet, y + offSet, radius);
		}
	}
}