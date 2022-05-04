package ui;

import classes.*;


//Buttons
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.scene.layout.Pane;
import java.util.*;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;
import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import classes.Actor;
import classes.ClassDiagram;
import classes.SequenceDia;

//Buttons
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Line;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group; 

public class SequenceDiaInterface implements EventHandler<ActionEvent>{
    private SequenceDia sq;
    private ClassDiagram workingDia;
    private Group gui;
    private int dimX;
    private int dimY;
    public MainC responder;
    private Button b_return;
    private Button add_Actor;
    private List<Button> remove_butts;
    private List<Button> sendMesbutts;
    List<Pane> highlighPanes;
 
    public SequenceDiaInterface(MainC responder,ClassDiagram cd,SequenceDia seq,int dimX,int dimY){
        this.sq = seq;
        this.workingDia = cd;
        this.responder = responder;
        this.dimX=dimX;
        this.dimY=dimY;
    }

    private Group GenerateInActive(){
        Line l = new Line();
        l.setStartX(2.5);
        l.setEndX(2.5);
        l.setEndY(20);
        Group g = new Group();
        g.getChildren().add(l);
        return g;
    }

    private Group GenerateActive(ChunkType type){
        Group g = new Group();
        Pane p = new Pane();
        p.setPrefWidth(5);
        if(type == ChunkType.FULL)
            p.setPrefHeight(20);
        else
        if(type == ChunkType.BEGIN){
            Line l = new Line();
            l.setStartX(2.5);
            l.setEndX(2.5);
            l.setEndY(10);
            g.getChildren().add(l);
            p.setPrefHeight(10);
            p.setLayoutY(10);
        }else{
            Line l = new Line();
            l.setStartX(2.5);
            l.setEndX(2.5);
            l.setEndY(20);
            l.setStartY(10);
            g.getChildren().add(l);
            p.setPrefHeight(10);
        }
        p.setStyle("-fx-border-style: solid inside;"
        + "-fx-border-width: 2;"
        + "-fx-border-color: #bdbdbd;"
        + "-fx-background-color: grey;");
        g.getChildren().add(p);
        return g;
    }

    private Group GenerateChunk(ChunkType type){
        if(type == ChunkType.EMPTY){
            return GenerateInActive();
        }else{
            return GenerateActive(type);
        }
    }

    private Group GenerateArrow(int startX,int startY,int endX,int endY, boolean dotted){
        boolean dir_reverse = (startX<endX);
        Line l = new Line();
        l.setStartX(startX);
        l.setStartY(startY);
        l.setEndX(endX);
        l.setEndY(endY);
        Line ul = new Line();
        ul.setStartX(endX);
        ul.setStartY(endY);
        ul.setEndY(endY-10);
        Line dl = new Line();
        dl.setStartX(endX);
        dl.setStartY(endY);
        dl.setEndY(endY+10);
        if(dotted){
            l.getStrokeDashArray().addAll(25d, 20d, 5d, 20d);
        }
        if(!dir_reverse){
            ul.setEndX(endX+10);
            dl.setEndX(endX+10);
        }else{
            dl.setEndX(endX-10);
            ul.setEndX(endX-10);
        }
        Group g = new Group();
        g.getChildren().addAll(l,ul,dl);
        return g;
    }

