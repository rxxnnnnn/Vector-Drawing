import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolbarView extends Pane implements IView{
    private Model model;
    static double stageW = SketchIt.WIDTH;
    static double WIDTH = SketchIt.WIDTH/4;
    static double HEIGHT = SketchIt.HEIGHT;
    static double B_WIDTH = 50;
    static double B_HEIGHT = 50;
    ArrayList<ToggleButton> tools;
    ArrayList<ToggleButton> thicks;
    ArrayList<ToggleButton> styles;
    ColorPicker linecolor;
    ColorPicker fillcolor;
    ToolbarView(Model model){
        this.model = model;
        this.setMinSize(WIDTH, HEIGHT);
        //icons
        Image arrow = new Image("images/arrow.png",B_WIDTH,B_HEIGHT,false,false);
        Image eraser = new Image("images/eraser.png",B_WIDTH,B_HEIGHT,false,false);
        Image line = new Image("images/line.png",B_WIDTH,B_HEIGHT,false,false);
        Image circle = new Image("images/circle.png",B_WIDTH,B_HEIGHT,false,false);
        Image rectangle = new Image("images/rectangle.png",B_WIDTH,B_HEIGHT,false,false);
        Image fill = new Image("images/fill.png",B_WIDTH,B_HEIGHT,false,false);
        //buttons
        ToggleButton button1 = new ToggleButton("",new ImageView(arrow));
        ToggleButton button2 = new ToggleButton("",new ImageView(eraser));
        ToggleButton button3 = new ToggleButton("",new ImageView(line));
        ToggleButton button4 = new ToggleButton("",new ImageView(circle));
        ToggleButton button5 = new ToggleButton("",new ImageView(rectangle));
        ToggleButton button6 = new ToggleButton("",new ImageView(fill));
        tools = new ArrayList<ToggleButton>(Arrays.asList(button1,button2,button3,button4,button5,button6));
        //set position
        button1.setLayoutX(0);
        button1.setLayoutY(0);
        button1.setSelected(true);
        button2.setLayoutX(70);
        button2.setLayoutY(0);
        button3.setLayoutX(0);
        button3.setLayoutY(60);
        button4.setLayoutX(70);
        button4.setLayoutY(60);
        button5.setLayoutX(0);
        button5.setLayoutY(120);
        button6.setLayoutX(70);
        button6.setLayoutY(120);

        //set functions to button
        //set controllers
        //select
        button1.setOnMouseClicked(mouseEvent -> {
            unselect(tools);
            fillcolor.setDisable(false);
            button1.setSelected(true);
            model.selectshape();
        });

        //erase
        button2.setOnMouseClicked(mouseEvent -> {
            unselect(tools);
            fillcolor.setDisable(false);
            button2.setSelected(true);
            model.eraseshape();
        });

        //draw line
        button3.setOnMouseClicked(mouseEvent -> {
            unselect(tools);
            fillcolor.setDisable(true);
            button3.setSelected(true);
            model.drawline();
        });

        //draw circle
        button4.setOnMouseClicked(mouseEvent -> {
            unselect(tools);
            fillcolor.setDisable(false);
            button4.setSelected(true);
            model.drawcircle();
        });

        //draw rec
        button5.setOnMouseClicked(mouseEvent -> {
            unselect(tools);
            fillcolor.setDisable(false);
            button5.setSelected(true);
            model.drawrec();
        });

        //fill color
        button6.setOnMouseClicked(mouseEvent -> {
            unselect(tools);
            button6.setSelected(false);
            model.fillcolor();
        });


        //add buttons to GridPane
        this.getChildren().addAll(button1,button2,button3,button4,button5,button6);

        //color pickers
        linecolor = new ColorPicker();
        fillcolor = new ColorPicker();
        linecolor.setMaxWidth(70);
        linecolor.setValue(javafx.scene.paint.Color.BLACK);
        fillcolor.setMaxWidth(70);
        fillcolor.setValue(javafx.scene.paint.Color.WHITE);
        Label linel = new Label(" line color:");
        linel.setFont(Font.font("Marker Felt",15));
        Label filll = new Label("fill color:");
        filll.setFont(Font.font("Marker Felt",15));
        linel.setLayoutX(0);
        linel.setLayoutY(200);
        filll.setLayoutX(70);
        filll.setLayoutY(200);
        linecolor.setLayoutX(0);
        linecolor.setLayoutY(220);
        fillcolor.setLayoutX(70);
        fillcolor.setLayoutY(220);
        linecolor.setOnAction(new EventHandler() {
            public void handle(Event t) {
                model.setLinecolor(linecolor.getValue());
            }
        });
        fillcolor.setOnAction(new EventHandler() {
            public void handle(Event t) {
                model.setFillcolor(fillcolor.getValue());
            }
        });

        this.getChildren().addAll(linel,filll,linecolor,fillcolor);

        //line style
        Label linet = new Label(" line thickness:");
        linet.setFont(Font.font("Marker Felt",15));
        linet.setLayoutX(0);
        linet.setLayoutY(280);
        Label lines = new Label(" line style:");
        lines.setFont(Font.font("Marker Felt",15));
        lines.setLayoutX(0);
        lines.setLayoutY(370);
        Image thick1 = new Image("images/thick1.png",25,25,false,false);
        Image thick2 = new Image("images/thick2.png",25,25,false,false);
        Image thick3 = new Image("images/thick3.png",25,25,false,false);
        Image style1 = new Image("images/style1.png",25,25,false,false);
        Image style2 = new Image("images/style2.png",25,25,false,false);
        Image style3 = new Image("images/style3.png",25,25,false,false);
        ToggleButton t1 = new ToggleButton("",new ImageView(thick1));
        ToggleButton t2 = new ToggleButton("",new ImageView(thick2));
        ToggleButton t3 = new ToggleButton("",new ImageView(thick3));
        ToggleButton s1 = new ToggleButton("",new ImageView(style1));
        ToggleButton s2 = new ToggleButton("",new ImageView(style2));
        ToggleButton s3 = new ToggleButton("",new ImageView(style3));
        t1.setLayoutX(0);
        t1.setLayoutY(300);
        t1.setSelected(true);
        t2.setLayoutX(49);
        t2.setLayoutY(300);
        t3.setLayoutX(98);
        t3.setLayoutY(300);
        s1.setLayoutX(0);
        s1.setLayoutY(390);
        s1.setSelected(true);
        s2.setLayoutX(49);
        s2.setLayoutY(390);
        s3.setLayoutX(98);
        s3.setLayoutY(390);
        thicks = new ArrayList<>(Arrays.asList(t1, t2, t3));
        styles = new ArrayList<>(Arrays.asList(s1, s2, s3));
        this.getChildren().addAll(linet,lines,t1,t2,t3,s1,s2,s3);
        //set controllers
        //set thickness
        t1.setOnMouseClicked(mouseEvent -> {
            unselect(thicks);
            t1.setSelected(true);
            model.setThick(1);
        });
        t2.setOnMouseClicked(mouseEvent -> {
            unselect(thicks);
            t2.setSelected(true);
            model.setThick(2);
        });
        t3.setOnMouseClicked(mouseEvent -> {
            unselect(thicks);
            t3.setSelected(true);
            model.setThick(3);
        });
        //set styles
        s1.setOnMouseClicked(mouseEvent -> {
            unselect(styles);
            s1.setSelected(true);
            model.setStyle(1);
        });
        s2.setOnMouseClicked(mouseEvent -> {
            unselect(styles);
            s2.setSelected(true);
            model.setStyle(2);
        });
        s3.setOnMouseClicked(mouseEvent -> {
            unselect(styles);
            s3.setSelected(true);
            model.setStyle(3);
        });



        model.addView(this);
    }

    //array of buttons unselected
    public void unselect(ArrayList<ToggleButton> b){
        for(int i = 0; i < b.size(); i++){
            b.get(i).setSelected(false);
        }
    }
    //when notified by model, update the display
    public void updateView(){
        //update thick and style
        unselect(thicks);
        thicks.get(model.thick-1).setSelected(true);
        unselect(styles);
        styles.get(model.style-1).setSelected(true);
        if(model.fillenable&&!tools.get(2).isSelected()) fillcolor.setDisable(false);
        if(model.fillenable){
            fillcolor.setValue(model.fillcolor);
        } else {
            fillcolor.setDisable(true);
        }
        linecolor.setValue(model.linecolor);
    }

}