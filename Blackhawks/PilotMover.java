import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.*;

public class PilotMover
{

    public static void main(String[] args)
    {
        DifferentialPilot p = new DifferentialPilot(26f, 140f, Motor.A, Motor.C);

        while (true)
        {
            p.travel(200);
            p.rotateRight();
            System.out.println("Turn");
        }
    }
}
