import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
public class TouchSense implements SensorPortListener {
    public void stateChanged(SensorPort a, int b, int c) {
        System.out.println("It moved!");
    }
}