import java.io.FileWriter;
import java.io.IOException;

/**
 * CODE FROM Nabil-1999
 * EDITED BY NOAH GRESK
 */

public class Main {

	public static void main(String[] args) {
		
		RubikCube c = new RubikCube();

		int moves = 0;
		double averageMoves = 0;
		long totalTime = 0;
		double numCubes = 10;
		double averageTime = 0;

		try (FileWriter writer = new FileWriter("X1_10.txt")) {
			for(int i=0; i < numCubes; i++){ //edited number of cubes to solve for testing(added by Noah)
				String s = RubikCube.generateScramble();		
				c.scramble(s);
				
				long start  = System.currentTimeMillis();
				moves += c.Solve();	
				long end = System.currentTimeMillis();
				totalTime += (end - start);
			} 


			averageMoves = moves/numCubes;
			averageTime = totalTime/numCubes;


			
			System.out.println("Average Moves: " + averageMoves); //edited for testing
			System.out.println("Average Time: " + String.format("%.6f", averageTime) + "ms"); 

			writer.write(numCubes + " " + averageMoves + " " + averageTime);
			
		}catch(IOException e){
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}
}
