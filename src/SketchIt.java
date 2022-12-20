import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

import java.io.File;
import java.util.ArrayList;


public class SketchIt extends Application{
    static double WIDTH = 640;
    static double HEIGHT = 480;
    static double currentWIDTH = 640;
    static double currentHEIGHT = 480;
    Model model;
    ToolbarView toolbar;
    CanvasView canvas;
    @Override
    public void start(Stage stage){
        Pane pane = new Pane();
        //menus
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        MenuItem UnDo = new MenuItem("Undo");
        MenuItem fileNew = new MenuItem("New");
        Menu fileLoad= new Menu("Load");
        MenuItem fileSave = new MenuItem("Save");
        MenuItem fileQuit = new MenuItem("Quit");
        fileQuit.setOnAction(e->{stage.close();});
        fileMenu.getItems().addAll(fileNew, fileLoad, fileSave, fileQuit);
        editMenu.getItems().add(UnDo);

        UnDo.setOnAction(e->{
            model.Undo();
        });

        menubar.getMenus().addAll(fileMenu,editMenu);
        menubar.setMinWidth(WIDTH);
        menubar.setMaxHeight(25);
        menubar.setMinHeight(25);
        pane.getChildren().add(menubar);

        //Save file
        fileSave.setOnAction(e->{
            String file_name;
            if(!model.ifnamed()) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(stage);
                file_name = file.getName();
            } else file_name = model.getname();
            try {
                FileOutputStream fileout = new FileOutputStream(file_name);
                ObjectOutputStream out = new ObjectOutputStream(fileout);
                out.writeObject(model.shapes);

                out.close();
                fileout.close();
                model.setname(file_name);
                model.savemodel();
                fileLoad.getItems().add(new MenuItem(file_name));
            }
            catch(IOException ex)
            {
                System.out.println("IOException is caught when save");
                System.out.println(ex.toString());
            }
        });
        //Load file
        fileLoad.setOnAction(e->{
            for(MenuItem item : fileLoad.getItems()){
                item.setOnAction(e2->{
                    String filename = item.getText();
                    try {
                        FileInputStream filein = new FileInputStream(filename);
                        ObjectInputStream in = new ObjectInputStream(filein);
                        ArrayList<IShape> shapes = new ArrayList<>();
                        shapes = (ArrayList<IShape>) in.readObject();
                        if(!model.ifsaved()){
                            String file_name;
                            if(model.ifnamed()) file_name = model.getname();
                            else {
                                FileChooser fileChooser = new FileChooser();
                                File file = fileChooser.showSaveDialog(stage);
                                file_name = file.getName();
                            }
                            try {
                                FileOutputStream fileout = new FileOutputStream(file_name);
                                ObjectOutputStream out = new ObjectOutputStream(fileout);
                                out.writeObject(model.shapes);
                                out.close();
                                fileout.close();
                                if(!model.ifnamed()) {
                                    model.setname(file_name);
                                    fileLoad.getItems().add(new MenuItem(file_name));
                                }
                            }
                            catch(IOException ex)
                            {
                                System.out.println("IOException is caught");
                            }
                        }
                        model.clear();
                        model.setShapes(shapes);
                        model.setname(filename);
                        model.resize(stage.getWidth(),stage.getHeight());
                    }
                    catch(IOException | ClassNotFoundException ex2)
                    {
                        System.out.println("IOException is caught when load");
                    }
                });
            }
        });
        //New file
        fileNew.setOnAction(e->{
            if(!model.ifsaved()){
                String file_name;
                if(model.ifnamed()) file_name = model.getname();
                else {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showSaveDialog(stage);
                    file_name = file.getName();
                }
                try {
                    FileOutputStream fileout = new FileOutputStream(file_name);
                    ObjectOutputStream out = new ObjectOutputStream(fileout);
                    out.writeObject(model.shapes);
                    out.close();
                    fileout.close();
                    if(!model.ifnamed()) {
                        model.setname(file_name);
                        fileLoad.getItems().add(new MenuItem(file_name));
                    }
                }
                catch(IOException ex)
                {
                    System.out.println("IOException is caught when save");
                }
            }
            model.clear();
            //   model = new Model();
        });


        //Model
        model = new Model();
        //Views
        toolbar = new ToolbarView(model);
        canvas = new CanvasView(model);
        toolbar.setLayoutX(0);
        toolbar.setLayoutY(25);
        canvas.setLayoutX(140);
        canvas.setLayoutY(25);
        Line divide = new Line(139,26,140,HEIGHT);
        divide.setStrokeWidth(2);
        divide.setStroke(Color.GRAY);
        pane.getChildren().addAll(toolbar,canvas,divide);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.BACK_SPACE) {
                model.delete();
            }
            if(key.getCode()== KeyCode.ESCAPE) {
                model.deselect();
            }
        });
        pane.setMinWidth(WIDTH);
        pane.setMinHeight(HEIGHT);
        stage.setScene(scene);
        stage.setTitle("SketchIt");
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.setMaxWidth(1600);
        stage.setMaxHeight(1200);
        //when stage is resized
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            currentWIDTH = stage.getWidth();
            currentHEIGHT = stage.getHeight();
            pane.setMinWidth(stage.getWidth());
            pane.setMinHeight(stage.getHeight());
            menubar.setMinWidth(stage.getWidth());
            divide.setEndY(stage.getHeight());
            canvas.resizeCanvas(stage.getWidth(), stage.getHeight());
        };

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
        stage.show();
    }
}