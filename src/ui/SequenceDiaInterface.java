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

public class SequenceDiaInterface{
    private SequenceDia sq;
    private Group gui;
    private int dimX;
    private int dimY;
    
    private Button b_return;

    public SequenceDiaInterface(SequenceDia seq,int dimX,int dimY){
        this.sq = seq;
        this.dimX=dimX;
        this.dimY=dimY;
    }

    public void DrawInterface(){
        HBox topmenu=new HBox();
        topmenu.setPrefHeight(30);
        topmenu.setPrefWidth(1920);
        Button b = new Button("Return");
        b.setOnAction(this);
        b.setPrefHeight(30);
        topmenu.getChildren().add(b);

        ScrollPane mainScreen = new ScrollPane();
        mainScreen.setPrefWidth(this.dimX);
        mainScreen.setPrefHeight(this.dimY-30);

        int actorWidth = 400;
        int messageHeigth = 50;
        int headerHeigth = 100;

        List<Actor> cast = this.sq.GetCast();
        List<Message> messages = this.sq.GetMessages();

        int width = cast.size() * actorWidth;
        int heigth = messages.size() * messageHeigth-headerHeigth;

        Pane header = new Pane();
        Pane body = new Pane();
        
        

    }
}