    private Pane GetHead(String name,int headerHeigth,int slotWidth,int collumn, int offSet){
        Pane bckg = new Pane();
        bckg.setPrefWidth(slotWidth-40);
        bckg.setPrefHeight(headerHeigth);
        bckg.setLayoutX(20);
        bckg.setLayoutY(20 + offSet);
        bckg.setStyle("-fx-border-style: solid inside;"
        + "-fx-border-width: 2;"
        + "-fx-border-color: blue;"
        + "-fx-background-color: grey;");
        if(name==""){
            Button b = new Button("Add");
            b.setOnAction(this);
            this.add_Actor = b;
            b.setPrefHeight(headerHeigth -35);
            b.setPrefWidth(slotWidth-80);
            b.setLayoutX(20);
            b.setLayoutY(10);
            bckg.getChildren().add(b);
        }else{
            Label l = new Label(name);
            
            l.setPrefHeight(headerHeigth-35);
            l.setPrefWidth(slotWidth-60);
            l.setLayoutX(10);
            l.setLayoutY(5);
            l.setAlignment(Pos.CENTER);
            l.setStyle("-fx-border-style: solid inside;"
            + "-fx-border-width: 2;"
            + "-fx-border-color: blue;"
            + "-fx-background-color: #bdbdbd;");
            bckg.getChildren().add(l);
            
            if(collumn!=0){
                Button b = new Button("Remove");
                b.setOnAction(this);
                this.remove_butts.add(b);
                b.setPrefHeight(25);
                b.setPrefWidth((slotWidth-80)/2);
                b.setLayoutX(75);
                b.setLayoutY(headerHeigth-35+5);

                bckg.getChildren().add(b);
            }
        }
        return bckg;
    }

    private Group GetArrows(int headerHeigth,int actorWidth){
        Group returner = new Group();
        List<Message> msList = this.sq.GetMessages();
        List<Actor> actors = this.sq.GetCast();

        for (int i = 0;i<msList.size();i++){
            int yCoord = (i+1)*20+headerHeigth+30;
            Actor a1 = msList.get(i).GetA1();
            Actor a2 = msList.get(i).GetA2();
            int num1 = this.sq.GetCast().indexOf(a1)+1;
            int num2 = this.sq.GetCast().indexOf(a2)+1;
            int xBegin = (num1)*actorWidth + actorWidth/2;
            int xEnd = (num2)*actorWidth + actorWidth / 2;
            Group g = GenerateArrow(xBegin, yCoord, xEnd, yCoord, msList.get(i).GetResponse());
            returner.getChildren().add(g);
        }

        return returner;
    }

