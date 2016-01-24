public class carAgent
{
   int bumpCount = 0;
   int len = 0;
   int spiralLevel = 1;
   int spiralSide = 0;
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
   
      return true;
   }
   
   private void printMove(int actionTaken)
   {
      System.out.println(""); //Will do later....
   }
   
   private void report()
   {
      int finalTime = myEnv.getTime();
      System.out.println("Reached Destination in " + finalTime + " minutes.");
   }
}