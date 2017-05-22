import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.*;
public class Robot {
    private static final int DIS_TO_PUSHED_CAN = 10; //The distance to a can when it is being pushed
    private static final int DIR_ERROR = 5; //The degrees the robot can be off of the targeted can by before it makes an adjustment
    public static final int BLACK_THRESHOLD = 10; //The input value from the light sensor to indicate that it is on black

    private DifferentialPilot movePilot;
    private RegulatedMotor rotatePilot;
    private LightSense light;
    private UltrasonicSense distance;
    private double canAngle;
    private double initCanAngle;
    private double canDis;
    ScanThread thread;

    /**The behavior which the robot is in
     * 0 - Scanning for cans
     * 1 - Turning towards a can
     * 2 - Moving towards a can
     * 3 - Pushing out a can
     * 4 - Performing a final scan
     */
    private int behaviour;
    public Robot(RegulatedMotor m1, RegulatedMotor m2, RegulatedMotor rot, float diam, float axle) {
        movePilot = new DifferentialPilot(diam, axle, m1, m2);
        rotatePilot = rot;
        behaviour = 0;
        rotatePilot.resetTachoCount();
        canAngle = 180;
        canDis = 0;
        thread = new ScanThread(rotatePilot);
        light = new LightSense(this);
        distance = new UltrasonicSense(this);
        initAngle = 0;
        SensorPort.S2.addSensorPortListener(light);
        SensorPort.S3.addSensorPortListener(distance);
    }

    public void main() {
        while(true) {
            if(behaviour == 0) {
                this.getNearestCanAngle;
                this.switchBehaviour(1);
            } else if(behaviour == 1) {
                this.rotateToCan();
                this.switchBehaaviour(2);
            } else if(behaviour == 2) {
                this.moveToCan();
            } else if(behaviour == 3) {
                
            }
        }
    }

    public void rotateToCan() {
        movePilot.turn(canAngle);
        rotatePilot.rotateTo(0);
    }

    public void moveToCan() {
        movePilot.forward();
    }

    /**
     * Scans 360 degrees, then reports the angle to the nearest can and 
     * rotates the ultrasonic back to center. If this is close to 180 degrees then all cans
     * not obstructed by the box have been removed.
     */
    public double getNearestCanAngle() {
        rotatePilot.rotateTo(0);
        rotatePilot.rotate(360);
        rotatePilot.resetTachoCount();
        initCanAngle = canAngle;
        return canAngle;
    }

    /**
     * Called to change the robots mode
     */
    public void switchBehaviour(int nmode) {
        thread.terminate();
        if(nmode == 0) {
            rotatePilot.rotateTo(0);
            canAngle = 180;
            canDis = 0;
        } else if(nmode == 2) {
            rotatePilot.rotateTo(0);
            thread.start();
        } else if(nmode == 3) {
            movePilot.forward();
        }
        behaviour = nmode;
    }

    /**
     * Called by light sensor when the robot is on the edge
     */
    public void detectEdge() {
        if(behaviour == 3) {
            movePilot.stop();
            movePilot.rotate(initCanAngle-180);
            this.switchBehaviour(0);
        }
    }

    /**
     * Called by the Ultrasonic Sensor when a can is detected
     */
    public void detectCan(double dis) {
        if(behaviour == 0) {
            if(Math.min(rotatePilot.getTachoCount(), 360-rotatePilot.getTachoCount()) < Math.min(canAngle, 360-canAngle)) {
                canAngle = rotatePilot.getTachoCount();
                canDis = dis;
            }
        } else if(behaviour == 2) {
            canAngle = rotatePilot.getTachoCount();
            canDis = dis;
            if(canDis < DIS_TO_PUSHED_CAN) {
                this.switchBehaviour(3);
            }if(Math.abs(canAngle) > DIR_ERROR) {
                movePilot.rotate(canAngle);
            }
        }
    }
}
