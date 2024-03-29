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

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.*;

import support.*;
import java.io.File;

import javafx.geometry.Pos;

/**
 * This class handles popup window for element editing
 * @author xlukac16
 */
public class ElemPopper implements EventHandler<ActionEvent>{

    private MainC backSide;

    private CD_Element toEdit;
    private ClassController toSave;

    private Stage secondaryStage;

    private Button b_isArg;
    private Button b_isFunc;
    private Button b_isPType;
    private Button b_isLType;
    private Button b_isKType;

    private Button b_submitter;

    private classes.Class edditedC;

    private TextField namer;
    private TextField typer;
    private TextField rTyper;

    private String voider;

    public ElemPopper(MainC backSide,ClassController calledByGrave,classes.Class editedC, CD_Element reCaller){
        this.backSide = backSide;
        this.edditedC = editedC;
        this.toSave = calledByGrave;
        this.toEdit = reCaller;
        Scene toShow = popMaker();
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(toShow);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    /**
     * Creates popup window for element editting
     * @return
     */
    private Scene popMaker(){

        VBox overlay = new VBox();
        overlay.setAlignment(Pos.CENTER);

        HBox oTyper = new HBox();
        oTyper.setAlignment(Pos.CENTER);

        Button b_arg = new Button("Argument");
        Button b_func = new Button("Function");
        b_arg.setOnAction(this);
        b_func.setOnAction(this);
        this.b_isArg = b_arg;
        this.b_isFunc = b_func;
        oTyper.getChildren().addAll(b_arg);
        oTyper.getChildren().add(b_func);
        overlay.getChildren().add(oTyper);

        HBox editor = new HBox();
        editor.setAlignment(Pos.CENTER);

        VBox typer = new VBox();
        typer.setAlignment(Pos.CENTER);
        Button pType = new Button("+");
        pType.setOnAction(this);
        this.b_isPType = pType;
        Button lType = new Button("-");
        lType.setOnAction(this);
        this.b_isLType = lType;
        Button kType = new Button(" ");
        kType.setOnAction(this);
        this.b_isKType = kType;
        typer.getChildren().add(pType);
        typer.getChildren().add(lType);
        typer.getChildren().add(kType);
        editor.getChildren().add(typer);
        TextField namer = new TextField(this.toEdit.GetName());
        this.namer = namer;

        editor.getChildren().add(namer);

        HBox adder = new HBox();
        adder.setAlignment(Pos.CENTER);
        if(this.toEdit.IsFunc()){
            Label lC = new Label("(");
            Label rC = new Label("):");
            TextField texter = new TextField(this.toEdit.GetType());
            TextField texter2 = new TextField(this.toEdit.GetReturnT());
            this.rTyper = texter2;
            this.typer = texter;
            adder.getChildren().addAll(lC,texter,rC,texter2);
        }else{
            Label lc = new Label(":");
            TextField texterNumeroDuo = new TextField(this.toEdit.GetType());
            this.typer = texterNumeroDuo;
            texterNumeroDuo.setOnAction(this);
            adder.getChildren().add(lc);
            adder.getChildren().add(texterNumeroDuo);           
        }

        editor.getChildren().add(adder);
        overlay.getChildren().add(editor);

        this.voider = this.toEdit.ToString();
        Label preview = new Label(this.voider);

        Button closer = new Button("Close");
        this.b_submitter = closer;
        closer.setOnAction(this);

        overlay.getChildren().add(preview);
        overlay.getChildren().add(closer);

        Scene popper = new Scene(overlay);
        return popper;

    }

    /**
     * Set input fields to elemnt
     */
    public void SetFields(){
        this.toEdit.ReName(this.namer.getText());
        this.toEdit.ReType(this.typer.getText());
        if(this.rTyper!=null)
        this.toEdit.SetReturnT(this.rTyper.getText());
    }

    /**
     * Handle buutps
     * @param event
     */
    @Override
    public void handle(ActionEvent event){
        this.SetFields();

        if(event.getSource()==this.b_isArg){
            if(this.toEdit.IsFunc()){
                this.edditedC.remFunction(this.toEdit);
                this.toEdit.SetFunc(false);
                this.edditedC.forceAdd(this.toEdit);
                Scene toShow = this.popMaker();
                this.secondaryStage.setScene(toShow);
            }
        }
        else if (event.getSource()==this.b_isFunc){
            if(this.toEdit.IsFunc()==false){
                this.edditedC.remElement(this.toEdit);
                this.toEdit.SetFunc(true);
                this.edditedC.forceAdd(this.toEdit);
                Scene toShow = this.popMaker();
                this.secondaryStage.setScene(toShow);
            }
        }
        else if(event.getSource()==this.b_isPType){
            this.toEdit.SetType(AccesT.PUBLIC);
            Scene toShow = this.popMaker();
            this.secondaryStage.setScene(toShow);
        }
        else if (event.getSource() == this.b_isKType){
            this.toEdit.SetType(AccesT.PROTECTED);
            Scene toShow = this.popMaker();
            this.secondaryStage.setScene(toShow);
        }
        else if (event.getSource()==this.b_isLType){
            this.toEdit.SetType(AccesT.PRIVATE);
            Scene toShow = this.popMaker();
            this.secondaryStage.setScene(toShow);
        }
        else if(event.getSource()==this.b_submitter){
            this.secondaryStage.close();
            this.toSave.Refresh();
        }
    }

}