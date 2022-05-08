package ui;

//My improts
import ui.*;
import classes.*;
//FX imports
//Roots
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

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

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.*;


import javafx.geometry.Pos;

/**
 * Creates popup window for renaming sequence diagrams
 * @author xmacej03
 */
public class SQDRenamePopper implements EventHandler<ActionEvent>{

    private MainC backSide;

    private Stage secondaryStage;

    private TextField namer;

    private Button b_submitter;

    private SequenceDia sq;

    public SQDRenamePopper(MainC backSide,SequenceDia sq){
        this.backSide = backSide;
        this.sq = sq;
        Scene toShow = popMaker();
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(toShow);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    /**
     * Creates graphical component
     * @return
     */
    private Scene popMaker(){

        VBox overlay = new VBox();
        overlay.setAlignment(Pos.CENTER);

        TextField namer = new TextField(this.sq.GetName());
        this.namer = namer;
        overlay.getChildren().add(namer);

        Button batter = new Button("Submit");
        this.b_submitter = batter;
        batter.setOnAction(this);
        overlay.getChildren().add(batter);

        Scene popper = new Scene(overlay);
        return popper;

    }

    /**
     * Handles buttons
     * @param event
     */
    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_submitter){
            this.sq.SequenceDia_Rename(this.namer.getText());
            this.secondaryStage.close();
            this.backSide.Refresh();
        }
    }

}