public class Robot {
    private static double x;
    private static double y;
    private static double dir;
    public static void main(String[] args) {
        x = 0;
        y = 0;
        dir = 0;

    }

    //These methods assume radius = 1, and position of jars are fractions of the radius
    public static int[] getBestPath(Jar[] j) {
        int start = 0;
        for(int i = 0; i < j.length; i++) {

        }
    }

    public static int[] getBestPathFrom(Jar[] jars, int start) {
        Jar[] j = new Jar[jars.length];
        for(int i = 0; i < jars.length; i++) {
            j[i] = jars[i];
        }
        double x = 0;
        double y = 0;
        double cdir = getDir(x, y, j[start]);
        int[] ret = new int[j.length];
        ret[0] = start;
        for(int i = 1; i < ret.length; i++) {
            j[ret[i-1]] = null;
            double min = 10000000;
            int selection = -1;
            for(int ii = 0; ii < j.length; ii++) {
                if(j[ii] != null) {
                    if(Math.abs((cdir-getDir(x, y, j[ii]))%Math.PI) < min || Math.abs((cdir-getDir(x, y, j[ii]))%Math.PI-Math.PI) < min) {
                        min = Math.min(Math.abs((cdir-getDir(x, y, j[ii]))%Math.PI), Math.abs((cdir-getDir(x, y, j[ii]))%Math.PI-Math.PI));
                        selection = ii;
                    }
                }
            }
            ret[i] = selection;
            x = 
            cdir = Math.atan2(y, x);
        }
    }

    public static double getPathLength(int[] path, Jar[] j) {

    }

    public static double getDir(double x, double y, Jar j) {
        return Math.atan2(y-j.getY(), x-j.getX());
    }
}
