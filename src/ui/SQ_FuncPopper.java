package ui;

import classes.*;

//f_buttons
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

//f_buttons
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

/**
 * Creates pop up window for adding to sequence diagram
 * @author xlukac16
 */
public class SQ_FuncPopper implements EventHandler<ActionEvent>{

    
    private List<Button> f_buttons;

    private Stage secondaryStage;

    private SequenceDia sq;

    private Actor source;
    private Actor target;

    private SequenceDiaInterface responder;

    private boolean constructor;

    public SQ_FuncPopper(SequenceDia sd, Actor source,Actor tar,SequenceDiaInterface responder,boolean constructor){
        f_buttons = new ArrayList<Button>();
        this.sq=sd;
        this.target=tar;
        this.source = source;
        this.responder = responder;
        this.constructor = constructor;

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

    /**
     * Creates graphical interface
     * @param target
     * @return
     */
    VBox Chooser(Actor target){
        List<CD_Element> funcList;
        if(target != null){
            funcList = this.sq.GetPossibleFunctions(target.GetClass());
            //funcList= target.GetClass().GetFunca();
        }else{
            funcList = new ArrayList<CD_Element>();
        }
        VBox v = new VBox();
        int heightOfButt = 30;
        int widthOfButt = 200;
        v.setPrefHeight(funcList.size()*heightOfButt);
        for(CD_Element elem: funcList){
            Button b = new Button(elem.GetName());
            b.setOnAction(this);
            b.setPrefWidth(widthOfButt);
            b.setPrefHeight(heightOfButt);
            this.f_buttons.add(b);
            v.getChildren().add(b);
        }
        Button b = new Button("Acknowledgement"); 
        b.setOnAction(this);
        b.setPrefWidth(widthOfButt);
        b.setPrefHeight(heightOfButt);
        this.f_buttons.add(b);
        v.getChildren().add(b);
        return v;
        
    }
    /**
     * Handles buttons
     * @param event
     */
    @Override
    public void handle(ActionEvent event){
        for(int i = 0;i<this.f_buttons.size();i++){
            if(event.getSource()==this.f_buttons.get(i)){
                if(i==this.f_buttons.size()-1){
                    this.sq.AddMessage(true, null, this.source, this.target,constructor,"");
                }else{
                    this.sq.AddMessage(false, this.sq.GetPossibleFunctions(target.GetClass()).get(i), this.source, this.target,constructor,"");
                }
                this.responder.Refresh();
                this.secondaryStage.close();
                break;
            }
        }
        
    }
}