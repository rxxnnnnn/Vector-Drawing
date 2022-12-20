import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public interface IShape extends Serializable{
    static final long serialVersionUID = 1L;
    void draw(GraphicsContext g);
    boolean ifcontains(Point2D point);
    String gettype();
    void setselect(Boolean b);
    Color getfillc();
    Color getlinec();
    int getstyle();
    int getthick();
    boolean ifselect();
    void setfillc(Color c);
    void setlinec(Color c);
    void setstyle(int s);
    void setthick(int t);
    void resize(double newwidth, double newheight);
    void moveshape(double dx, double dy);
}
