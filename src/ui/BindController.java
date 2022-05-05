
package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.Dimension;
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

    public BindController( Bind tar,MainC main,ClassDiagram eC){
        this.curB = tar;
        this.editedCD = eC;
        this.drawnBind = this.GetLine(tar,main);
        this.main = main;
    }

    private Group GetLine(Bind tar,MainC maii){
        Group returner = new Group();

        LineDrawer drawer = new LineDrawer();

        Button renamer = new Button(tar.GetName());

        List<Dimension> partsList = tar.GetDims();
        Dimension midPos = partsList.get((partsList.size()-2)/2);

        renamer.setLayoutX(Converts.ToWorldCoordinatesX((int)midPos.getWidth())-10);
        renamer.setLayoutY(Converts.ToWorldCoordinatesY((int)midPos.getHeight())-25);
        renamer.setOnAction(this);
        this.renameClass = renamer;
        
        if(tar.Type_Get_L()==0){
            Button side1 = new Button(tar.Get_C1());
            side1.setLayoutX(Converts.ToWorldCoordinatesX((int)partsList.get(0).getWidth())-10);
            side1.setLayoutY(Converts.ToWorldCoordinatesY((int)partsList.get(0).getHeight())-10);
            side1.setOnAction(this);
            this.c1 = side1;
            returner.getChildren().add(side1);
        }

        if(tar.Type_Get_R()==0){
            Button side2 = new Button(tar.Get_C2());
            side2.setLayoutX(Converts.ToWorldCoordinatesX((int)partsList.get(1).getWidth())-10);
            side2.setLayoutY(Converts.ToWorldCoordinatesY((int)partsList.get(1).getHeight())-10); 
            side2.setOnAction(this);
            this.c2 = side2;    
            returner.getChildren().add(side2);
        }

        for(Dimension d : partsList){
            Dimension sender = new Dimension((int)Converts.ToWorldCoordinatesX((int)d.getWidth()),(int)Converts.ToWorldCoordinatesY((int)d.getHeight()));
            Group g = drawer.DrawXY(maii.mapper.GetVal((int)d.getWidth(),(int)d.getHeight()), sender);
            returner.getChildren().add(g);
        }

        returner.getChildren().add(renamer);

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