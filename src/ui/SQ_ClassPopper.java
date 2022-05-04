package ui;


//Buttons
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.util.*;
import javafx.scene.Scene;

import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;

import javafx.scene.layout.VBox;

import classes.ClassDiagram;
import classes.SequenceDia;
import classes.Actor;

public class SQ_ClassPopper implements EventHandler<ActionEvent>{

    private List<Button> buttons;

    private Stage secondaryStage;

    private SequenceDiaInterface sq;
    private ClassDiagram cd;
    private boolean makeEternal;
    private Actor sourceA;
    private SequenceDia sqd;

    public SQ_ClassPopper(ClassDiagram cd,SequenceDiaInterface resPonder,boolean eternal,Actor a,SequenceDia sq){
        this.buttons=new ArrayList<Button>();
        this.sq=resPonder;
        this.cd = cd;
        this.sqd = sq;
        this.sourceA = a;
        this.makeEternal = eternal;

        VBox content = this.Chooser(cd);
        ScrollPane pane = new ScrollPane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(5*30);
        pane.setContent(content);
        
        this.secondaryStage = new Stage();
        Scene layout = new Scene(pane);
        this.secondaryStage.setScene(layout);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    VBox Chooser(ClassDiagram cd){
        List<classes.Class> classList = cd.GetClasses();
        VBox v = new VBox();
        int heightOfButt = 30;
        int widthOfButt = 200;
        v.setPrefHeight(classList.size()*heightOfButt);
        for(classes.Class c : classList){
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
        int i=0;
        for(Button b : this.buttons){
            if(event.getSource()==b){
                this.secondaryStage.close();
                Actor a = this.sq.AddActor(this.cd.GetClasses().get(i),this.makeEternal);
                if(!this.makeEternal){
                    SQ_FuncPopper sPop = new SQ_FuncPopper(this.sqd, this.sourceA, a, this.sq,true);
                }
                break;
            }
            i++;
        }
        
    }
}