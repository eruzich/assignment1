package gp;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.Stack;

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
	private BinarySearchST<Coordinates, Checker> whiteCheckers;
	private BinarySearchST<Coordinates, Checker> redCheckers;
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
	public BinarySearchST<Coordinates, Checker> getWhiteCheckers()
	{
		return whiteCheckers;
	}

	/**
	 * getter for redCheckers, which is used to determine number of redCheckers by
	 * GameOfCheckers to identify when game is over
	 * 
	 * @return redCheckers
	 */
	public BinarySearchST<Coordinates, Checker> getRedCheckers()
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
					whiteCheckers.put(coor, new Checker(Draw.WHITE));
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
					redCheckers.put(coor, new Checker(Draw.RED));
					checkersToPlace--;
				}
			}
		}

	}

	/**
	 * Deletes a checker at a specific coordinate.
	 * 
	 * @param x for x-coordinate
	 * @param y for y-coordinate
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
	 * Given a Coordinate find all possible move the checker at that coordinate can
	 * take. If there is not a checker at the coordinate specified then the function
	 * returns
	 * 
	 * @param c Coordinate of checker you want to see the moves for
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
		checkerToMove.resetPossibleMoves(); // if checker has possibleMoves set reset them so we get fresh directed graph
											// without old possible moves

		if (checkerToMove.getIsKing())
		{
			kingedCheckerMove(myCheckers, otherCheckers, c);
		}
		else
		{
			regularCheckerMove(myCheckers, otherCheckers, c);
		}
	}

	/**
	 * Given a coordinate and two BinarySearchST of different colored checkers (one
	 * white and one red) determine where a checker (not kinged) can move on the
	 * board. The possible moves will be updated in that checker's graph
	 * 
	 * @param myCheckers
	 * @param otherCheckers
	 * @param c
	 */
	private void regularCheckerMove(BinarySearchST<Coordinates, Checker> myCheckers, BinarySearchST<Coordinates, Checker> otherCheckers, Coordinates c)
	{
		Checker checkerToMove = myCheckers.get(c);
		// if I don't have a checker at that coordinate, return
		if (checkerToMove == null)
		{
			return;
		}

		int direction;

		// red will move up the board, white will move down
		if (checkerToMove.getColor() == Draw.RED)
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
		if ((currentYPosition == 0 && checkerToMove.getColor() == Draw.WHITE) || (currentYPosition == 7 && checkerToMove.getColor() == Draw.RED))
		{
			return;
		}

		// see if we can move to the left, can't if will fall of the edge of the board
		if (currentXPosition != 0)
		{
			Coordinates moveToLeft = board[currentXPosition - 1][currentYPosition + direction];

			if (myCheckers.get(moveToLeft) == null && otherCheckers.get(moveToLeft) == null) // spot is empty can move
																								// there
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
	 * See if a checker can jump over another. If it can add the coordinates for the
	 * checker being jumped, and the space behind it to the checker doing the
	 * jumping. This allows the us to keep track of what checkers are being jumped
	 * and need to be deleted
	 * 
	 * @param myCheckers    Checkers of the player who will be doing the jumping
	 * @param otherCheckers Opposing player's checkers that may be jumped
	 * @param c             Coordinate of where the checker doing the jumping is
	 *                      starting from
	 * @param checkerToMove The checker who we want the possible moves for
	 */
	private void checkJumpMove(BinarySearchST<Coordinates, Checker> myCheckers, BinarySearchST<Coordinates, Checker> otherCheckers, Coordinates c, Checker checkerToMove)
	{
		int direction;

		if (checkerToMove.getColor() == Draw.RED)
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
		// functions to jump from now on
		if ((currentYPosition <= 1 && checkerToMove.getColor() == Draw.WHITE) || (currentYPosition >= 6 && checkerToMove.getColor() == Draw.RED))
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

	public void kingedCheckerMove(BinarySearchST<Coordinates, Checker> myCheckers, BinarySearchST<Coordinates, Checker> otherCheckers, Coordinates c)
	{
		Checker checkerToMove = myCheckers.get(c);
		Stack<Coordinates> haveJumped = new Stack<>();

		if (!checkerToMove.getIsKing())
		{
			return;
		}

		int currentYPosition = c.getY();
		int currentXPosition = c.getX();

		if (currentXPosition != 0)
		{

			// spot is empty can move there
			if (currentYPosition != 7)
			{
				Coordinates moveLeftUp = board[currentXPosition - 1][currentYPosition + 1];
				if (myCheckers.get(moveLeftUp) == null && otherCheckers.get(moveLeftUp) == null)
				{
					addPossibleMove(checkerToMove, c, moveLeftUp);
				}
				if (otherCheckers.get(moveLeftUp) != null)
				{
					checkKingJumpMove(myCheckers, otherCheckers, c, checkerToMove, haveJumped, 0, c);
				}
			}

			if (currentYPosition != 0)
			{
				Coordinates moveLeftDown = board[currentXPosition - 1][currentYPosition - 1];
				if (myCheckers.get(moveLeftDown) == null && otherCheckers.get(moveLeftDown) == null)
				{
					addPossibleMove(checkerToMove, c, moveLeftDown);
				}
				if (otherCheckers.get(moveLeftDown) != null)
				{
					checkKingJumpMove(myCheckers, otherCheckers, c, checkerToMove, haveJumped, 0, c);
				}
			}

		}

		if (currentXPosition != 7)
		{
			if (currentYPosition != 7)
			{
				Coordinates moveRightUp = board[currentXPosition + 1][currentYPosition + 1];

				if (myCheckers.get(moveRightUp) == null && otherCheckers.get(moveRightUp) == null)
				{
					addPossibleMove(checkerToMove, c, moveRightUp);
				}
				// if that square is taken by the opposing player's checker see if we can jump
				// it
				if (otherCheckers.get(moveRightUp) != null)
				{
					checkKingJumpMove(myCheckers, otherCheckers, c, checkerToMove, haveJumped, 0, c);
				}

			}

			if (currentYPosition != 0)
			{
				Coordinates moveRightDown = board[currentXPosition + 1][currentYPosition - 1];

				if (myCheckers.get(moveRightDown) == null && otherCheckers.get(moveRightDown) == null)
				{
					addPossibleMove(checkerToMove, c, moveRightDown);
				}
				// if that square is taken by the opposing player's checker see if we can jump
				// it
				if (otherCheckers.get(moveRightDown) != null)
				{
					checkKingJumpMove(myCheckers, otherCheckers, c, checkerToMove, haveJumped, 0, c);
				}
			}

		}
	}

	private void checkKingJumpMove(BinarySearchST<Coordinates, Checker> myCheckers, BinarySearchST<Coordinates, Checker> otherCheckers, Coordinates c, Checker checkerToMove,
			Stack<Coordinates> haveJumped, int jumpsSoFar, Coordinates startingPosition)
	{
		int currentYPosition = c.getY();
		int currentXPosition = c.getX();

		System.out.println("jumps so far " + jumpsSoFar);
		for (Coordinates coor : haveJumped)
		{
			System.out.print(coor.toString() + " - ");
		}

		System.out.println();

		while (haveJumped.size() > jumpsSoFar)
		{
			haveJumped.pop();
		}

		jumpsSoFar++;

		// can jump over piece to left only if won't fall of left side of board
		if (currentXPosition > 1)
		{
			if (currentYPosition < 6)
			{
				Coordinates toOurUpperLeft = board[currentXPosition - 1][currentYPosition + 1];

				// spot on board can only have one checker at a time, so only need to check the
				// other checkers
				if (otherCheckers.get(toOurUpperLeft) != null)
				{
					Coordinates behindChecker = board[toOurUpperLeft.getX() - 1][toOurUpperLeft.getY() + 1];

					// if no checker behind the one we intend to jump
					boolean canJump = (myCheckers.get(behindChecker) == null || behindChecker != startingPosition) && otherCheckers.get(behindChecker) == null;

					boolean alreadyJumped = false;
					for (Coordinates coor : haveJumped)
					{
						if (toOurUpperLeft == coor)
						{

							alreadyJumped = true;
							break;
						}

					}

					if (canJump && !alreadyJumped)
					{

						haveJumped.push(toOurUpperLeft);
						addPossibleMove(checkerToMove, c, toOurUpperLeft);
						addPossibleMove(checkerToMove, toOurUpperLeft, behindChecker);
						checkKingJumpMove(myCheckers, otherCheckers, behindChecker, checkerToMove, haveJumped, jumpsSoFar, startingPosition);

					}

				}
			}

			if (currentYPosition > 2)
			{
				Coordinates toOurLowerLeft = board[currentXPosition - 1][currentYPosition - 1];

				// spot on board can only have one checker at a time, so only need to check the
				// other checkers
				if (otherCheckers.get(toOurLowerLeft) != null)
				{
					Coordinates behindChecker = board[toOurLowerLeft.getX() - 1][toOurLowerLeft.getY() - 1];

					// if no checker behind the one we intend to jump
					boolean canJump = (myCheckers.get(behindChecker) == null || behindChecker != startingPosition) && otherCheckers.get(behindChecker) == null;

					boolean alreadyJumped = false;
					for (Coordinates coor : haveJumped)
					{
						if (toOurLowerLeft == coor)
						{
							System.out.println("matched");
							alreadyJumped = true;
							break;
						}

					}

					System.out.println("canJump " + canJump + " alreadyJumped " + alreadyJumped);
					if (canJump && !alreadyJumped)
					{
						haveJumped.push(toOurLowerLeft);
						addPossibleMove(checkerToMove, c, toOurLowerLeft);
						addPossibleMove(checkerToMove, toOurLowerLeft, behindChecker);
						checkKingJumpMove(myCheckers, otherCheckers, behindChecker, checkerToMove, haveJumped, jumpsSoFar, startingPosition);
					}

				}

			}
		}

		// if we are not on the right edge of the board see what right moves we can make
		if (currentXPosition < 6)

		{
			if (currentYPosition < 6)
			{
				Coordinates toOurUpperRight = board[currentXPosition + 1][currentYPosition + 1];
				if (otherCheckers.get(toOurUpperRight) != null)
				{
					Coordinates behindChecker = board[toOurUpperRight.getX() + 1][toOurUpperRight.getY() + 1];

					// if no checker behind the one we intend to jump
					boolean canJump = (myCheckers.get(behindChecker) == null || behindChecker != startingPosition) && otherCheckers.get(behindChecker) == null;

					boolean alreadyJumped = false;
					for (Coordinates coor : haveJumped)
					{
						if (toOurUpperRight == coor)
						{
							System.out.println("matched");
							alreadyJumped = true;
							break;
						}

					}

					System.out.println("canJump " + canJump + " alreadyJumped " + alreadyJumped);
					if (canJump && !alreadyJumped)
					{
						haveJumped.push(toOurUpperRight);
						addPossibleMove(checkerToMove, c, toOurUpperRight);
						addPossibleMove(checkerToMove, toOurUpperRight, behindChecker);
						checkKingJumpMove(myCheckers, otherCheckers, behindChecker, checkerToMove, haveJumped, jumpsSoFar, startingPosition);
					}
				}

			}

			if (currentYPosition > 1)
			{
				Coordinates toOurLowerRight = board[currentXPosition + 1][currentYPosition - 1];
				if (otherCheckers.get(toOurLowerRight) != null)
				{
					Coordinates behindChecker = board[toOurLowerRight.getX() + 1][toOurLowerRight.getY() - 1];

					// if no checker behind the one we intend to jump
					boolean canJump = (myCheckers.get(behindChecker) == null || behindChecker != startingPosition) && otherCheckers.get(behindChecker) == null;

					boolean alreadyJumped = false;
					for (Coordinates coor : haveJumped)
					{
						if (toOurLowerRight == coor)
						{
							alreadyJumped = true;
							break;
						}

					}

					if (canJump && !alreadyJumped)
					{
						haveJumped.push(toOurLowerRight);
						addPossibleMove(checkerToMove, c, toOurLowerRight);
						addPossibleMove(checkerToMove, toOurLowerRight, behindChecker);
						checkKingJumpMove(myCheckers, otherCheckers, behindChecker, checkerToMove, haveJumped, jumpsSoFar, startingPosition);
					}

				}
			}
		}

	}

	/**
	 * Add two vertices to a checker's possible move directed graph
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

		// determine the color of the checkers we are dealing with
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
		// BreadthFirstDirectedPaths dfp = new BreadthFirstDirectedPaths(availableMoves,
		// startingPosition);

		int endingPosition = coordinateToInteger(end);
		Coordinates coordinateImOn = start;

		// if it's not in the realm of possible moves, don't move anywhere
		if (!dfp.hasPathTo(endingPosition))
		{

			return false;

		}

		for (int path : dfp.pathTo(endingPosition))
		{
			// if the checker we mapped out on our path is the opposing player's checker
			// that means that we jumped it and it is gone.
			Coordinates c = integerToCoordinate(path);

			if (otherCheckers.get(c) != null)
			{
				otherCheckers.delete(c);

			}
			else
			{
				myCheckers.put(c, checker);

				// if the checker is at the opposite side of the board king them
				if (checker.getColor() == Draw.RED && c.getY() == 7)
				{
					checker.kingMe();
				}
				else if (checker.getColor() == Draw.WHITE && c.getY() == 0)
				{
					checker.kingMe();
				}

			}

			myCheckers.delete(coordinateImOn);
			coordinateImOn = c;

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
	 * Print an adjacency table in the console for the checker at those coordinates
	 * 
	 * @param c Coordinate the checker is at
	 */
	public void printPossibleMoves(Coordinates c)
	{
		Checker checker;
		if (whiteCheckers.get(c) != null)
		{
			checker = whiteCheckers.get(c);
		}
		else if (redCheckers.get(c) != null)
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

		for (int v = 0; v < dg.V(); v++)
		{
			if (dg.adj(v) == null)
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
	 * 
	 * @param c
	 * @return
	 */
	public ArrayList<Integer> getPossibleMoves(Coordinates c)
	{
		Checker checker;
		if (whiteCheckers.get(c) != null)
		{
			checker = whiteCheckers.get(c);
		}
		else if (redCheckers.get(c) != null)
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
			for (Integer a : vertexAdjs)
			{
				adjacencyList.add(a);
			}
		}
		return adjacencyList;
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
					System.out.print("   w   ");
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
	 * set up board for presentation
	 */
	public void test1Board()
	{
		redCheckers = new BinarySearchST<>();

		// red checkers
		Coordinates coor = board[3][4];
		redCheckers.put(coor, new Checker(Draw.RED));
		coor = board[3][2];
		redCheckers.put(coor, new Checker(Draw.RED));
		coor = board[1][0];
		redCheckers.put(coor, new Checker(Draw.RED));
	}

	public void testKingBoard()
	{
		redCheckers = new BinarySearchST<>();
		whiteCheckers = new BinarySearchST<>();

		// top
		Coordinates coor = board[2][7];
		Checker toPlace = new Checker(Draw.RED);
		toPlace.kingMe();
		redCheckers.put(coor, toPlace);
		// bottom
		coor = board[1][0];
		toPlace = new Checker(Draw.RED);
		toPlace.kingMe();
		redCheckers.put(coor, toPlace);

		// right
		coor = board[7][4];
		toPlace = new Checker(Draw.RED);
		toPlace.kingMe();
		redCheckers.put(coor, toPlace);

		// left
		coor = board[0][3];
		toPlace = new Checker(Draw.RED);
		toPlace.kingMe();
		redCheckers.put(coor, toPlace);

		// center
		coor = board[4][3];
		toPlace = new Checker(Draw.RED);
		toPlace.kingMe();
		redCheckers.put(coor, toPlace);

		// white pieces test top
		coor = board[1][6];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[1][4];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[3][0];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[3][2];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[3][4];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[5][6];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);
	}

	public void testKingLoop()
	{

		redCheckers = new BinarySearchST<>();
		whiteCheckers = new BinarySearchST<>();

		// top
		Coordinates coor = board[2][7];
		Checker toPlace = new Checker(Draw.RED);
		toPlace.kingMe();
		redCheckers.put(coor, toPlace);

		// white pieces test top
		coor = board[1][6];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[1][4];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[3][6];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);

		coor = board[3][4];
		toPlace = new Checker(Draw.WHITE);
		toPlace.kingMe();
		whiteCheckers.put(coor, toPlace);
	}

}