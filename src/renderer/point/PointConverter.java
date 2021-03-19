package renderer.point;

import java.awt.Point;
import renderer.Display;

public class PointConverter {
    
    private static double scale = 1;
    
    public static final double zoomFactor = 1.2;
    
    public static void zoomIn(){
        scale *= zoomFactor;
    }
    
    public static void zoomOut(){
        scale /= zoomFactor;
    }
    
    public static Point convertPoint(MyPoint point3D) {
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth = point3D.x * scale;
        
        double[] newVal = scale(x3d, y3d, depth);
        
        int x2d = (int)(Display.WIDTH / 2 + newVal[0]);
        int y2d = (int)(Display.HEIGHT / 2 - newVal[1]);
        
        Point point2d = new Point(x2d, y2d);
        return point2d;
    }
    
    private static double[] scale(double x3d, double y3d, double depth){
        double dist = Math.sqrt(x3d*x3d + y3d*y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth; //depth relative to camera
        double localScale = Math.abs(1400/(depth2 + 1400));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);
        return newVal;
    }
    
    public static void rotateAxisX(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.y * p.y + p.z * p.z);
        double theta = Math.atan2(p.z, p.y);
        theta -= Math.PI / 180 * degrees;
        p.y = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }
    
    public static void rotateAxisY(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.x * p.x + p.z * p.z);
        double theta = Math.atan2(p.z, p.x);
        theta += Math.PI / 180 * degrees;
        p.x = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }
    
    public static void rotateAxisZ(MyPoint p, boolean CW, double degrees){
        double radius = Math.sqrt(p.x * p.x + p.y * p.y);
        double theta = Math.atan2(p.y, p.x);
        theta -= Math.PI / 180 * degrees;
        p.x = radius * Math.cos(theta);
        p.y = radius * Math.sin(theta);
    }
            
            
}