    private void DrawInterface(){
        this.remove_butts = new ArrayList<Button>();
        this.sendMesbutts = new ArrayList<Button>();
        this.highlighPanes = new ArrayList<Pane>();

        Group g = new Group();

        HBox topmenu=new HBox();
        topmenu.setPrefHeight(30);
        topmenu.setPrefWidth(1920);
        Button b = new Button("Return");
        b.setOnAction(this);
        b.setPrefHeight(30);
        topmenu.getChildren().add(b);
        this.b_return = b;

        ScrollPane mainScreen = new ScrollPane();
        mainScreen.setPrefWidth(this.dimX);
        mainScreen.setPrefHeight(this.dimY-30);
        mainScreen.setLayoutY(30);
        Group mainSDummy = new Group();

        int actorWidth = 300;
        int messageHeigth = 50;
        int headerHeigth = 70;

        List<Actor> cast = this.sq.GetCast();
        List<Message> messages = this.sq.GetMessages();

        int width = cast.size() * actorWidth;
        int heigth = messages.size() * messageHeigth-headerHeigth;

        Pane body = new Pane();

        for(int i = -1; i<=cast.size();i++){
            
            Pane bckg = new Pane();
            bckg.setPrefWidth(actorWidth);
            bckg.setPrefHeight(1050);
            bckg.setLayoutX((i+1)*actorWidth);
            Pane header;
            if(i==-1) header = GetHead("user", headerHeigth, actorWidth, i+1,0);
            else if(i==cast.size())header = GetHead("", headerHeigth, actorWidth, i+1,0);
            else header = GetHead(cast.get(i).GetName(), headerHeigth, actorWidth, i+1,0);
            bckg.getChildren().add(header);
            if(i!=cast.size()){

                Actor a;
                if(i==-1)a=null;
                else a=cast.get(i);
                List<Message> connection = this.sq.FindAssociatedMessages(a);
                boolean ac = false;

                for(int j=-1;j<messages.size();j++){
                    Group g1;
                    if(i==-1){
                        g1 = GenerateChunk(ChunkType.FULL);
                    }else{

                        if(a.IsEternal()){
                            g1 =GenerateChunk(ChunkType.FULL);
                        }else{
                            boolean hasMess;
                            if(j==-1)hasMess=false;
                            else hasMess = connection.contains(messages.get(j));
                            if(hasMess){
                                if(ac == false){
                                    g1 = GenerateChunk(ChunkType.BEGIN);
                                    ac=!ac;
                                }else{
                                    g1 = GenerateChunk(ChunkType.END);
                                    ac=!ac;
                                }
                            }else{
                                if(ac==false)
                                    g1=GenerateChunk(ChunkType.EMPTY);
                                else
                                    g1=GenerateChunk(ChunkType.FULL);
                            }
                        }


                    }
                    g1.setLayoutX(actorWidth/2 -2.5);
                    g1.setLayoutY((j+1)*20+headerHeigth+20);
                    bckg.getChildren().add(g1);
                }
            }
            this.highlighPanes.add(bckg);
            mainSDummy.getChildren().add(bckg);
        }

        for(int i = -1;i<cast.size();i++){
            Button bt = new Button("+");
            bt.setPrefHeight(20);
            bt.setOnAction(this);
            bt.setPrefWidth(20);
            bt.setLayoutX((i+1)*actorWidth + actorWidth/2 - 15);
            bt.setLayoutY((this.sq.GetMessages().size()+1)*20+headerHeigth+20);
            
            Line drawer = new Line();
            int helper = i;

            bt.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    g.getChildren().add(drawer);
                    drawer.setStartX(mouseEvent.getSceneX());
                    drawer.setStartY(mouseEvent.getSceneY());
                    drawer.setEndX(mouseEvent.getSceneX());
                    drawer.setEndY(mouseEvent.getSceneY());
                }
            });

            bt.setOnMouseDragged(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    drawer.setEndX(mouseEvent.getSceneX());
                    drawer.setEndY(mouseEvent.getSceneY());
                    for(int k = 0; k<=cast.size()+1;k++){
                        if(k!=(helper+1) && mouseEvent.getSceneX()>=k*actorWidth+20 && mouseEvent.getSceneX()<=k*actorWidth-20+actorWidth){
                            highlighPanes.get(k).setStyle("-fx-background-color: dbdbdb;");
                        }else{
                            highlighPanes.get(k).setStyle("-fx-background-color: none;");
                        }
                    }
                }
            });

            SequenceDiaInterface h2 = this;

            bt.setOnMouseReleased(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent mouseEvent) {
                    for(int k = 0; k<=cast.size();k++){
                        if(k!=(helper+1) && mouseEvent.getSceneX()>=k*actorWidth+20 && mouseEvent.getSceneX()<=k*actorWidth-20+actorWidth){
                            Actor source;
                            Actor target;
                            if(helper==-1)source=null;
                            else source = cast.get(helper);
                            if(k==0)target=null;
                            else target = cast.get(k-1);
                            SQ_FuncPopper popperMaster = new SQ_FuncPopper(sq,source,target,h2);
                        }
                    }
                    g.getChildren().remove(drawer);
                    responder.SequenceRefresh(sq);
                }
            });


            mainSDummy.getChildren().add(bt);
        }

        mainSDummy.getChildren().add(this.GetArrows(headerHeigth, actorWidth));

        mainScreen.setContent(mainSDummy);
        g.getChildren().addAll(mainScreen,topmenu);
        this.gui = g;
    }

    public Group GetDrawn(){
        this.DrawInterface();
        return this.gui;
    }

    public void AddActor(classes.Class c,boolean eternal){
        this.sq.AddActor(c,eternal);
        this.Refresh();
    }

    public void Refresh(){
        this.DrawInterface();
        this.responder.SequenceRefresh(sq);
    }

    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_return){
            this.responder.Refresh();
        }else
        if(event.getSource()==this.add_Actor){
            SQ_ClassPopper sq = new SQ_ClassPopper(this.workingDia, this,true);
        }else{
            int i = 0;
            for(Button b : this.remove_butts){
                if(event.getSource()==b){
                    this.sq.RemoveActorByActor(this.sq.GetCast().get(i));
                    this.responder.SequenceRefresh(this.sq);
                    break;
                }
                i++;
            }
        }
    }
}

enum ChunkType{
    EMPTY,
    FULL,
    BEGIN,
    END
}