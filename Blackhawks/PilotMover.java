import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.*;

public class PilotMover {

    public static void main(String[] args) {
        DifferentialPilot p = new DifferentialPilot(26f, 26f*3.035f, Motor.A, Motor.C);

        //while (true) {
            //p.setSpeed(30, 30);
            //p.travel(200);
            p.rotate(360);
        //}
    }
}
