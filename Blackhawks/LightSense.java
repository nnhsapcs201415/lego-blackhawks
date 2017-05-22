import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
public class LightSense implements SensorPortListener {
    private Robot robot;
    public LightSense(Robot rob) {
        robot = rob;
    }

    public void stateChanged(SensorPort a, int b, int c) {
        if(c > BLACK_THRESHOLD) {
            robot.detectLine();
        }
    }
}