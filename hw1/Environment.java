public class Environment
{
   private int gridSize;
   private int[] accidentLocation = new int[2];
   private int[] carLocation = new int[2];
   private int bump = 0;
   private int accidentReached = 0;
   private int time = 0;
   private int carDir = 0; //0 for E, 1 for N, 2 for W, 3 for S
   
   public Environment(int size, int accidentX, int accidentY)
   {
      gridSize = size;
      accidentLocation[0] = accidentX;
      accidentLocation[1] = accidentY;
   }
   
   public int[] getPercepts()
   {
      int[] perceptArr = new int[5];
      perceptArr[0] = carLocation[0];
      perceptArr[1] = carLocation[1];
      perceptArr[2] = bump;
      perceptArr[3] = time % 2; //Green Direction is N-S for odd, E-W for even
      perceptArr[4] = accidentReached;
      return perceptArr;
   }
   
   public int getTime()
   {
      return time;
   }
   
   public boolean goStraight()
   {
      if((time % 2) == (carDir % 2))
      {
         if(carDir == 0)
         {
            carLocation[0]++;
         } 
         else if (carDir == 1)
         {
            carLocation[1]++;
         }
         else if(carDir == 2)
         {
            carLocation[0]--;
         }
         else if(carDir == 3)
         {
            carLocation[1]--;
         }
         if(carLocation[0] > gridSize)
         {
            bump = 1;
            carLocation[0] = gridSize;
            time += 10;
         }
         else if(carLocation[1] > gridSize)
         {
            bump = 1;
            carLocation[1] = gridSize;
            time += 10;
         }
         else if(carLocation[0] < 0)
         {
            bump = 1;
            carLocation[0] = 0;
            time += 10;
         }
         else if(carLocation[1] < 0)
         {
            bump = 1;
            carLocation[1] = 0;
            time += 10;
         }
         else
         {
            bump = 0;
         }
         time += 3;
         if((carLocation[0] == accidentLocation[0]) && (carLocation[1] == accidentLocation[1]))
         {
            accidentReached = 1;
         }
         return true;
      }
      return false;
   }
   
   public boolean turnLeft()
   {
      if((time % 2) != (carDir % 2))
      {
         carDir = (carDir + 1) % 4;
         goStraight();
         time++;
         return true;
      }
      return false;   
   }
   
   public boolean turnRight()
   {
      if((time % 2) != (carDir % 2))
      {
         carDir = (carDir + 3) % 4;
         goStraight();
         time++;
         return true;
      }
      return false;  
   }
   
   public int stop()
   {
      if(accidentReached == 1)
      {
         return time;
      }
      return -1;
   }
   
   public void waitAtSignal()
   {
      time++;
      return;
   }
}
