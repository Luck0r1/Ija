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

/**
 * Creates pop up window for editing class diagram relations
 * @author xlukac16
 */
public class bPopper implements EventHandler<ActionEvent>{

    private MainC backSide;

    private BindController toSave;

    private Stage secondaryStage;

    private TextField namer;
    private TextField c1;
    private TextField c2;

    private Button b_submitter;
    private Button b_deleter;

    private Button b_l_association;
    private Button b_r_association;
    private Button b_l_multiAssoc;
    private Button b_r_multiAssoc;
    private Button b_l_Gener;
    private Button b_r_Gener;
    private Button b_l_GenerFiller;
    private Button b_r_GenerFiller;
    private Button b_l_Agreg;
    private Button b_r_Agreg;
    private Button b_l_AgregFill;
    private Button b_r_AgregFill;

    private Bind editedBind;

    private int temptypeL;
    private int temptypeR;

    public bPopper(MainC backSide,BindController calledByGrave,Bind editedB){
        this.backSide = backSide;
        this.editedBind = editedB;
        this.toSave = calledByGrave;
        this.temptypeL = editedB.Type_Get_L();
        this.temptypeR = editedB.Type_Get_R();
        Scene toShow = popMaker();
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(toShow);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }
    /**
     * Gets list of relation types buttons
     * @param side
     * @return
     */
    private VBox GetButtonList(boolean side){
        Button b1 = new Button("Association");
        Button b2 = new Button("M. Association");
        Button b3 = new Button("Generalization");
        //Button b6 = new Button("F. G.");
        Button b7 = new Button("Agregation");
        Button b8 = new Button("F.A");
        b1.setOnAction(this);
        b2.setOnAction(this);
        b3.setOnAction(this);
        b7.setOnAction(this);
        //b6.setOnAction(this);
        b8.setOnAction(this);
        if(side)this.b_l_association=b1;else this.b_r_association=b1;
        if(side)this.b_l_multiAssoc=b2; else this.b_r_multiAssoc=b2;
        if(side)this.b_l_Gener=b3; else this.b_r_Gener=b3;
        //if(side)this.b_l_GenerFiller=b6; else this.b_r_GenerFiller=b6;
        if(side)this.b_l_AgregFill = b8; else this.b_r_AgregFill = b8;
        if(side)this.b_l_Agreg = b7; else this.b_r_Agreg = b7;
        VBox returner = new VBox();
        returner.getChildren().addAll(b1,b2,b3,b7,b8);
        return returner;
    }

    /**
     * Creates pop up window interface
     * @return
     */
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

        VBox buttonsL = this.GetButtonList(true);
        VBox buttonsR = this.GetButtonList(false);

        topper.getChildren().addAll(buttonsL, c1,tabber1,namer,tabber2,c2,buttonsR);

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

    /**
     * Handles buttons
     * @param event
     */
    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_submitter){
            this.editedBind.SetName(this.namer.getText());
            this.editedBind.Set_C1(this.c1.getText());
            this.editedBind.Set_C2(this.c2.getText());
            this.editedBind.Type_Set_L(this.temptypeL);
            this.editedBind.Type_Set_R(this.temptypeR);
            this.secondaryStage.close();
            this.toSave.Refresh();
        }

        if(event.getSource()==this.b_deleter){
            this.toSave.SendToTheRaveYard();
            this.secondaryStage.close();
            this.toSave.Refresh();
        }

        if(event.getSource()==this.b_l_multiAssoc)
            this.temptypeL=0;
        else if(event.getSource()==this.b_r_multiAssoc)
            this.temptypeR=0;
        else if (event.getSource()==this.b_l_Gener)
            this.temptypeL=1;
        else if (event.getSource()==this.b_r_Gener)
            this.temptypeR=1;
        else if (event.getSource()==this.b_l_association)
            this.temptypeL=2;
        else if (event.getSource()==this.b_r_association)
            this.temptypeR=2;
        else if (event.getSource()==this.b_l_Agreg)
            this.temptypeL=3;
        else if (event.getSource()==this.b_r_Agreg)
            this.temptypeR=3;
        else if (event.getSource()==this.b_l_AgregFill)
            this.temptypeL=4;
        else if (event.getSource()==this.b_r_AgregFill)
            this.temptypeR=4;

        
        
    }

}