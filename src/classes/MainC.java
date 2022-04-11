
package classes;

//My improts
import classes.*;
//FX imports
//Roots
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Buttons
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

//Layouts
import javafx.scene.layout.StackPane;
import javafx.scene.Group; 

//Labels
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.crypto.AEADBadTagException;
import javax.swing.ButtonModel;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Line;

import java.util.*;

import java.io.File;

public class MainC extends Application implements EventHandler<ActionEvent>{

    FileHandler fh;
    ClassDiagram curClass;

    Line drawer;

    Stage primaryStage;
    String pathway="./data";

    Group drawerG;

    //Small menu
    Button addClassDiagram;
    List<Button> loadClassButtons;
    List<Button> renameClasButtons;
    List<Button> deleteClassButtons;

    Button b_AddClass;
    Button b_Save;

    ArrayList<ArrayList<Integer>> mapper;


    boolean loaded=false;
    public void FireStarter(String[] args) {
        launch(args);
    }

    private void LoadMap(int x,int y){
        this.mapper = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<y/5;i++)
            this.mapper.add(new ArrayList<Integer>(Collections.nCopies(x/5, 0)));
    }

    private void FillMap(int strtX, int startY, int endX, int endY,int h){
        for(int i = strtX / 5; i<endX/5;i++){
            for (int j = startY/5; j<endY/5;j++){
                this.mapper.get(i).set(j, h);
            }
        }
    }

    /*Small menu functions*/
    private List<String> listFilesUsingJavaIO(String dir) {
        List<String> retNames = new ArrayList<String>();
        for (File phil : new File(dir).listFiles()){
            retNames.add(phil.getName());
        }
        return retNames;
    }

    private Scene UpdateFList(){
        this.loadClassButtons=new ArrayList<Button>();
        this.renameClasButtons=new ArrayList<Button>();
        this.deleteClassButtons= new ArrayList<Button>();

        VBox layout = new VBox();
        List<String> files = this.listFilesUsingJavaIO(this.pathway);
        for(String file : files){
            HBox hElem = new HBox();
            Button newButt = new Button(file);
            this.loadClassButtons.add(newButt);
            newButt.setOnAction(this);
            hElem.getChildren().add(newButt);

            Button rButt = new Button("Rename");
            rButt.setOnAction(this);
            hElem.getChildren().add(rButt);
            this.renameClasButtons.add(rButt);

            Button dButt = new Button("Delete");
            dButt.setOnAction(this);
            hElem.getChildren().add(dButt);
            this.deleteClassButtons.add(dButt);

            layout.getChildren().add(hElem);
        }

        Button addButt = new Button("Add class diagram");
        addButt.setOnAction(this);
        layout.getChildren().add(addButt); 
        this.addClassDiagram = addButt;

        Scene nScene = new Scene(layout,400,200);
        return nScene;
    }

    private Scene LoadStage(){
        int dimX = 1000;
        int dimY = 800;

        LoadMap(dimX,dimY);


        Pane layout = new Pane();

        HBox buttonen = new HBox();

        Button addC_butt = new Button("Add Class");
        addC_butt.setOnAction(this);
        Button save_butt = new Button("Save");
        save_butt.setOnAction(this);
        this.b_AddClass = addC_butt;
        this.b_Save = save_butt;
        buttonen.getChildren().add(addC_butt);
        buttonen.getChildren().add(save_butt);  
    
        layout.getChildren().add(buttonen);

        int i = 0;
        int j = 0;
        Line drawer = new Line();
        for (Class curClass : this.curClass.GetClasses()){
            ClassController ctrl = new ClassController(curClass,this);
            VBox newClass = ctrl.GetDrawn();
            
            HBox newButts = new HBox();
            Button move = new Button("Move");
            Button addBind = new Button("Add Bind");

            addBind.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    layout.getChildren().add(drawer);
                    drawer.setStartX(mouseEvent.getSceneX());
                    drawer.setStartY(mouseEvent.getSceneY());
                    drawer.setEndX(mouseEvent.getSceneX());
                    drawer.setEndY(mouseEvent.getSceneY());
                }
            });

            addBind.setOnMouseDragged(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    drawer.setEndX(mouseEvent.getSceneX());
                    drawer.setEndY(mouseEvent.getSceneY());
                    //ADD TARGET HERE
                }
            });

            

            addBind.setOnMouseReleased(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    layout.getChildren().remove(drawer);
                }
            });
            

            newButts.getChildren().addAll(move,addBind);

            final Delta dragDelta = new Delta();
            move.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  // record a delta distance for the drag and drop operation.
                  dragDelta.x = newClass.getLayoutX() - mouseEvent.getSceneX();
                  dragDelta.y = newClass.getLayoutY() - mouseEvent.getSceneY();
                }
            });

            move.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  newClass.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                  newClass.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                }
            });

            move.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    curClass.SetPos((int)Math.round(newClass.getLayoutX())-20, (int)Math.round(newClass.getLayoutY())-40);
                    newClass.setLayoutX(curClass.GetPosX()+20);
                    newClass.setLayoutY(curClass.GetPosY()+40);
                }
            });

            newClass.setLayoutX(curClass.GetPosX()+20);
            newClass.setLayoutY(curClass.GetPosY()+40);
            this.FillMap((int)Math.round( newClass.getLayoutX()),(int)Math.round(  newClass.getLayoutY()),(int)Math.round(  newClass.getLayoutX() + 30),(int)Math.round(  newClass.getLayoutX() + 60),i+1);

            newClass.getChildren().add(newButts);
            layout.getChildren().add(newClass);
        }
        
        Scene newScene = new Scene(layout,dimX,dimY);
        
        return newScene;
    }

    public void Refresh(){
        Scene newscene = this.LoadStage();
        this.primaryStage.setScene(newscene);
        this.primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{        
        primaryStage.setTitle("Dia Builder");
        this.fh = new FileHandler();

        Scene showScene = this.UpdateFList();
        
        primaryStage.setScene(showScene);
        primaryStage.show();

        this.primaryStage=primaryStage;
    }

    @Override
    public void handle(ActionEvent event){

        if(!this.loaded){

            if(event.getSource() == this.addClassDiagram){
                this.fh.New(this.pathway);
                Scene newScene = this.UpdateFList();
                this.primaryStage.setScene(newScene);
                this.primaryStage.show();
            }
            
            
            
            for(int i =0;i<this.loadClassButtons.size();i++){
                String file = this.loadClassButtons.get(i).getText();
                if(event.getSource()==this.loadClassButtons.get(i)){
                    this.loaded=true;
                    this.curClass = this.fh.Load(this.pathway, file);
                    this.primaryStage.setScene(this.LoadStage());
                    this.primaryStage.show();
                    break;
                }
                else if(event.getSource()==this.renameClasButtons.get(i)){
                    this.fh.Rename_F(this.pathway,file, pathway+"/"+file);
                    Scene newScene = this.UpdateFList();
                    this.primaryStage.setScene(newScene);
                    this.primaryStage.show();
                    break;
                }
                else if(event.getSource()==this.deleteClassButtons.get(i)){
                    this.fh.Delete_F(this.pathway,file);
                    Scene newScene = this.UpdateFList();
                    this.primaryStage.setScene(newScene);
                    this.primaryStage.show();
                    break;
                }
            }
        }else{
            if(event.getSource()==this.b_AddClass){
                this.curClass.Class_Add("new_clss");
                this.Refresh();
                return;
            }
            else if(event.getSource()==this.b_Save){
                this.fh.Save(this.pathway,this.curClass);
                return;
            }
        }
    }
    
}

class Delta { double x, y; }