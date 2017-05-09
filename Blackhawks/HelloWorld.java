import lejos.nxt.Button;
import lejos.nxt.Motor;
import java.util.concurrent.TimeUnit;

public class HelloWorld
{
    public static void main(String[] args)
    {
        System.out.println("Hello World");

        Motor.A.setAcceleration(100000);
        Motor.C.setAcceleration(100000);
        Motor.A.setSpeed(100000);// 2 RPM
        Motor.C.setSpeed(100000);// 2 RPM
        Motor.A.backward();
        Motor.C.forward();
        Button.waitForAnyPress();
        
        
    }
}