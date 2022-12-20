import com.sun.webkit.Timer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.lang.Math;
import javax.sound.midi.Receiver;
import java.util.ArrayList;

public class Model{
    private ArrayList<IView> views = new ArrayList<>();


    public void addView(IView view){
        views.add(view);
        view.updateView();
    }


    boolean saved = false;
    String name;
    boolean named = false;
    public void savemodel(){saved = true;}
    public boolean ifsaved(){return saved;}
    public boolean ifnamed(){ return  named;}
    public void setname(String s){
        named = true;
        name = s;
    }
    public String getname(){return  name;}
    public void clear(){
        shapes.clear();
        linecolor = Color.BLACK;
        fillenable = true;
        fillcolor = Color.WHITE;
        thick = 1;
        style = 1;
        saved = false;
        name = "";
        named=false;
        notifyObservers();
    }
    public void setShapes(ArrayList<IShape> s){
        shapes.clear();
        shapes=s;
        notifyObservers();
    }

    ArrayList<IShape> shapes = new ArrayList<>();
    ArrayList<IShape> last_shapes = new ArrayList<>();
    ArrayList<IShape> before_select = new ArrayList<>();

    Model(){}
    MLine line = new MLine();
    MRect rect = new MRect();
    MCircle circle = new MCircle();


    String ActiveTool = "Select";
    javafx.scene.paint.Color linecolor = Color.BLACK;
    boolean fillenable = true;
    javafx.scene.paint.Color fillcolor = Color.WHITE;
    int thick = 1;
    int style = 1;

    public void setLinecolor(Color lc){
        linecolor=lc;
        if(ActiveTool=="Select"){
            for(IShape shape : shapes){
                if(shape.ifselect()){
                    copyshapes(shapes,last_shapes);
                    shape.setlinec(lc);
                    notifyObservers();
                }
            }
        }
    }
    public void setFillcolor(javafx.scene.paint.Color fc){
        fillcolor=fc;
        if(ActiveTool=="Select"){
            for(IShape shape : shapes){
                if(shape.ifselect()&&shape.gettype()!="Line"){
                    copyshapes(shapes,last_shapes);
                    shape.setfillc(fc);
                    notifyObservers();
                }
            }
        }
    }
    public void setThick(int t){
        thick=t;
        if(ActiveTool=="Select"){
            for(IShape shape : shapes){
                if(shape.ifselect()){
                    copyshapes(shapes,last_shapes);
                    shape.setthick(t);
                    notifyObservers();
                }
            }
        }

    }

    public void setStyle(int s){
        style=s;
        if(ActiveTool=="Select"){
            for(IShape shape : shapes){
                if(shape.ifselect()){
                    copyshapes(shapes,last_shapes);
                    shape.setstyle(s);
                    notifyObservers();
                }
            }
        }
    }

    public void selectshape(){ActiveTool = "Select";}

    public void eraseshape(){
        for(IShape shape : shapes){
            shape.setselect(false);
        }
        ActiveTool = "Erase";
        notifyObservers();
    }

    public void drawline(){
        for(IShape shape : shapes){
            shape.setselect(false);
        }
        ActiveTool = "Line";
        notifyObservers();
    }

    public void drawcircle(){
        for(IShape shape : shapes){
            shape.setselect(false);
        }
        ActiveTool = "Circle";
        notifyObservers();
    }

    public void drawrec(){
        for(IShape shape : shapes){
            shape.setselect(false);
        }
        ActiveTool = "Rect";
        notifyObservers();
    }

    public void fillcolor(){
        for(IShape shape : shapes){
            shape.setselect(false);
        }
        ActiveTool = "Fill";
        notifyObservers();
    }

    double clickedx, clickedy;
    boolean selected=false;
    boolean existline = false;
    boolean existnotline = false;

