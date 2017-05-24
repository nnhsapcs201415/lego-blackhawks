import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.*;
public class ScanThread extends Thread {
    private RegulatedMotor motor;
    public ScanThread(RegulatedMotor m) {
        motor = m;
    }
    public void run() {
        for(;;) {
            motor.rotateTo(-30);
            motor.rotateTo(30);
        }
    }
}
