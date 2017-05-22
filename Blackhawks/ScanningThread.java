import lejos.nxt.*;
public class ScanningThread extends Thread {
    RegulatedMotor motor;
    Robot rob;
    public ScanningThread(RegulatedMotor m, Robot p) {
        motor = m;
        rob = p;
    }
    public void run() {
        
    }
}
