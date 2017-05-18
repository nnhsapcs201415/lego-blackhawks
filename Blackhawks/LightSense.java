import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
import java.util.concurrent.TimeUnit;
public class LightSense implements SensorPortListener {
    public void stateChanged(SensorPort a, int b, int c) {
        System.out.println("Stuff: " + c+" "+b);
    }
}