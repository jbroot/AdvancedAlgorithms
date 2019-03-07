package cs5280assign1;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cs5280assign1 {

    private int[] hints;
    private int[] kints;
    
    public static void main(String[] args) {
        Cs5280assign1 mySum4 = new Cs5280assign1();
        mySum4.go();
    }
    
    private void go(){
        hints = toList("5Hints.txt");
        kints = toList("1Kints.txt");
        print(true);
        print(false);
    }
    
    private void print(boolean doHints){
        //loads array in the other class
        Improved improved = (doHints) ? new Improved(hints) : new Improved(kints);
        
        System.out.print(String.format("%-15s", ""));
        System.out.print(String.format("|%-20s|", "   sets in "+ (doHints ? "5Hints" : "1Kints")));
        System.out.println(String.format("%-20s|", " milliseconds elapsed "));
        
        StopWatch timer = new StopWatch();
        int brutes = (doHints) ? bruteForce(hints) : bruteForce(kints);
        long time = timer.elapsedTime();
        System.out.println(String.format("%-15s", "Brute Force") + 
                String.format("|     %-15d", brutes) + String.format("|     %-15d", time));
        
        StopWatch timer1 = new StopWatch();
        int imp = improved.improvement();
        long time1 = timer1.elapsedTime();
        System.out.println(String.format("%-15s", "Improved") + 
                String.format("|     %-15d", imp) + String.format("|     %-15d", time1));
    }

    //brute force
    private int bruteForce(int[] ogList){
        int count = 0;
        for (int i=0; i < ogList.length; ++i) {
            for (int j = i+1; j < ogList.length; ++j) {
                for (int k = j+1; k < ogList.length; ++k) {
                    for (int n = k+1; n < ogList.length; ++n) {
                        if(ogList[i]+ogList[j]+ogList[k]+ogList[n] == 0){
                            ++count;
                        }
                    }
                }
            }
        }
        return count;
    }
    
    //read data into list
    private int[] toList(String filePath){
            ArrayList<Integer> myList = new ArrayList<>();            
            Scanner fileStream = null;
        try {
            fileStream = new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
        Logger.getLogger(Cs5280assign1.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        while(fileStream.hasNextInt()){
            myList.add(fileStream.nextInt());
        }
        int[] converted = new int[myList.size()];
        for(int i=0; i< myList.size(); ++i){
            converted[i] = myList.get(i);
        }
        return converted;
    }
}
