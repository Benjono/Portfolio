/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;
import ianWakemanParser.*;
/**
 *
 * @author admin
 */
public class Futoshiki {
    public static void main(String[] args) {
        Parser p = new Parser();
        FutoshikiPuzzle fp = new FutoshikiPuzzle(5);
        Command c;
        do{
            //System.out.print(">");
            c = p.getCommand();
            switch(c.getCommand().getWord()){
                case "new":
                    if(c.getValue()>1 && c.getValue()<10){
                        fp = new FutoshikiPuzzle(c.getValue());
                    }
                    else{
                        fp = new FutoshikiPuzzle(5);
                    }
                    fp.fillPuzzle();
                    System.out.println(fp.displayString());
                    break;
                case "quit":
                    break;
                case "clear":
                    fp.setSquare(c.getRow(), c.getColumn(), 0, true);
                    System.out.println(fp.displayString());
                    break;
                case "mark":
                    fp.setSquare(c.getRow(), c.getColumn(), c.getValue(), true);
                    System.out.println(fp.displayString());
                    break;
                case "unknown":
                    System.out.println("Unknown command, please try again");
                    break;
            }
        }while(!c.getCommand().getWord().equals("quit"));
    }
}
