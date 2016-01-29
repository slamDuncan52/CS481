import java.util.*;
/*
Driver Class
Mitchell Duncan
January 24 2016
Collects user input, instantiates classes, and runs accident finding program
*/
public class Driver 
{
/*
runRandom
input: none
output: a true when the accident is found
Moves the car in random directions until the accident is found
Author: Mitchell Duncan
*/

   public static void main(String [] args) 
   {
      final int gridSize = 10;//Can be changed to alter size of map  
      boolean debug = false;
      Scanner userInput = new Scanner(System.in);
      char routeSelect = ' ';
      if(args.length == 1 && args[0].equals("-d"))//Check if we're getting debug output
      {
         debug = true;
      }
      Random randGen = new Random();
      int xLoc = randGen.nextInt(gridSize);
      int yLoc = randGen.nextInt(gridSize);//Randomly select an accident site within the grid
      System.out.println("Welcome to the cop find simulator! The accident is at: "+ xLoc + "," + yLoc);
      System.out.println("Please enter 'p' for a planned path or 'r' for a random path: ");
      routeSelect = userInput.findInLine(".").charAt(0);//Grab the r or p
   
      Environment curEnv = new Environment(gridSize,xLoc,yLoc,debug);//Create our environment
      CarAgent curCar = new CarAgent(curEnv);//Pass it to out new car
      if(routeSelect == 'r')//Run a planned route
      {
         if(debug)//Set the debug printing header
         {
            System.out.println("Time  Car Location  Car Direction  Green Light Direction  Move Chosen");
         }
         curCar.runRandom();
      }
      else if(routeSelect == 'p')//Run a random route
      {
         if(debug)//Set the debug printing header
         {
            System.out.println("Time  Car Location  Car Direction  Green Light Direction  Move Chosen");
         }
         curCar.runPlanned();
      }     
   
   }
}
