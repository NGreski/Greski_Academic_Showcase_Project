import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class rubikscubetest {
    public static void main (String[] args){
        
        int moves = 0; //added by Noah
        double averageMoves;
        long totalTime = 0;
        double averageTime = 0;
        double numCubes = 10;

        try (FileWriter writer = new FileWriter("X2_10.txt")) {
            for(int i = 0; i < numCubes; i++){ //loop added by Noah
                rubikscube r=new rubikscube(2);
                r.scramble();

                long start  = System.currentTimeMillis();
                r.solve();
                long end = System.currentTimeMillis();
                totalTime += (end - start);

                moves+= r.printMoveCount();
            }

            averageMoves = moves/numCubes;
            averageTime = totalTime/numCubes;

            System.out.println("Average Moves: " + averageMoves); //edited for testing
            System.out.println("Average Time: " + String.format("%.6f", averageTime) + "ms"); 

            writer.write(numCubes + " " + averageMoves + " " + averageTime);
            

            //commented out by Noah, not looking at 3x3 cubes
            // rubikscube c=new rubikscube(3);
            // c.scramble();
            // c.solve();
        }catch(IOException e){
			System.err.println("Error writing to file: " + e.getMessage());
		}
    }
}
