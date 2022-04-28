package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Buttons
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
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
import java.util.function.Function;

import javax.crypto.AEADBadTagException;
import javax.swing.ButtonModel;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Line;

import java.util.*;
import java.awt.Dimension;

import classes.*;
import support.*;
import ui.*;

public class LineDrawer{
    
    private Group DrawVertical(double LM_pos){
        Group g = new Group();

        Line l = new Line();
        l.setStartX(LM_pos+2.5);
        l.setStartY(LM_pos);
        l.setEndX(LM_pos+2.5);
        l.setEndY(LM_pos+5);
        g.getChildren().add(l);

        return g;
    }

    private Group DrawHorizontal(double LM_pos){
        Group g = new Group();

        Line l = new Line();
        l.setStartX(LM_pos);
        l.setStartY(LM_pos+2.5);
        l.setEndX(LM_pos+5);
        l.setEndY(LM_pos+2.5);
        g.getChildren().add(l);

        return g;
    }

    private Group DrawNine(double LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        l1.setStartX(LM_pos);
        l1.setStartY(LM_pos+2.5);
        l1.setEndX(LM_pos+2.5);
        l1.setEndY(LM_pos+2.5);

        l2.setStartX(LM_pos+2.5);
        l2.setStartY(LM_pos+2.5);
        l2.setEndX(LM_pos+2.5);
        l2.setEndY(LM_pos);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawThree(double LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        l1.setStartX(LM_pos+2.5);
        l1.setStartY(LM_pos);
        l1.setEndX(LM_pos+2.5);
        l1.setEndY(LM_pos+2.5);

        l2.setStartX(LM_pos+2.5);
        l2.setStartY(LM_pos+2.5);
        l2.setEndX(LM_pos+5);
        l2.setEndY(LM_pos+2.5);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawOPThree(double LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        l1.setStartX(LM_pos+2.5);
        l1.setStartY(LM_pos+2.5);
        l1.setEndX(LM_pos+5);
        l1.setEndY(LM_pos+2.5);

        l2.setStartX(LM_pos+2.5);
        l2.setStartY(LM_pos+2.5);
        l2.setEndX(LM_pos+2.5);
        l2.setEndY(LM_pos+5);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawOPNine(double LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        l1.setStartX(LM_pos);
        l1.setStartY(LM_pos+2.5);
        l1.setEndX(LM_pos+2.5);
        l1.setEndY(LM_pos+2.5);

        l2.setStartX(LM_pos+2.5);
        l2.setStartY(LM_pos+2.5);
        l2.setEndX(LM_pos+2.5);
        l2.setEndY(LM_pos+5);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    public Group DrawXY(int num,double LM_pos){
        switch (num) {
            case -2:
                return DrawVertical(LM_pos);
                break;

            case -3:
                return DrawHorizontal(LM_pos);
                break;

            case -4:
                return DrawNine(LM_pos);
                break;

            case -5:
                return DrawThree(LM_pos);
                break;

            case -6:
                return DrawOPThree(LM_pos);
                break;
            
            case -7:
                return DrawOPNine(LM_pos);
                break;
        
            default:
                break;
        }
    }

    
}