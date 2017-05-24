import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
public class UltrasonicSense implements FeatureListener {
    //The maximum detection distance to indicate a can
    public static int MAX_DETECT = 140;
    Robot rob;
    public UltrasonicSense(Robot r) {
        rob = r;
    }
    
    public void featureDetected(Feature feature, FeatureDetector detector) {
        double range = feature.getRangeReading().getRange();
        if(range < MAX_DETECT && range > 0.1) {
            rob.detectCan(range);
        }
    }
}