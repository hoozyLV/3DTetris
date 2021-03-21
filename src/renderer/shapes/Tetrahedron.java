package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import renderer.point.MyVector;

public class Tetrahedron {
    private MyPolygon[] polygons;
    private Color color;
    
    public Tetrahedron(Color color, boolean decayColor,MyPolygon... polygons){
        this.color = color;
        this.polygons = polygons;
        
        if(decayColor){
            this.setDecayingPolygonColor();
        } else {
            this.setPolygonColor();
        }
        
        //MyPolygon.sortPolygons(this.polygons);
        this.sortPolygons();
    }
    
    public Tetrahedron(MyPolygon... polygons){
        this.color = Color.WHITE;
        this.polygons = polygons;
        this.sortPolygons();
    }
    
    public void render(Graphics g){
        for(MyPolygon poly : this.polygons){
            poly.render(g);
        }
    }
    
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector){
        for(MyPolygon p : this.polygons){
            p.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector);
        }
        this.sortPolygons();
    }
    
    public void setlighting(MyVector lightVector){
        for(MyPolygon p : this.polygons){
            p.setLighting(lightVector);
        }
    }
    
    public MyPolygon[] getPolygons(){
        return this.polygons;
    }
    
    private void sortPolygons(){
        MyPolygon.sortPolygons(this.polygons);
    }
    
    private void setPolygonColor(){
        for(MyPolygon poly : this.polygons){
            poly.setColor(this.color);
        }
    }
    
    private void setDecayingPolygonColor(){
        double decayFactor = 0.99;
        for(MyPolygon poly : this.polygons){
            poly.setColor(this.color);
            int r = (int) (this.color.getRed() * decayFactor);
            int g = (int) (this.color.getGreen() * decayFactor);
            int b = (int) (this.color.getBlue() * decayFactor);
            
            this.color = new Color(r,g,b);
        }
    }
}
