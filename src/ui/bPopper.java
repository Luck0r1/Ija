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

import java.io.File;

import javafx.geometry.Pos;

public class bPopper implements EventHandler<ActionEvent>{

    MainC backSide;

    BindController toSave;

    Stage secondaryStage;

    TextField namer;
    TextField c1;
    TextField c2;

    Button b_submitter;
    Button b_deleter;
    Bind editedBind;

    public bPopper(MainC backSide,BindController calledByGrave,Bind editedB){
        this.backSide = backSide;
        this.editedBind = editedB;
        this.toSave = calledByGrave;
        Scene toShow = popMaker();
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(toShow);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    private Scene popMaker(){

        VBox overlay = new VBox();
        overlay.setAlignment(Pos.CENTER);

        HBox topper = new HBox();
        TextField namer = new TextField(this.editedBind.GetName());
        this.namer = namer;
        TextField c1 = new TextField(this.editedBind.Get_C1());
        this.c1 = c1;
        TextField c2 = new TextField(this.editedBind.Get_C2());
        this.c2 = c2;
        Label tabber1 = new Label("    ");
        Label tabber2 = new Label("    ");
        topper.getChildren().addAll(c1,tabber1,namer,tabber2,c2);

        overlay.getChildren().add(topper);

        Label lab = new Label("-------------------------");
        overlay.getChildren().add(lab);

        Button batter = new Button("Submit");
        this.b_submitter = batter;
        batter.setOnAction(this);
        overlay.getChildren().add(batter);

        Button newDel = new Button("Remove");
        this.b_deleter = newDel;
        newDel.setOnAction(this);
        overlay.getChildren().add(newDel);

        Scene popper = new Scene(overlay);
        return popper;

    }



    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_submitter){
            this.editedBind.SetName(this.namer.getText());
            this.editedBind.Set_C1(this.c1.getText());
            this.editedBind.Set_C2(this.c2.getText());
            this.secondaryStage.close();
            this.toSave.Refresh();
        }

        if(event.getSource()==this.b_deleter){
            this.toSave.SendToTheRaveYard();
            this.secondaryStage.close();
            this.toSave.Refresh();
        }
    }

}