/*
Environment Class
Mitchell Duncan
January 24 2016
Handles map, as well as movement and reporting methods for a cop car agent.
*/
public class Environment
{
   private int gridSize;
   private int[] accidentLocation = new int[2];
   private int[] carLocation = new int[2];
   private int bump = 0;//Flag if the car hits a border
   private int bumpCount = 0; //The number of bumps
   private int accidentReached = 0;//Flag if the car moves into the accident spot
   private int time = 0;//Ticks in minutes
   private int carDir = 0; //0 for E, 1 for N, 2 for W, 3 for S
   private boolean debug = false;
   
/*
Environment
input: size of the square map, accident location (x,y), and a boolean for path printing
output: none
Constructs an environment object
Author: Mitchell Duncan
*/
   public Environment(int size, int accidentX, int accidentY, boolean debugFlag)
   {
      gridSize = size;
      accidentLocation[0] = accidentX;
      accidentLocation[1] = accidentY;
      debug = debugFlag;//Used for verbose printing
   }

/*
getPercepts
input: none
output: An array containing the current car location, if a bump was detected, the current light
direction, and if the accident is here
Returns the above output
Author: Mitchell Duncan
*/   
   public int[] getPercepts()
   {
      int[] perceptArr = new int[5];
      perceptArr[0] = carLocation[0];//Car X
      perceptArr[1] = carLocation[1];//Car Y
      perceptArr[2] = bump;//If the car just moved into a border
      perceptArr[3] = time % 2; //Green Direction is N-S for odd, E-W for even
      perceptArr[4] = accidentReached;//If the car is in the accident square
      return perceptArr;
   }
/*
goStraight
input: a boolean saying if this straight is part of a turn or not (used for printing only)
output: A boolean if the move was successful
Moves the car one unit in the direction it's facing. If at a border, bumps, and fails
Author: Mitchell Duncan
*/   
   public boolean goStraight(boolean turning)
   {
      if(!turning)//If we're turning, the turn handles its own printing
      {
         printMove(0);
      }
      if((time % 2) == (carDir % 2))//If the lights are green in the car's direction
      {
         if(carDir == 0)//Move EAST
         {
            carLocation[0]++;
         } 
         else if (carDir == 1)//Move NORTH
         {
            carLocation[1]++;
         }
         else if(carDir == 2)//Move WEST
         {
            carLocation[0]--;
         }
         else if(carDir == 3)//Move SOUTH
         {
            carLocation[1]--;
         }
         if(carLocation[0] > gridSize)//If we moved too far east
         {
            bump = 1;
            bumpCount++;
            carLocation[0] = gridSize;
            time += 10;
         }
         else if(carLocation[1] > gridSize)//If we moved too far north
         {
            bump = 1;
            bumpCount++;
            carLocation[1] = gridSize;
            time += 10;
         }
         else if(carLocation[0] < 0)//If we moved too far west
         {
            bump = 1;
            bumpCount++;
            carLocation[0] = 0;
            time += 10;
         }
         else if(carLocation[1] < 0)//If we moved too far south
         {
            bump = 1;
            bumpCount++;
            carLocation[1] = 0;
            time += 10;
         }
         else//If the move was legal
         {
            bump = 0;
         }
         time += 3;//Moving costs 3 minutes
         if((carLocation[0] == accidentLocation[0]) && (carLocation[1] == accidentLocation[1]))//If we arrived in the accident square
         {
            accidentReached = 1;
         }
         return true;
      }
      return false;
   }
/*
turnLeft
input: none
output: A boolean if the turn was successful
Rotates the car to the left and moves the car one unit in the 
new direction. If turning into a border, bumps, and fails
Author: Mitchell Duncan
*/      
   public boolean turnLeft()
   {
      printMove(1);//Prints turn left info if debugging
      if((time % 2) == (carDir % 2))//Check the lights are green in the car dir
      {
         carDir = (carDir + 1) % 4;//Rotate car
         goStraight(true);//Move in the new direction
         time++;//Turning costs 4, the other 3 are covered by move straight
         return true;
      }
      return false;   
   }
/*
turnRight
input: none
output: A boolean if the turn was successful
Rotates the car to the right and moves the car one unit in the 
new direction. If turning into a border, bumps, and fails
Author: Mitchell Duncan
*/        
   public boolean turnRight()
   {
      printMove(2);//Prints turn right info if debugging
      if((time % 2) == (carDir % 2))//Check the lights are green in the car dir
      {
         carDir = (carDir + 3) % 4;//Rotate car
         goStraight(true);//Move in the new direction
         time++;//Turning costs 4, the other 3 are covered by move straight
         return true;
      }
      return false;  
   }
/*
stop
input: none
output: If at the accident, the final time and bump number, if not, -1s
No action is taken, merely checks if the current location is the accident location
Author: Mitchell Duncan
*/        
   public int[] stop()
   {
      int[] results = new int[2];
      results[0] = results[1] = -1;
      if(accidentReached == 1)//If we stopped in the accident space, return the number of minutes
      {
         results[0] = time;
         results[1] = bumpCount;
         return results;
      }
      return results;//Otherwise, return fail value
   }
/*
waitAtSignal
input: none
output: none
Waits at a light, ticks time
Author: Mitchell Duncan
*/     
   public void waitAtSignal()
   {
      printMove(3);//If debugging, prints wait info
      time++;//Costs a minute
      return;
   }
/*
stop
input: an int describing the action taken
      Actions taken: go straight: 0
                  turn left: 1
                  turn right: 2
                  wait: 3     
output: none
If debugging, prints out the current time and position data, and the move decided on.
Author: Mitchell Duncan
*/     
   private void printMove(int actionTaken)
   {
      if(debug)//Only print if we're debugging
      {
         String wordDir = new String();//Holds the car's direction as a word
         String wordLight = new String();//Holds the current Green Light direction as a word
         String wordMove = new String();//Holds the car's current action as a word
      //Direction conditional
         if(carDir == 0)
         {
            wordDir = "East";
         } 
         else if (carDir == 1)
         {
            wordDir = "North";
         } 
         else if (carDir == 2)
         {
            wordDir = "West";
         } 
         else if (carDir == 3)
         {
            wordDir = "South";
         }
      //Light conditional
         if(time % 2 == 0)
         {
            wordLight = "E-W";
         } 
         else if (time % 2 == 1)
         {
            wordLight = "N-S";
         }
      //Move conditional
         if(actionTaken == 0)
         {
            wordMove = "Go Straight";
         } 
         else if (actionTaken == 1)
         {
            wordMove = "Turn Left";
         } 
         else if (actionTaken == 2)
         {
            wordMove = "Turn Right";
         } 
         else if (actionTaken == 3)
         {
            wordMove = "Wait at Light";
         }
         System.out.println(time + "        " + carLocation[0] + "," + carLocation[1] + "            " + wordDir + "            " + wordLight + "     " + wordMove);
      }
   }
}
