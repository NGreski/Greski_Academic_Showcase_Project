import java.util.*;
public class rubikscubetest {
    public static void main (String[] args){
        
        double moves = 0.0; //added by Noah

        for(int i = 0; i < 10; i++){ //loop added by Noah
            rubikscube r=new rubikscube(2);
            r.scramble();
            r.solve();
            moves+= r.printMoveCount();
        }

        System.out.println("Average Moves: " + moves/10); //added by Noah


        

        //commented out by Noah, not looking at 3x3 cubes
        // rubikscube c=new rubikscube(3);
        // c.scramble();
        // c.solve();
    }
}
