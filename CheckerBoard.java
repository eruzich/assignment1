package gp;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdRandom;

public class CheckerBoard {

		
	
		public static void main(String[] args) {
        
		
		//creating white checkers	
		Checker[] checker = new Checker[24];
		String wC = "whiteChecker";
		int[] key = new int[24];
		for (int i = 0; i < 24; i++) {
			key[i] = 10 + StdRandom.uniformInt(20);
		}
		for (int j = 0; j < 12; j++) {
			checker[j] = new Checker (key[j], wC.concat(Character.toString(j)));
		}
		
		//creating array of coordinates
		Coordinates[][] coord = { {new Coordinates(0, 0)}, {new Coordinates(0, 2)}, {new Coordinates(0, 4)},
		{new Coordinates(0, 6)}, {new Coordinates(1, 1)}, {new Coordinates(1, 3)}, {new Coordinates(1, 5)},
		{new Coordinates(1, 7)}, {new Coordinates(2, 0)}, {new Coordinates(2, 2)}, {new Coordinates(2, 4)},
		{new Coordinates(2, 6)}, {new Coordinates(5, 1)}, {new Coordinates(5, 3)}, {new Coordinates(5, 5)},
		{new Coordinates(5, 7)}, {new Coordinates(6, 0)}, {new Coordinates(6, 2)}, {new Coordinates(6, 4)},
		{new Coordinates(6, 6)}, {new Coordinates(7, 1)}, {new Coordinates(7, 3)}, {new Coordinates(7, 5)}, 
		{new Coordinates(7, 7)}};
		
		//creating black checkers
		String bC = "blackChecker";
		for (int h = 12; h < 24; h++) {
			checker[h] = new Checker (key[h], bC.concat(Character.toString(h)));
		}

		//creating symbol table of keys and coordinates for checkers
		BinarySearchST<Integer, Coordinates> st = new BinarySearchST<>();
		st.put(key[0], coord[0][0]);
		st.put(key[0], coord[0][1]);
		
			
		
			
			
			
			
		//creating a checkerboard
		int n = 8;
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 != 0) StdDraw.setPenColor(StdDraw.BLACK);
                else                  StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
            }
        }
        
        //creating a white checker
        StdDraw.setPenColor(StdDraw.WHITE);
        double x = 0.5;
        double y = 7.5;
        double radius = 0.4;
        StdDraw.filledCircle(x, y, radius);
    }
}
