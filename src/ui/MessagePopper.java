package ui;
//My improts
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
import javax.swing.OverlayLayout;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Group; 

import java.util.*;

public class MessagePopper implements EventHandler<ActionEvent>{

    Button b_submiter;
    Button b_remove;
    TextField text_f;

    private Stage secondaryStage;
    private Message ms;
    private SequenceDiaInterface sq;
    private SequenceDia backer;

    public MessagePopper(Message m,SequenceDiaInterface backer,SequenceDia seq){
        this.sq = backer;
        this.ms = m;
        this.backer = seq;

        Pane p = this.GetViz(); // and boomstick

        this.secondaryStage = new Stage();
        Scene layout = new Scene(p);
        this.secondaryStage.setScene(layout);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    private Pane GetViz(){
        Pane p = new Pane();
        p.setPrefWidth(200);
        p.setPrefHeight(80);

        TextField tf = new TextField(this.ms.GetText());
        tf.setPrefWidth(180);
        tf.setPrefHeight(30);
        tf.setLayoutY(10);
        tf.setLayoutX(10);
        this.text_f = tf;
        HBox hb = new HBox();
        hb.setLayoutY(45);
        hb.setLayoutX(25);
        Button bs = new Button("Submit");
        bs.setPrefWidth(75);
        bs.setPrefHeight(25);
        Button br = new Button("Remove");
        br.setPrefWidth(75);
        br.setPrefHeight(25);
        bs.setOnAction(this);
        br.setOnAction(this);
        this.b_submiter = bs;
        this.b_remove = br;
        hb.getChildren().addAll(bs,br);
        p.getChildren().addAll(tf,hb);
        return p;
    }

    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_remove){
            this.backer.RemoveMessage(this.ms);
            this.sq.Refresh();
            this.secondaryStage.close();
        }else
        if(event.getSource()==this.b_submiter){
            this.ms.SetText(this.text_f.getText());
            this.sq.Refresh();
            this.secondaryStage.close();
        }
    }
}