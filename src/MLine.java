import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import java.lang.Math;
import java.lang.Object.*;
import java.io.Serializable;

public class MLine implements IShape {
    double x1,y1,x2,y2;
    int mythick,mystyle;
    double stagewidth, stageheight;
    double red, green, blue, opacity;
    double thickness;
    boolean selected = false;
    double ratiox = 1, ratioy = 1;

    public MLine(MLine l){
        this.x1=l.x1;
        this.y1=l.y1;
        this.x2=l.x2;
        this.y2=l.y2;
        this.mythick=l.mythick;
        this.mystyle=l.mystyle;
        this.stagewidth=l.stagewidth;
        this.stageheight=l.stageheight;
        this.red=l.red;
        this.green=l.green;
        this.blue=l.blue;
        this.opacity=l.opacity;
        this.thickness=l.thickness;
        this.selected = l.selected;
        this.ratiox = l.ratiox;
        this.ratioy = l.ratioy;
    }
    public void setselect(Boolean b){selected=b;}
    public Color getfillc(){return new Color(red,green,blue,opacity);}
    public Color getlinec(){return new Color(red,green,blue,opacity);}
    public int getstyle(){return mystyle;}
    public int getthick(){return mythick;}
    public boolean ifselect(){return selected;}
    public void setfillc(Color c){ }
    public void setlinec(Color c){
        red=c.getRed();
        blue=c.getBlue();
        green=c.getGreen();
        opacity=c.getOpacity();
    }
    public void setstyle(int s){mystyle=s;}
    public void setthick(int t){mythick=t;}

    public void draw(GraphicsContext g) {
        g.setEffect(null);
        g.beginPath();
        g.setStroke(new Color(red,green,blue,opacity));
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

        if(selected){
            g.setEffect(new DropShadow(5,Color.RED));
        } else g.setEffect(null);
        g.strokeLine(x1*ratiox,y1*ratioy,x2*ratiox,y2*ratioy);
        g.setEffect(null);
        g.beginPath();
    }

    public boolean ifcontains(Point2D point){
        double x = point.getX();
        double y = point.getY();
        //find distance from point to line
        double a = x - x1*ratiox;
        double b = y - y1*ratioy;
        double c = x2*ratiox - x1*ratiox;
        double d = y2*ratioy - y1*ratioy;

        double e = a * c + b * d;
        double f = c * c + d * d;
        double param = e / f;
        double xx, yy;
        if (param < 0) {
            xx = x1*ratiox;
            yy = y1*ratioy;
        }
        else if (param > 1) {
            xx = x2*ratiox;
            yy = y2*ratioy;
        }
        else {
            xx = x1*ratiox + param * c;
            yy = y1*ratioy + param * d;
        }
        double dx = x - xx;
        double dy = y - yy;
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance<=thickness/2+3;
    }

    public String gettype(){return "Line";}

    MLine(){}

    MLine(double _x1,double _y1, double _x2, double _y2, Color c, int t, int s, double _stagewidth, double _stageheight) {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
        red=c.getRed();
        blue=c.getBlue();
        green=c.getGreen();
        opacity=c.getOpacity();
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
        x1=x1+dx;
        x2=x2+dx;
        y1=y1+dy;
        y2=y2+dy;
    }

    public void setX1(double _x){x1=_x;}
    public void setY1(double _y){y1=_y;}

}
