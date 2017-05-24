import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.*;
public class Robot {
    private static final int DIS_TO_PUSHED_CAN = 10; //The distance to a can when it is being pushed
    private static final int DIR_ERROR = 10; //The degrees the robot can be off of the targeted can by before it makes an adjustment
    public static final int BLACK_THRESHOLD = 10; //The input value from the light sensor to indicate that it is on black

    private DifferentialPilot movePilot;
    private RegulatedMotor rotatePilot;
    private LightSense light;
    private UltrasonicSense distance;
    private int canAngle;
    private int initCanAngle;
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
        movePilot.reset();
        movePilot.setRotateSpeed(360);
        movePilot.setTravelSpeed(400);
        rotatePilot = rot;
        rotatePilot.setSpeed(100);
        behaviour = 0;
        rotatePilot.resetTachoCount();
        canAngle = 180;
        canDis = 0;
        thread = new ScanThread(rotatePilot);
        light = new LightSense(this);
        distance = new UltrasonicSense(this);
        initCanAngle = 0;
        SensorPort.S1.addSensorPortListener(light);
        UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
        FeatureDetector fd = new RangeFeatureDetector(us, 50, 500);
        fd.addListener(distance);
    }

    public void main() {
        while(true) {
            if(behaviour == 0) {
                this.getNearestCanAngle();
                System.out.println("Found cans, canAngle: "+canAngle);
                this.switchBehaviour(1);
            } else if(behaviour == 1) {
                this.rotateToCan();
                this.switchBehaviour(2);
            } else if(behaviour == 2) {
                if(!movePilot.isMoving()) {
                    movePilot.forward();
                }
            } else if(behaviour == 3) {

            }
        }
    }

    public static void main(String[] args) {
        Robot r = new Robot(Motor.B, Motor.C, Motor.A, 56f, 164f);
        r.main();
    }

    public void rotateToCan() {
        System.out.println("Rotating...  "+canAngle);
        movePilot.rotate(canAngle, false);
        rotatePilot.rotateTo(0, false);
        System.out.println("Done, now in Behaviour 2");
        return;
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
        rotatePilot.rotateTo(0, false);
        rotatePilot.rotate(360, false);
        rotatePilot.rotateTo(0, false);
        //rotatePilot.resetTachoCount();
        initCanAngle = canAngle;
        return canAngle;
    }

    /**
     * Called to change the robots mode
     */
    public void switchBehaviour(int nmode) {
        thread.interrupt();
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
     * Called by the Ultrasonic Sensor when a can is detected
     */
    public void detectCan(double dis) {
        if(behaviour == 0) {
            if(Math.min(Math.abs(rotatePilot.getTachoCount()), 360-Math.abs(rotatePilot.getTachoCount())) < Math.min(canAngle, 360-canAngle)) {
                canAngle = rotatePilot.getTachoCount();
                System.out.println(behaviour + ", "+canAngle);
                canDis = dis;
            }
        } else if(behaviour == 2) {
            System.out.println("Can found distance "+dis+" Direction "+rotatePilot.getTachoCount());
            canAngle = rotatePilot.getTachoCount();
            canDis = dis;
            if(canDis < DIS_TO_PUSHED_CAN && canDis > 0.1) {
                movePilot.stop();
                this.switchBehaviour(3);
            }if(Math.abs(canAngle) > DIR_ERROR) {
                movePilot.stop();
                System.out.println(behaviour+", "+canAngle);
                movePilot.rotate(canAngle, false);
                movePilot.forward();
            }
        }
    }

    /**
     * Called by LightSense when a dark line (edge) is reached)
     */
    public void edgeReached() {
        movePilot.quickStop();
        rotatePilot.rotate((int)initCanAngle-180, false);
        this.switchBehaviour(0);
    }
}
