
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
import java.awt.Toolkit;
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

    //Side menu
    Button sb_addDia;
    List<Button> sb_loadDia;
    List<Button> sb_renameDia;
    List<Button> sb_removeDia;

    Button b_AddClass;
    Button b_Save;

    Button b_debug;
    boolean in_debug=false;

    Mapper mapper;

    int dimX=1920;
    int dimY=1080;

    ProgramState pState = ProgramState.SMALLMENU;

    public void FireStarter(String[] args) {
        launch(args);
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
    

    private VBox GetSideMenu(){
        VBox v = new VBox();
        v.setPrefWidth(300);
        v.setPrefHeight(this.dimY);
        v.setStyle("-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;"
                            + "-fx-border-color: blue;"
                            + "-fx-background-color: grey;");
        //v.setBackground(new Background(new BackgroundFill(java.awt.Color.darkGray,CornerRadii.EMPTY,Insets.EMPTY)));

        Button addNewDiagram = new Button("Add new SubDia");
        addNewDiagram.setOnAction(this);
        this.sb_addDia = addNewDiagram;
        addNewDiagram.setPrefWidth(300);
        v.getChildren().add(addNewDiagram);

        for(SequenceDia sd : this.curClass.GetSeqD()){
            
            HBox oneSd = new HBox();

            Button loadDia = new Button(sd.GetName());
            Button renameDia = new Button("Rename");
            Button deleteDia = new Button("Delete");

            loadDia.setOnAction(this);
            renameDia.setOnAction(this);
            deleteDia.setOnAction(this);

            this.sb_loadDia.add(loadDia);
            this.sb_removeDia.add(deleteDia);
            this.sb_renameDia.add(renameDia);

            loadDia.setPrefWidth(200);
            renameDia.setPrefWidth(50);
            deleteDia.setPrefWidth(50);

            oneSd.getChildren().addAll(loadDia,renameDia,deleteDia);
            v.getChildren().add(oneSd);
        }
        return v;
    }

    private Pane LoadStage(){

        Pane layout = new Pane();

        HBox buttonen = new HBox();

        VBox sideM = GetSideMenu();
        layout.getChildren().add(sideM);

        Button addC_butt = new Button("Add Class");
        addC_butt.setOnAction(this);
        Button save_butt = new Button("Save");
        save_butt.setOnAction(this);
        this.b_AddClass = addC_butt;
        this.b_Save = save_butt;
        buttonen.getChildren().add(addC_butt);
        buttonen.getChildren().add(save_butt);
        buttonen.setLayoutX(300);
        
        /*Debugg purposes*/
        Button debugMode = new Button("Debug");
        debugMode.setOnAction(this);
        this.b_debug=debugMode;
        buttonen.getChildren().add(debugMode);
    
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

            /* Line drag functions Bgn*/
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
                    if(mapper.GetMap(mouseEvent.getSceneX(),mouseEvent.getSceneY())>0){
                        MainC.this.curClass.Bind_Add("new_bind",curClass,MainC.this.curClass.GetClasses().get(mapper.GetMap(mouseEvent.getSceneX(),mouseEvent.getSceneY())-1));
                        System.out.println("hit");
                    }
                    layout.getChildren().remove(drawer);
                    MainC.this.Refresh();
                }
            });
            /*Line drag functions End */            

            newButts.getChildren().addAll(move,addBind);

            final Delta dragDelta = new Delta();

            move.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  // record a delta distance for the drag and drop operation.
                  mapper.UnfilSpaceForClass(curClass);
                  dragDelta.x = newClass.getLayoutX() - mouseEvent.getSceneX();
                  dragDelta.y = newClass.getLayoutY() - mouseEvent.getSceneY();
                }
            });

            move.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    Dimension h = mapper.FindClosestPlace(curClass, mouseEvent.getSceneX() + dragDelta.x, mouseEvent.getSceneY() + dragDelta.y,dimX);
                    newClass.setLayoutX(Converts.ToWorldCoordinatesX((int)h.getWidth()));
                    newClass.setLayoutY(Converts.ToWorldCoordinatesY((int)h.getHeight()));
                }
            });

            move.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    curClass.SetPos(Converts.ToMapCoordinatesX(newClass.getLayoutX()), Converts.ToMapCoordinatesY(newClass.getLayoutY()));
                    newClass.setLayoutX(Converts.ToWorldCoordinatesX(curClass.GetPosX()));
                    newClass.setLayoutY(Converts.ToWorldCoordinatesY(curClass.GetPosY()));
                    MainC.this.Refresh();
                }
            });


            newClass.setLayoutX(Converts.ToWorldCoordinatesX(curClass.GetPosX()));
            newClass.setLayoutY(Converts.ToWorldCoordinatesY(curClass.GetPosY()));

            newClass.getChildren().add(newButts);
            layout.getChildren().add(newClass);
            i++;
        }
        
        return layout;
    }

    public void Refresh(){
        Pane layout = this.LoadStage();
        Scene newScene = new Scene(layout,this.dimX,this.dimY);
        this.primaryStage.setScene(newScene);
        this.mapper=new Mapper(this.dimX, this.dimY, this.curClass);
        Group addG = this.RefreshLines();
        layout.getChildren().add(addG);
        this.primaryStage.setMaximized(true);
        this.primaryStage.show();
    }

    public void DebugRefresh(){
        Pane layout = this.mapper.DebugMap();
        Button debugMode = new Button("Debug");
        debugMode.setOnAction(this);
        this.b_debug=debugMode;
        layout.getChildren().add(debugMode);
        Scene newScene = new Scene(layout,this.dimX,this.dimY);
        this.primaryStage.setScene(newScene);
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

    private Dimension GetConPoint(Side x, classes.Class c){
        Dimension pair = new Dimension();
        if(x==Side.TOP){
            pair.setSize(c.GetPosX(),c.GetPosY());
        }   
        else if(x==Side.BOTTOM){
            pair.setSize(c.GetPosX(),c.GetPosY()+c.GetGraphicHeigth());
        }
        else if(x==Side.LEFT){
            pair.setSize(c.GetPosX(),c.GetPosY());
        }
        else{
            pair.setSize(c.GetPosX()+c.GetGraphicWidth(),c.GetPosY());
        }
        return pair;
    }

    private Group DrawLines(){
        return null;
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

            Dimension fDim = this.GetConPoint(f, c1);
            Dimension tDim = this.GetConPoint(this.Oppose(f), c2);
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

        //Dimension screenSize = Toolkit. getDefaultToolkit(). getScreenSize();
        //System.out.println((int)screenSize.getWidth());
        //System.out.println((int)screenSize.getHeight());

        Scene showScene = this.UpdateFList();
        
        primaryStage.setScene(showScene);
        primaryStage.show();

        this.primaryStage=primaryStage;
    }

    @Override
    public void handle(ActionEvent event){

        if(this.pState==ProgramState.SMALLMENU){

            if(event.getSource() == this.addClassDiagram){
                this.fh.New(this.pathway);
                this.SmallRefresh();
            }
            
            
            
            for(int i =0;i<this.loadClassButtons.size();i++){
                String file = this.loadClassButtons.get(i).getText();
                if(event.getSource()==this.loadClassButtons.get(i)){
                    this.pState=ProgramState.MAINER;
                    this.curClass = this.fh.Load(this.pathway, file);
                    this.mapper=new Mapper(this.dimX, this.dimY,curClass);
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
        }
        else
        if(this.pState==ProgramState.MAINER){
            if(event.getSource()==this.b_AddClass){
                Class newC= this.curClass.Class_Add("new_clss");
                Dimension h = this.mapper.FindClosestPlace(newC, 0, 0,this.dimX);
                newC.SetPos((int)h.getWidth(), (int)h.getHeight());
                this.Refresh();
                return;
            }
            else if(event.getSource()==this.b_Save){
                this.fh.Save(this.pathway,this.curClass);
                return;
            }
            else if(event.getSource()==this.b_debug){
                if(this.in_debug){
                    this.Refresh();
                }else{
                    this.DebugRefresh();
                }
                this.in_debug= !this.in_debug;
                return;
            }
            else if(event.getSource()==this.sb_addDia){

                return;
            }

            for(int i=0;i<this.sb_loadDia.size();i++){
                if(event.getSource()==this.sb_loadDia.get(i)){

                    return;
                }
            }
            for(int i=0;i<this.sb_renameDia.size();i++){
                if(event.getSource()==this.sb_renameDia.get(i)){

                    return;
                }
            }
            for(int i=0;i<this.sb_removeDia.size();i++){
                if(event.getSource()==this.sb_removeDia.get(i)){

                    return;
                }
            }
        }
    }
    
}

enum ProgramState{
    SMALLMENU,
    MAINER,
    SIDEDIA
}