    public void pressed(double x, double y){
        if(ActiveTool=="Line"){
            copyshapes(shapes,last_shapes);
            line.setX1(x);
            line.setY1(y);
            shapes.add(line);
        }
        if(ActiveTool=="Circle"){
            copyshapes(shapes,last_shapes);
            circle.setX(x);
            circle.setY(y);
            shapes.add(circle);
        }
        if(ActiveTool=="Rect"){
            copyshapes(shapes,last_shapes);
            rect.setX(x);
            rect.setY(y);
            shapes.add(rect);
        }
        if(ActiveTool=="Erase"){
            for(int i = shapes.size()-1;i>=0;i--){
                if(shapes.get(i).ifcontains(new Point2D(x,y))){
                    copyshapes(shapes,last_shapes);
                    shapes.remove(i);
                    notifyObservers();
                    break;
                }
            }
        }
        if(ActiveTool=="Select"){
            boolean success = false;
            fillenable = true;
            notifyObservers();

            for(int i = shapes.size()-1;i>=0;i--){
                if(shapes.get(i).ifcontains(new Point2D(x,y))){
                    success=true;
                    shapes.get(i).setselect(true);
                    if(shapes.get(i).gettype().equals("Line")){
                        existline=true;
                        if(!existnotline)fillenable = false;
                    } else {
                        existnotline=true;
                        fillenable = true;
                        fillcolor=shapes.get(i).getfillc();
                    }
                    linecolor=shapes.get(i).getlinec();
                    thick=shapes.get(i).getthick();
                    style=shapes.get(i).getstyle();
                    //get the first clicked position
                    clickedx=x;
                    clickedy=y;
                    notifyObservers();
                    copyshapes(shapes,before_select);
                    copyshapes(before_select,last_shapes);
                    break;
                }
            }
            if(!success){
                for(IShape shape : shapes){
                    shape.setselect(false);
                    notifyObservers();
                }
                selected=false;
            }
        }
        if(ActiveTool=="Fill"){
            for(int i = shapes.size()-1;i>=0;i--){
                if(shapes.get(i).ifcontains(new Point2D(x,y))){
                    if(shapes.get(i).gettype()!="Line"){
                        copyshapes(shapes,last_shapes);
                        shapes.get(i).setfillc(fillcolor);
                    }
                    notifyObservers();
                    break;
                }
            }
        }

    }

    public void deselect(){
        for(IShape shape : shapes){
            shape.setselect(false);
        }
        notifyObservers();
    }

    public void delete(){
        int count = 0;
        if(ActiveTool=="Select"){
            for(int i = shapes.size()-1;i>=0;i--){
                if(shapes.get(i).ifselect()) {
                    if(count==0) {
                        copyshapes(shapes,last_shapes);
                    }
                    count++;
                    shapes.remove(i);
                    notifyObservers();
                }
            }
        }
    }

    public void dragged(double x, double y){
        if(ActiveTool=="Line"){
            shapes.set(shapes.size()-1,new MLine(line.x1,line.y1,x,y,linecolor,thick,style,SketchIt.currentWIDTH,SketchIt.currentHEIGHT));
            notifyObservers();
        }
        if(ActiveTool=="Circle"){
            double x1 = Math.min(x,circle.x);
            double x2 = Math.max(x,circle.x);
            double y1 = Math.min(y,circle.y);
            double y2 = Math.max(y,circle.y);
            double r = Math.max(y2-y1,x2-x1);
            shapes.set(shapes.size()-1,new MCircle(x1,y1,r,linecolor,fillcolor,thick,style,SketchIt.currentWIDTH,SketchIt.currentHEIGHT));
            notifyObservers();
        }
        if(ActiveTool=="Rect"){
            double x1 = Math.min(x,rect.x);
            double x2 = Math.max(x,rect.x);
            double y1 = Math.min(y,rect.y);
            double y2 = Math.max(y,rect.y);
            double width = x2-x1;
            double height = y2-y1;
            shapes.set(shapes.size()-1,new MRect(x1,y1,width,height,linecolor,fillcolor,thick,style,SketchIt.currentWIDTH,SketchIt.currentHEIGHT));
            notifyObservers();
        }
        if(ActiveTool=="Select"){
            copyshapes(before_select,last_shapes);
            double deltax=x-clickedx;
            double deltay=y-clickedy;
            clickedx=x;
            clickedy=y;
            for(int i = 0;i<shapes.size();i++){
                if(shapes.get(i).ifselect()){
                    IShape current = shapes.get(i);
                    current.moveshape(deltax,deltay);
                    shapes.set(i,current);
                    notifyObservers();
                }
            }
        }
    }


    public void resize(double newwidth, double newheight){
        notifyObservers();
        for (IShape shape : shapes){
            shape.resize(newwidth,newheight);
            notifyObservers();
        }
    }

    public void Undo(){
        copyshapes(last_shapes,shapes);
        notifyObservers();
    }

    public void copyshapes(ArrayList<IShape> origin, ArrayList<IShape> desti){
        desti.clear();
        for(IShape s : origin){
            if(s.gettype()=="Line") desti.add(new MLine((MLine) s));
            else if (s.gettype()=="Rect") desti.add(new MRect((MRect) s));
            else if (s.gettype()=="Circle") desti.add(new MCircle((MCircle) s));
        }
    }

    // Code from HelloMVC3/Model.java Example from CS349 Git Repository
    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private void notifyObservers() {
        saved = false;
        for (IView view : this.views) {
            view.updateView();
        }
    }

}