package gp;

import java.util.Arrays;

import a07.SnowWaterEquivalent;
import edu.princeton.cs.algs4.ST;

public class CheckerBoard {

		
	
		public static void main(String[] args) {
        
		
		//creating white checkers	
		String[] key = new String[24];
		String wC = "whiteChecker";
		for (int i = 0; i < 12; i++) {
			key[i] = wC.concat(Character.toString(i));
		}
		String bC = "blackChecker";
		for (int j = 0; j < 12; j++) {
			key[j] = bC.concat(Character.toString(j));
		}
		
				
		ST<key, Coordinate> st = new ST<>();
			
			
			
			
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
