import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
public class LightSense implements SensorPortListener {

    private final int DARK_COLOR = 770;
    
    Robot rob;
    public LightSense(Robot r) {
        rob = r;
        SensorPort.S1.addSensorPortListener(this);
    }
    
    public void stateChanged(SensorPort a, int b, int c) {
        //System.out.println("Stuff: " + c+" "+b);
        if (c > DARK_COLOR) {
            rob.edgeReached();
        }
    }
}