import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Motor.A.setAcceleration(100000);
        Motor.C.setAcceleration(100000);
        Motor.A.setSpeed(100000);// 2 RPM
        Motor.C.setSpeed(100000);// 2 RPM
        //Motor.A.backward();
        //Motor.C.forward();
        LightSensor light = new LightSensor(SensorPort.S1);
        //UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        int MAX_DISTANCE = 50;
        int PERIOD = 500;
        UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
        FeatureDetector fd = new RangeFeatureDetector(us, MAX_DISTANCE, PERIOD);
        ObjectDetect listener = new ObjectDetect();
        fd.addListener(listener);
        SensorPort.S3.addSensorPortListener(new LightSense());
        

        for(;;) {
            //LCD.drawInt(light.getLightValue(), 4, 0, 1);
            //LCD.drawInt(sonic.getDistance(), 4, 0, 3);
        }

    }
    private static class ObjectDetect implements FeatureListener {
        public static int MAX_DETECT = 80;
        public void featureDetected(Feature feature, FeatureDetector detector) {
            int range = (int)feature.getRangeReading().getRange();
            Sound.playTone(1200 - (range * 10), 100);
            System.out.println("Range:" + range);
        }
    }
    private static class LightSense implements SensorPortListener {
        public void stateChanged(SensorPort a, int b, int c) {
            System.out.println("Stuff: " + c+" "+b);
        }
    }
    private static class TouchSense implements SensorPortListener {
        public void stateChanged(SensorPort a, int b, int c) {
            System.out.println("It moved!");
        }
    }
}