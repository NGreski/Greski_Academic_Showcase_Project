/**
 * CODE FROM Nabil-1999
 * EDITED BY NOAH GRESK
 */

public class Main {

	public static void main(String[] args) {
		
		RubikCube c = new RubikCube();

		double averageMoves = 0;
		for(int i=0; i < 10; i++){ //edited number of cubes to solve for testing(added by Noah)
			String s = RubikCube.generateScramble();		
			c.scramble(s);
			
			averageMoves+= c.Solve();	
		} 
		
		System.out.println("Average Moves: " + averageMoves/10); //edited for testing
		
	}
}
