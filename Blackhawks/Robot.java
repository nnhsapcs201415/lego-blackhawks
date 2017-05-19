import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.*;
public class Robot {
    private static final int DIS_TO_PUSHED_CAN = 10; //The distance to a can when it is being pushed
    private static final int DIR_ERROR = 5; //The degrees the robot can be off of the targeted can by before it makes an adjustment
    
    private DifferentialPilot movePilot;
    private RegulatedMotor rotatePilot;
    private LightSense light;
    private UltrasonicSense distance;
    private double canAngle;
    private double canDis;
    
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
    }
    
    public void main() {
        
    }
    
    /**
     * Used to interupt current movement to make an adjustment to the direction
     */
    public void poke(double offset) {
        pilot.rotate(offset);
    }
    
    public void moveToCan() {
        movePilot.turn(canAngle);
        rotatePilot.rotateTo(0);
        
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
        return canAngle;
    }
    
    /**
     * Called to change the robots mode
     */
    public void switchBehaviour(int nmode) {
        if(nmode == 0) {
            rotatePilot.rotateTo(0);
            rotatePilot.resetTachoCount();
            canAngle = 180;
            canDis = 0;
        }
        behaviour = nmode;
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
            if(Math.abs(canAngle) > DIR_ERROR) {
                movePilot.rotate(canAngle);
            }
        }
    }
}
