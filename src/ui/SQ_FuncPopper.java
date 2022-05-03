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

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.*;

import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;

import javafx.scene.layout.VBox;
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

import javafx.scene.layout.Pane;

public class SQ_FuncPopper implements EventHandler<ActionEvent>{

    
    private List<Button> buttons;

    private Stage secondaryStage;

    private SequenceDia sq;

    private Actor source;
    private Actor target;

    private SequenceDiaInterface responder;

    public SQ_FuncPopper(SequenceDia sd, Actor source,Actor tar,SequenceDiaInterface responder){
        buttons = new ArrayList<Button>();
        this.sq=sd;
        this.target=tar;
        this.source = source;
        this.responder = responder;

        VBox content = this.Chooser(tar);
        ScrollPane pane = new ScrollPane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(5*30);
        pane.setContent(content);

        Scene layout = new Scene(pane);
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(layout);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    
    VBox Chooser(Actor target){
        List<CD_Element> funcList;
        if(target != null){
            funcList= target.GetClass().GetFunca();
        }else{
            funcList = new ArrayList<CD_Element>();
        }
        VBox v = new VBox();
        int heightOfButt = 30;
        int widthOfButt = 200;
        v.setPrefHeight(funcList.size()*heightOfButt);
        for(CD_Element elem: funcList){
            Button b = new Button(target.GetClass().GetName());
            b.setOnAction(this);
            b.setPrefWidth(widthOfButt);
            b.setPrefHeight(heightOfButt);
            this.buttons.add(b);
            v.getChildren().add(b);
        }
        Button b = new Button("Acknowledgement"); 
        b.setOnAction(this);
        b.setPrefWidth(widthOfButt);
        b.setPrefHeight(heightOfButt);
        this.buttons.add(b);
        v.getChildren().add(b);
        return v;
        
    }
    @Override
    public void handle(ActionEvent event){
        for(int i = 0;i<this.buttons.size();i++){
            if(event.getSource()==this.buttons.get(i)){
                if(i==this.buttons.size()-1){
                    this.sq.AddMessage(true, null, this.source, this.target);
                }else{
                    this.sq.AddMessage(false, this.target.GetClass().GetFunca().get(i), this.source, this.target);
                }
                this.responder.Refresh();
                this.secondaryStage.close();
                break;
            }
        }
        
    }
}