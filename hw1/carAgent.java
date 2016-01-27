public class carAgent
{
   int bumpCount = 0;
   int gridLength = 0;
   int spiralLevel = 1;
   int spiralSide = 1;
   int distance = 0;
   int[] perceptArray = new int[5];
   int carDir = 0; //0 for E, 1 for N, 2 for W, 3 for S
   Environment myEnv;
   
   public carAgent(Environment theEnv)
   {
      myEnv = theEnv;
   }
   public boolean run()
   {
      perceptArray = myEnv.getPercepts();
      while(bumpCount == 0)
      {
         myEnv.goStraight();
         myEnv.waitAtSignal();
         //printMove(0);
         perceptArray = myEnv.getPercepts();
         if(perceptArray[4] == 1)
         {
            report();
            return true;
         }
         else if(perceptArray[2] == 1)
         {
            bumpCount++;
         }
         else
         {
            gridLength++;
         }
      }   
      while(spiralLevel < gridLength)
      {
         myEnv.waitAtSignal();
         myEnv.turnLeft();
         perceptArray = myEnv.getPercepts();
         if(perceptArray[4] == 1)
         {
            report();
            return true;
         }
         distance = 0;
         spiralSide = (spiralSide + 1) % 2;
         spiralLevel += spiralSide;
         while(distance < gridLength - spiralLevel)
         {
            myEnv.goStraight();
            myEnv.waitAtSignal();
            perceptArray = myEnv.getPercepts();
            if(perceptArray[4] == 1)
            {
               report();
               return true;
            }
            distance++;
         }
      
      }
      return false;
   }
   
   private void printMove(int actionTaken)
   {
   /*
   Actions taken: go straight: 0
                  turn left: 1
                  turn right: 2
                  wait: 3
   */
      System.out.println(""); //Will do later....
   }
   
   private void report()
   {
      int finalTime = myEnv.getTime();
      System.out.println("Reached Destination in " + finalTime + " minutes.");
   }
}
