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

import java.io.File;

import javafx.geometry.Pos;

public class CPopper implements EventHandler<ActionEvent>{

    MainC backSide;

    ClassController toSave;

    Stage secondaryStage;

    TextField namer;

    Button b_submitter;

    Class edditedC;

    public CPopper(MainC backSide,ClassController calledByGrave,Class editedC){
        this.backSide = backSide;
        this.edditedC = editedC;
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

        TextField namer = new TextField(this.edditedC.GetName());
        this.namer = namer;
        overlay.getChildren().add(namer);

        Button batter = new Button("Submit");
        this.b_submitter = batter;
        batter.setOnAction(this);
        overlay.getChildren().add(batter);

        Scene popper = new Scene(overlay);
        return popper;

    }



    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_submitter){
            this.edditedC.SetName(this.namer.getText());
            this.secondaryStage.close();
            this.toSave.Refresh();
        }
    }

}