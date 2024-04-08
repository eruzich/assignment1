package gp;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class CheckerBoard {

		public static void main(String[] args) {
        
		//creating array of coordinates for checkers
		Coordinates[][] coord = new Coordinates[8][4];
		coord[0][0] = new Coordinates(0, 0);
		coord[0][1] = new Coordinates(0, 2);
		coord[0][2] = new Coordinates(0, 4);
		coord[0][3] = new Coordinates(0, 6);
		coord[1][0] = new Coordinates(1, 1);
		coord[1][1] = new Coordinates(1, 3);
		coord[1][2] = new Coordinates(1, 5);
		coord[1][3] = new Coordinates(1, 7);
		coord[2][0] = new Coordinates(2, 0);
		coord[2][1] = new Coordinates(2, 2);
		coord[2][2] = new Coordinates(2, 4);
		coord[2][3] = new Coordinates(2, 6);
		coord[3][0] = new Coordinates(3, 1);
		coord[3][1] = new Coordinates(3, 3);
		coord[3][2] = new Coordinates(3, 5);
		coord[3][3] = new Coordinates(3, 7);
		coord[4][0] = new Coordinates(4, 0);
		coord[4][1] = new Coordinates(4, 2);
		coord[4][2] = new Coordinates(4, 4);
		coord[4][3] = new Coordinates(4, 6);
		coord[5][0] = new Coordinates(5, 1);
		coord[5][1] = new Coordinates(5, 3);
		coord[5][2] = new Coordinates(5, 5);
		coord[5][3] = new Coordinates(5, 7);
		coord[6][0] = new Coordinates(6, 0);
		coord[6][1] = new Coordinates(6, 2);
		coord[6][2] = new Coordinates(6, 4);
		coord[6][3] = new Coordinates(6, 6);
		coord[7][0] = new Coordinates(7, 1);
		coord[7][1] = new Coordinates(7, 3);
		coord[7][2] = new Coordinates(7, 5);
		coord[7][3] = new Coordinates(7, 7);
			
		//creating checker array where checker is key and string
		Checker[] checker = new Checker[24];
		int[] key = new int[24];
		for (int i = 0; i < 24; i++) {
			key[i] = StdRandom.uniformInt(1, 100);
		}
		
		//creating white checkers	
		String wC = "whiteChecker";
		for (int j = 0; j < 12; j++) {
			String s = "" + j;
			checker[j] = new Checker (key[j], wC.concat(s));
		}
		//creating symbol table of keys and coordinates for white checkers
		BinarySearchST<Integer, Coordinates> whiteST = new BinarySearchST<>();
		whiteST.put(key[0], coord[0][0]);
		whiteST.put(key[1], coord[0][1]);
		whiteST.put(key[2], coord[0][2]); 
		whiteST.put(key[3], coord[0][3]);
		whiteST.put(key[4], coord[1][0]);
		whiteST.put(key[5], coord[1][1]);
		whiteST.put(key[6], coord[1][2]);
		whiteST.put(key[7], coord[1][3]);
		whiteST.put(key[8], coord[2][0]);
		whiteST.put(key[9], coord[2][1]);
		whiteST.put(key[10], coord[2][2]);
		whiteST.put(key[11], coord[2][3]);
		
		//creating red checkers
		String rC = "redChecker";
		for (int h = 12; h < 24; h++) {
			String s = "" + h;
			checker[h] = new Checker (key[h], rC.concat(s));
		}
		//creating symbol table of keys and coordinates for white checkers
		BinarySearchST<Integer, Coordinates> redST = new BinarySearchST<>();
		redST.put(key[12], coord[3][0]);
		redST.put(key[13], coord[3][1]);
		redST.put(key[14], coord[3][2]);
		redST.put(key[15], coord[3][3]);
		redST.put(key[16], coord[4][0]);
		redST.put(key[17], coord[4][1]);
		redST.put(key[18], coord[4][2]);
		redST.put(key[19], coord[4][3]);
		redST.put(key[20], coord[5][0]);
		redST.put(key[21], coord[5][1]);
		redST.put(key[22], coord[5][2]);
		redST.put(key[23], coord[5][3]);
		
		//printing checkers
		System.out.println("checker name (key)");
		for (int i = 0; i < checker.length; i++) {
			System.out.println(checker[i].toString());
		}
				
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
        
        //creating checkers (x, y, radius)
        StdDraw.setPenColor(StdDraw.WHITE);
        double radius = 0.4;
        	//row 1
        StdDraw.filledCircle(0.5, 7.5, radius);
        StdDraw.filledCircle(2.5, 7.5, radius);
        StdDraw.filledCircle(4.5, 7.5, radius);
        StdDraw.filledCircle(6.5, 7.5, radius);
        	//row2
        StdDraw.filledCircle(1.5, 6.5, radius);
        StdDraw.filledCircle(3.5, 6.5, radius);
        StdDraw.filledCircle(5.5, 6.5, radius);
        StdDraw.filledCircle(7.5, 6.5, radius);
        	//row 3
        StdDraw.filledCircle(0.5, 5.5, radius);
        StdDraw.filledCircle(2.5, 5.5, radius);
        StdDraw.filledCircle(4.5, 5.5, radius);
        StdDraw.filledCircle(6.5, 5.5, radius);
        	//row 6
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(1.5, 2.5, radius);
        StdDraw.filledCircle(3.5, 2.5, radius);
        StdDraw.filledCircle(5.5, 2.5, radius);
        StdDraw.filledCircle(7.5, 2.5, radius);
        	//row 7
        StdDraw.filledCircle(0.5, 1.5, radius);
        StdDraw.filledCircle(2.5, 1.5, radius);
        StdDraw.filledCircle(4.5, 1.5, radius);
        StdDraw.filledCircle(6.5, 1.5, radius);
        	//row 8
        StdDraw.filledCircle(1.5, 0.5, radius);
        StdDraw.filledCircle(3.5, 0.5, radius);
        StdDraw.filledCircle(5.5, 0.5, radius);
        StdDraw.filledCircle(7.5, 0.5, radius);
    }
		
	

}
