/**
 * CODE FROM just-jay
 * EDITED BY NOAH GRESK
 */

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cube c = new Cube();

		double averageMoves = 0;
		for(int i=0; i < 1; i++){ //edited to 10 from 10k
			String s = Cube.generateScramble();		
			c.scramble(s);
			//System.out.println(c.Solve());
			averageMoves+= c.Solve();	
		} 
		System.out.println("Average Moves: " + averageMoves/1); //edited to 10 from 10k
	}
}

//39.66538
//39.70893
