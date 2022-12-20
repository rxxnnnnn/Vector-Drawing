import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class CanvasView extends Canvas implements IView{
    static double WIDTH = SketchIt.WIDTH-140;
    static double HEIGHT = SketchIt.HEIGHT-25;
    private Model model;
    CanvasView(Model model) {

        this.model = model;
        this.setWidth(WIDTH);
        this.setHeight(WIDTH);

        //controller
        this.setOnMousePressed(mouseEvent -> {
            model.pressed(mouseEvent.getX(),mouseEvent.getY());
        });
        this.setOnMouseDragged(mouseEvent -> {
            model.dragged(mouseEvent.getX(),mouseEvent.getY());
        });

        model.addView(this);
    }

    //resizeCanvas
    public void resizeCanvas(double width, double height){
        this.setWidth(width);
        this.setHeight(height);
        model.resize(width,height);
    }
    //when notified by model, update the display
    public void updateView(){
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        for(int i = 0;i<model.shapes.size();i++){
            model.shapes.get(i).draw(gc);
            gc.beginPath();
        }
    }

}