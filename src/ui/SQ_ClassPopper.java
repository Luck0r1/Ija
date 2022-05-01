package ui;


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

import classes.ClassDiagram;
import classes.SequenceDia;

public class SQ_ClassPopper implements EventHandler<ActionEvent>{

    private List<Button> buttons;

    private Stage secondaryStage;

    private SequenceDia sq;

    public SQ_ClassPopper(ClassDiagram cd,SequenceDia resPonder){
        this.buttons=new ArrayList<Button>();
        this.sq=resPonder;

        VBox content = this.Chooser(cd);
        ScrollPane pane = new ScrollPane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(5*30);
        
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(pane);
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
                this.resPonder.AddActor(this.cd.GetClasses().get(i));
                break;
            }
            i++;
        }
        
    }
}