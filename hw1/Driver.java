public class Driver 
{
   public static void main(String [] args) 
   {
      Environment curEnv = new Environment(10,5,6);
      carAgent curCar = new carAgent(curEnv);
      curCar.run();
   }
}
