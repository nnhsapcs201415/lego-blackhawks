//This trash is useless
public class BadRobot {
    private static double x;
    private static double y;
    private static double dir;
    public static void main(String[] args) {
        x = 0;
        y = 0;
        dir = 0;
        Jar[] j = {new Jar(0.25, 0.25), new Jar(-0.1, 0.7), new Jar(-0.8, -0.8)};
        for(int i = 0; i < j.length; i++) {
            int[] path = getBestPathFrom(j, i);
            for(int ii = 0; ii < path.length; ii++) {
                System.out.print(path[ii]+", ");
            }
            System.out.print(getPathDistance(j, path)+"\n");
        }
    }

    //These methods assume radius = 1, and position of jars are fractions of the radius
    public static int[] getBestPath(Jar[] j) {
        int[] path = getBestPathFrom(j, 0);
        double dis = getPathDistance(j, path);
        for(int i = 1; i < j.length; i++) {
            int[] npath = getBestPathFrom(j, i);
            if(dis > getPathDistance(j, npath)) {
                for(int ii = 0; ii < path.length; ii++) {
                    path[ii] = npath[ii];
                }
                dis = getPathDistance(j, npath);
            }
        }
        return path;
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
            double ntheta = Math.signum((cdir-getDir(x, y, j[selection])))*(Math.PI-Math.abs((cdir-getDir(x, y, j[selection]))));
            cdir += Math.signum(ntheta)*(Math.PI-Math.abs(ntheta)*2.0);
            x = Math.cos(cdir);
            y = Math.sin(cdir);
        }
        return ret;
    }
    
    public static double getPathDistance(Jar[] jars, int[] path) {
        Jar[] j = new Jar[jars.length];
        for(int i = 0; i < jars.length; i++) {
            j[i] = jars[i];
        }
        double x = 0;
        double y = 0;
        double cdir = getDir(x, y, j[path[0]]);
        double dis = 0;
        for(int i = 1; i < path.length; i++) {
            double ntheta = Math.signum((cdir-getDir(x, y, j[path[i]])))*(Math.PI-Math.abs((cdir-getDir(x, y, j[path[i]]))));
            cdir += Math.signum(ntheta)*(Math.PI-Math.abs(ntheta)*2.0);
            dis += getPointDis(x, y, Math.cos(cdir), Math.sin(cdir));
            x = Math.cos(cdir);
            y = Math.sin(cdir);
        }
        return dis;
    }
    
    public static double getPointDis(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public static double getDir(double x, double y, Jar j) {
        return Math.atan2(y-j.getY(), x-j.getX());
    }
}
