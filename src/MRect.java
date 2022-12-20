import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class MRect implements IShape{
    double x,y,width,height;
    int mythick,mystyle;
    double stagewidth, stageheight;
    double redl, greenl, bluel, opacityl;
    double redf, greenf, bluef, opacityf;
    double thickness;
    boolean selected = false;
    double ratiox=1, ratioy = 1;

    public MRect(MRect l){
        this.x=l.x;
        this.y=l.y;
        this.width=l.width;
        this.height=l.height;
        this.mythick=l.mythick;
        this.mystyle=l.mystyle;
        this.stagewidth=l.stagewidth;
        this.stageheight=l.stageheight;
        this.redl=l.redl;
        this.greenl=l.greenl;
        this.bluel=l.bluel;
        this.opacityl=l.opacityl;
        this.redf=l.redf;
        this.greenf=l.greenf;
        this.bluef=l.bluef;
        this.opacityf=l.opacityf;
        this.thickness=l.thickness;
        this.selected = l.selected;
        this.ratiox = l.ratiox;
        this.ratioy = l.ratioy;
    }

    public void setselect(Boolean b){selected=b;}
    public Color getfillc(){return new Color(redf,greenf,bluef,opacityf);}
    public Color getlinec(){return new Color(redl,greenl,bluel,opacityl);}
    public int getstyle(){return mystyle;}
    public int getthick(){return mythick;}
    public boolean ifselect(){return selected;}
    public void setfillc(Color c){
        redf=c.getRed();
        bluef=c.getBlue();
        greenf=c.getGreen();
        opacityf=c.getOpacity();
    }
    public void setlinec(Color c){
        redl=c.getRed();
        bluel=c.getBlue();
        greenl=c.getGreen();
        opacityl=c.getOpacity();
    }
    public void setstyle(int s){mystyle=s;}
    public void setthick(int t){mythick=t;}

    public void draw(GraphicsContext g) {
        g.setEffect(null);
        g.beginPath();
        g.setStroke(new Color(redl,greenl,bluel,opacityl));
        g.setFill(new Color(redf,greenf,bluef,opacityf));
        if(mythick==1){
            g.setLineWidth(1);
            if(mystyle==1) g.setLineDashes();
            if(mystyle==2) g.setLineDashes(6,6);
            if(mystyle==3) g.setLineDashes(2,6);
        }
        if(mythick==2){
            g.setLineWidth(5);
            if(mystyle==1) g.setLineDashes();
            if(mystyle==2) g.setLineDashes(10,10);
            if(mystyle==3) g.setLineDashes(7,10);
        }
        if(mythick==3){
            g.setLineWidth(10);
            if(mystyle==1) g.setLineDashes();
            if(mystyle==2) g.setLineDashes(15,15);
            if(mystyle==3) g.setLineDashes(6,15);
        }

        g.setEffect(null);
        g.fillRect(x*ratiox+thickness/2,y*ratioy+thickness/2,width*ratiox-thickness,height*ratioy-thickness);

        if(selected){
            g.setEffect(new DropShadow(5,Color.RED));
        } else g.setEffect(null);
        g.strokeRect(x*ratiox,y*ratioy,width*ratiox,height*ratioy);
        g.setEffect(null);
        g.beginPath();
    }

    public boolean ifcontains(Point2D point){
        Rectangle check = new Rectangle(x*ratiox-thickness/2,y*ratioy-thickness/2,width*ratiox+thickness,height*ratioy+thickness);
        return check.contains(point);
    }

    public String gettype(){return "Rect";}

    MRect(){}

    MRect(double _x,double _y, double _width, double _height, Color linec, Color fillc, int t, int s, double _stagewidth, double _stageheight) {
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        redl=linec.getRed();
        bluel=linec.getBlue();
        greenl=linec.getGreen();
        opacityl=linec.getOpacity();
        redf=fillc.getRed();
        bluef=fillc.getBlue();
        greenf=fillc.getGreen();
        opacityf=fillc.getOpacity();
        mythick = t;
        mystyle = s;
        stagewidth = _stagewidth;
        stageheight = _stageheight;
        if(mythick==1) thickness = 1;
        if(mythick==2) thickness = 5;
        if(mythick==3) thickness = 10;
    }

    public void resize(double neww, double newy){
        ratiox = neww/stagewidth;
        ratioy = newy/stageheight;
    }

    public void moveshape(double dx,double dy){
        x+=dx;
        y+=dy;
    }

    public void setX(double _x){x=_x;}
    public void setY(double _y){y=_y;}

}
