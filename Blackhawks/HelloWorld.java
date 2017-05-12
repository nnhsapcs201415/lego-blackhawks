import lejos.nxt.*;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Motor.A.setAcceleration(100000);
        Motor.C.setAcceleration(100000);
        Motor.A.setSpeed(100000);// 2 RPM
        Motor.C.setSpeed(100000);// 2 RPM
        //Motor.A.backward();
        //Motor.C.forward();
        LightSensor light = new LightSensor(SensorPort.S1);
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        for(;;) {
            LCD.drawInt(light.getLightValue(), 4, 0, 1);
            LCD.drawInt(sonic.getDistance(), 4, 0, 3);
        }

    }
}