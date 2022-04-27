
package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.*;

import javax.swing.ButtonGroup;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.shape.Line;
import javafx.scene.Group; 

import classes.*;
import ui.*;
import support.*;

public class BindController implements EventHandler<ActionEvent>{

    public Button c1;
    public Button c2;
    public Button renameClass;

    public Bind curB;
    public Group drawnBind;

    public MainC main;

    public ClassDiagram editedCD;

    public BindController( double strX,double strY, double endX, double endY,Side f,Bind tar,MainC main,ClassDiagram eC){
        this.curB = tar;
        this.editedCD = eC;
        this.drawnBind = this.GetLine(strX,strY,endX,endY,f,tar);
        this.main = main;
    }

    private Group GetLine(double strX,double strY, double endX, double endY,Side f,Bind tar){
        Group returner = new Group();

        Line ln = new Line(strX,strY,endX,endY);

        Button renamer = new Button(tar.GetName());
        renamer.setLayoutX((strX+endX)/2+5);
        renamer.setLayoutY((strY+endY)/2+5);
        renamer.setOnAction(this);
        this.renameClass = renamer; 

        if(f==Side.TOP || f==Side.BOTTOM){
            Button side1 = new Button(tar.Get_C1());
            Button side2 = new Button(tar.Get_C2());
            side1.setLayoutX(strX +15);
            side1.setLayoutY(strY);
            side2.setLayoutX(endX+15);
            side2.setLayoutY(endY);
            side1.setOnAction(this); 
            side2.setOnAction(this);
            this.c1 = side1;
            this.c2 = side2;
            returner.getChildren().addAll(ln,side1,side2,renamer);
        }else{
            Button side1 = new Button(tar.Get_C1());
            Button side2 = new Button(tar.Get_C2());
            side1.setLayoutX(strX);
            side1.setLayoutY(strY+15);
            side2.setLayoutX(endX);
            side2.setLayoutY(endY+15);
            side1.setOnAction(this); 
            side2.setOnAction(this);
            this.c1 = side1;
            this.c2 = side2;
            returner.getChildren().addAll(ln,side1,side2,renamer);
        }

        return returner;
    }

    public Group GetDrawn(){
        return this.drawnBind;
    }

    public void Refresh(){
        this.main.Refresh();
    }

    public void SendToTheRaveYard(){
        this.editedCD.Bind_Delete(this.curB);
        this.main.Refresh();
    }

    @Override
    public void handle(ActionEvent event){
        bPopper pop = new bPopper(this.main, this,this.curB);
        this.main.Refresh();

    }

    class Delta { double x, y; }
}