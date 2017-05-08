import lejos.nxt.Button;
import lejos.nxt.Motor;

public class HelloWorld
{
    public static void main(String[] args)
    {
        System.out.println("Hello World");
        //Button.waitForAnyPress();
        Motor.A.forward();
        Motor.C.backward();
    }
}