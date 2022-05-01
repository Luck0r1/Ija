package ui;

import classes.SequenceDia;

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

    private Actor target;

    public SQ_FuncPopper(SequenceDia sd, Actor a){
        buttons = new ArrayList<Button>();
        this.sq=sd;
        this.target=a;

        VBox content = this.Chooser(a.GetClass());
        ScrollPane pane = new ScrollPane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(5*30);
        
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(pane);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    
    VBox Chooser(classes.Class c){
        List<CD_Element> funcList= c.GetFunca();
        VBox v = new VBox();
        int heightOfButt = 30;
        int widthOfButt = 200;
        v.setPrefHeight(funcList.size()*heightOfButt);
        for(CD_Element elem: funcList){
            Button b = new Button(c.GetName());
            b.setOnAction(this);
            b.setPrefWidth(widthOfButt);
            b.setPrefHeight(heightOfButt);
            this.buttons.add(b);
            v.getChildren().add(b);
        }
        return v;
        
    }
    @Override
    public void handle(ActionEvent event){
        for(Button b : this.buttons){
            
        }
        
    }
}