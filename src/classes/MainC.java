
package classes;

//My improts
import support.*;
import ui.*;
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
import java.util.function.Function;

import javax.crypto.AEADBadTagException;
import javax.swing.ButtonModel;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Line;

import java.util.*;
import java.awt.Dimension;
import java.io.File;

public class MainC extends Application implements EventHandler<ActionEvent>{

    public FileHandler fh;
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

    List<VBox> newClassSizeData;

    Button b_AddClass;
    Button b_Save;

    ArrayList<ArrayList<Integer>> mapper;

    int dimX;
    int dimY;
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

    private void FillAll(){
        this.LoadMap(this.dimX, this.dimY);
        int i=0;
        for (Class c : this.curClass.GetClasses()){
            int bgnX = (int)Math.round(c.GetPosX()+20);
            int bgnY = (int)Math.round(c.GetPosY()+40);
            int endX = bgnX + (int)Math.round(this.newClassSizeData.get(i).getWidth());
            int endY = bgnY + (int)Math.round(this.newClassSizeData.get(i).getHeight());
            this.FillMap(bgnX, bgnY, endX, endY, i+1);
            i++;
        }
    }

    public Integer GetMap(double posX,double posY){
        return this.mapper.get((int)Math.round(posX/5)).get((int)Math.round(posY/5));
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

    private Pane LoadStage(){
        this.dimX = 1000;
        this.dimY = 800;

        this.newClassSizeData = new ArrayList<VBox>();


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
        Line drawer = new Line();
        for (Class curClass : this.curClass.GetClasses()){
            ClassController ctrl = new ClassController(curClass,this);
            VBox newClass = ctrl.GetDrawn();
            
            newClass.setPrefWidth(curClass.GetGraphicWidth());
            newClass.setPrefHeight(curClass.GetGraphicHeigth());

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
                }
            });

            addBind.setOnMouseReleased(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    if(GetMap(mouseEvent.getSceneX(),mouseEvent.getSceneY())>0){
                        MainC.this.curClass.Bind_Add("new_bind",curClass,MainC.this.curClass.GetClasses().get(GetMap(mouseEvent.getSceneX(),mouseEvent.getSceneY())-1));
                        System.out.println("hit");
                    }
                    layout.getChildren().remove(drawer);
                    MainC.this.Refresh();
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
                    MainC.this.Refresh();
                }
            });


            newClass.setLayoutX(curClass.GetPosX()+20);
            newClass.setLayoutY(curClass.GetPosY()+40);

            newClass.getChildren().add(newButts);
            layout.getChildren().add(newClass);
            i++;

            this.newClassSizeData.add(newClass);
        }
        
        return layout;
    }

    public void Refresh(){
        Pane layout = this.LoadStage();
        Scene newScene = new Scene(layout,this.dimX,this.dimY);
        this.primaryStage.setScene(newScene);
        this.FillAll();
        Group addG = this.RefreshLines();
        layout.getChildren().add(addG);
        this.primaryStage.show();
    }

    public void SmallRefresh(){
        Scene newScene = this.UpdateFList();
        this.primaryStage.setScene(newScene);
        this.primaryStage.show();
    }

    private Side Oppose(Side x){
        if(x == Side.LEFT)
        return Side.RIGHT;
        else if(x==Side.RIGHT)
        return Side.LEFT;
        else if(x==Side.BOTTOM)
        return Side.TOP;
        else
        return Side.BOTTOM;
    }

    private Dimension GetConPoint(Side x, VBox conPoint){
        Dimension pair = new Dimension();
        if(x==Side.TOP){
            pair.setSize(conPoint.getLayoutX(),conPoint.getLayoutY());
        }
        else if(x==Side.BOTTOM){
            pair.setSize(conPoint.getLayoutX(),conPoint.getLayoutY()+conPoint.getHeight());
        }
        else if(x==Side.LEFT){
            pair.setSize(conPoint.getLayoutX(),conPoint.getLayoutY());
        }
        else{
            pair.setSize(conPoint.getLayoutX()+conPoint.getWidth(),conPoint.getLayoutY());
        }
        return pair;
    }


    public Group RefreshLines(){
        Group newGr = new Group();

        for(Bind b : this.curClass.GetBinds()){
            Class c1 = b.GetClasses().get(0);
            Class c2 = b.GetClasses().get(1);

            
            int c1_num = c1.GetId();
            int c2_num = c2.GetId();

            int c1_x = c1.GetPosX();
            int c1_y = c1.GetPosY();
            int c2_x = c2.GetPosX();
            int c2_y = c2.GetPosY();

            int len_x;
            int len_y;
            Side c1_p1;
            Side c1_p2;
            Side f;

            if(c1_x < c2_x){
                len_x = c2_x - c1_x;
                c1_p1 = Side.RIGHT;
            }else{
                len_x = c1_x - c2_x;
                c1_p1 = Side.LEFT;
            }
            if(c1_y < c2_y){
                len_y = c2_y - c1_y;
                c1_p2 = Side.TOP;
            }else{
                len_y = c1_y - c2_y;
                c1_p2=Side.BOTTOM;
            }

            if(len_x > len_y)
                f = c1_p1;
            else 
                f=c1_p2;

            Dimension fDim = this.GetConPoint(f, this.newClassSizeData.get(c1_num));
            Dimension tDim = this.GetConPoint(this.Oppose(f), this.newClassSizeData.get(c2_num));
            BindController ctrl = new BindController(fDim.getWidth(), fDim.getHeight(), tDim.getWidth(), tDim.getHeight(),f, b,this,this.curClass);
            Group newLine =  ctrl.GetDrawn();

            newGr.getChildren().add(newLine);
        }

        return newGr;
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
                this.SmallRefresh();
            }
            
            
            
            for(int i =0;i<this.loadClassButtons.size();i++){
                String file = this.loadClassButtons.get(i).getText();
                if(event.getSource()==this.loadClassButtons.get(i)){
                    this.loaded=true;
                    this.curClass = this.fh.Load(this.pathway, file);
                    this.Refresh();
                    break;
                }
                else if(event.getSource()==this.renameClasButtons.get(i)){
                    vPopper newPop = new vPopper(this,this.pathway,file);
                    
                    break;
                }
                else if(event.getSource()==this.deleteClassButtons.get(i)){
                    this.fh.Delete_F(this.pathway,file);
                    this.SmallRefresh();
                    break;
                }
            }
        }else{
            if(event.getSource()==this.b_AddClass){
                Class newC= this.curClass.Class_Add("new_clss");
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