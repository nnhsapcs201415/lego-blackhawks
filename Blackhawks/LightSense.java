import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
public class LightSense implements SensorPortListener {
    public void stateChanged(SensorPort a, int b, int c) {
        System.out.println("Stuff: " + c+" "+b);
    }
}