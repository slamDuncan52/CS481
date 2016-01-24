public class Driver 
{
   public static void main(String [] args) 
   {
      Environment curEnv = new Environment(15,12,11);
      carAgent curCar = new carAgent(curEnv);
      curCar.run();
      
   }
}