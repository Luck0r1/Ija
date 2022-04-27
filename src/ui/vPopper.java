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

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.crypto.AEADBadTagException;
import javax.swing.ButtonModel;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.*;

import javafx.geometry.Pos;

public class vPopper implements EventHandler<ActionEvent>{

    MainC backSide;

    Stage secondaryStage;

    TextField namer;

    Button b_submitter;

    String pather;
    String deprecatedStr;

    public vPopper(MainC backSide,String path,String oldF){
        this.backSide = backSide;
        this.pather = path;
        this.deprecatedStr = oldF;
        Scene toShow = popMaker();
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(toShow);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    private Scene popMaker(){

        VBox overlay = new VBox();
        overlay.setAlignment(Pos.CENTER);

        TextField namer = new TextField(this.deprecatedStr);
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
            this.backSide.fh.Rename_F(this.pather,this.deprecatedStr, this.pather+"/"+this.namer.getText());
            this.secondaryStage.close();
            this.backSide.SmallRefresh();
        }
    }

}