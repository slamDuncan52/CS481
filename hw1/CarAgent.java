import java.util.Random;
/*
CarAgent Class
Mitchell Duncan
January 24 2016
Navigates the cop car in a spiral pattern around the square map, stopping once it finds the accident
*/
public class CarAgent
{
   boolean bumped = false;//Used to find size of grid
   int gridLength = 0;
   int spiralLevel = 1;//Used to decrement driving distances within spiral loops
   int spiralSide = -1;//Helper var to assist in incrementing spiralLevel
   int distance = 0;//Distance traveled in this arm of the spiral
   int[] perceptArray = new int[5];
   int carDir = 0; //0 for E, 1 for N, 2 for W, 3 for S
   Environment myEnv;
   
/*
CarAgent
input: Environment Object to provide methods to the car
output: None
Just passes an environment to the car object so it can move
Author: Mitchell Duncan
*/  
   public CarAgent(Environment theEnv)
   {
      myEnv = theEnv;
   }
   
/*
runPlanned
input: none
output: a boolean true if the accident is found, false if the whole map is explored without finding.
Moves the car in a spiral pattern until the accident is found
Author: Mitchell Duncan
*/    
   public boolean runPlanned()
   {
      perceptArray = myEnv.getPercepts();
      while(!bumped)//Go straight until we hit the end
      {
         myEnv.goStraight(false);
         myEnv.waitAtSignal();//Wait after every move for the lights to change
         perceptArray = myEnv.getPercepts();//Check if we found the accident at each location
         if(perceptArray[4] == 1)
         {
            int finalTime = myEnv.stop();
            System.out.println("Reached Destination in " + finalTime + " minutes.");
            return true;
         }
         else if(perceptArray[2] == 1)//We bumped!
         {
            bumped = true;
         }
         else//Otherwise, the grid is larger still
         {
            gridLength++;
         }
      }
      while(spiralLevel < gridLength)//This will never run out before we explore the whole map
      {
         myEnv.turnLeft();//Turn onto the next arm of the spiral
         perceptArray = myEnv.getPercepts();//Check if the accident is here
         if(perceptArray[4] == 1)
         {
            int finalTime = myEnv.stop();
            System.out.println("Reached Destination in " + finalTime + " minutes.");
            return true;
         }
         distance = 0;//Reset distance along this arm
         spiralLevel += spiralSide;//If the arm wasn't updated last turn, update it
         spiralSide = (spiralSide + 1) % 2;//Toggle the arm update flag
         while(distance < gridLength - spiralLevel)//The distance of each arm decreases every two turns
         {
            myEnv.goStraight(false);//Yet again, go straight, wait, check if we found accident
            myEnv.waitAtSignal();
            perceptArray = myEnv.getPercepts();
            if(perceptArray[4] == 1)
            {
               int finalTime = myEnv.stop();
               System.out.println("Reached Destination in " + finalTime + " minutes.");
               return true;
            }
            distance++;
         }
      }
      return false;//Should be unreachable, as the above will fully explore a square map
   }

/*
runRandom
input: none
output: a true when the accident is found
Moves the car in random directions until the accident is found
Author: Mitchell Duncan
*/     
   public boolean runRandom()
   {
      boolean accidentFound = false;
      Random randDir = new Random();
      int curDir = -1;
      while(!accidentFound)//Until we find the accident
      {
         curDir = randDir.nextInt(4);//Pick a random action
         if(curDir == 0)//And just run with it
         {
            myEnv.goStraight(false);
         }
         else if(curDir == 1)
         {
            myEnv.turnLeft();
         }
         else if(curDir == 2)
         {
            myEnv.turnRight();
         }
         else if(curDir == 3)
         {
            myEnv.waitAtSignal();
         }
         perceptArray = myEnv.getPercepts();//After the action, check if the accident is here
         if(perceptArray[4] == 1)
         {
            int finalTime = myEnv.stop();
            System.out.println("Reached Destination in " + finalTime + " minutes.");
            accidentFound = true;
         }
      
      }
      return true;//We'll find it eventually
   }
}
