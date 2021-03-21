package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import renderer.entity.builder.BasicEntityBuilder;
import renderer.entity.builder.ComplexEntityBuilder;
import renderer.input.ClickType;
import renderer.input.Mouse;
import renderer.point.MyVector;
import renderer.point.PointConverter;

public class EntityManager {
    
    private List<IEntity> entities;
    
    private int initialX, initialY;
    private double mouseSensitivity = 0.3;
    private MyVector lightVector = MyVector.normalize(new MyVector(1,1,1));
    
    public EntityManager(){
        this.entities = new ArrayList<IEntity>();
    }
    
    public void init(){
        //this.entities.add(BasicEntityBuilder.createCube(100, 0, 0, 0));
        //this.entities.add(BasicEntityBuilder.createDiamond(new Color(200, 40, 150), 100, 0,0,0));
        this.entities.add(ComplexEntityBuilder.createRubiksCube(100, 0, 0,0));
        this.setLighting();
    }
    
    public void update(Mouse mouse){
        int x = mouse.getX();
        int y = mouse.getY();
        
        if(mouse.getButton() == ClickType.LeftClick){
            double xDiff = x - initialX;
            double yDiff = y - initialY;
            
            this.rotate(true, 0, -yDiff * mouseSensitivity, -xDiff * mouseSensitivity);
            
        } 
        else if(mouse.getButton() == ClickType.RightClick){
            double xDiff = x - initialX;
            
            this.rotate(true, xDiff * mouseSensitivity, 0, 0);
        }
        
        if(mouse.isScrollingUp()){
            PointConverter.zoomIn();
        } 
        else if(mouse.isScrollingDown()){
            PointConverter.zoomOut();
        }
        
        mouse.resetScroll();
        
        initialX = x;
        initialY = y;
    }
    
    public void render(Graphics g){
        for(IEntity e : this.entities){
            e.render(g);
        }
    }
    
    private void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees){
        for(IEntity e : this.entities){
            e.rotate(CW, xDegrees, yDegrees, zDegrees, this.lightVector);
            
        }
    }
    
    private void setLighting(){
        for(IEntity entity : this.entities){
            entity.setLighting(this.lightVector);
        }
    }
}
