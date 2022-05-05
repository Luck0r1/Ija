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

import javafx.scene.shape.Polygon;

import javafx.scene.shape.Line;

import java.util.*;
import java.awt.Dimension;

import classes.*;
import support.*;
import ui.*;

public class LineDrawer{
    
    private Group DrawVertical(Dimension LM_pos){
        Group g = new Group();
        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();
        Line l = new Line();
        l.setStartX(LM_Pos_x+2.5);
        l.setStartY(LM_Pos_y);
        l.setEndX(LM_Pos_x+2.5);
        l.setEndY(LM_Pos_y+5);
        g.getChildren().add(l);

        return g;
    }

    private Group DrawHorizontal(Dimension LM_pos){
        Group g = new Group();
        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();
        Line l = new Line();
        l.setStartX(LM_Pos_x );
        l.setStartY(LM_Pos_y+2.5);
        l.setEndX(LM_Pos_x +5);
        l.setEndY(LM_Pos_y+2.5);
        g.getChildren().add(l);

        return g;
    }

    private Group DrawNine(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x);
        l1.setStartY(LM_Pos_y+2.5);
        l1.setEndX(LM_Pos_x+2.5);
        l1.setEndY(LM_Pos_y+2.5);

        l2.setStartX(LM_Pos_x+2.5);
        l2.setStartY(LM_Pos_y+2.5);
        l2.setEndX(LM_Pos_x+2.5);
        l2.setEndY(LM_Pos_y);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawThree(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x+2.5);
        l1.setStartY(LM_Pos_y);
        l1.setEndX(LM_Pos_x+2.5);
        l1.setEndY(LM_Pos_y+2.5);

        l2.setStartX(LM_Pos_x+2.5);
        l2.setStartY(LM_Pos_y+2.5);
        l2.setEndX(LM_Pos_x+5);
        l2.setEndY(LM_Pos_y+2.5);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawOPThree(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x+2.5);
        l1.setStartY(LM_Pos_y+2.5);
        l1.setEndX(LM_Pos_x+5);
        l1.setEndY(LM_Pos_y+2.5);

        l2.setStartX(LM_Pos_x+2.5);
        l2.setStartY(LM_Pos_y+2.5);
        l2.setEndX(LM_Pos_x+2.5);
        l2.setEndY(LM_Pos_y+5);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawOPNine(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x);
        l1.setStartY(LM_Pos_y+2.5);
        l1.setEndX(LM_Pos_x+2.5);
        l1.setEndY(LM_Pos_y+2.5);

        l2.setStartX(LM_Pos_x+2.5);
        l2.setStartY(LM_Pos_y+2.5);
        l2.setEndX(LM_Pos_x+2.5);
        l2.setEndY(LM_Pos_y+5);        

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawX(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x+2.5);
        l1.setStartY(LM_Pos_y);
        l1.setEndX(LM_Pos_x+2.5);
        l1.setEndY(LM_Pos_y+5);

        l2.setStartX(LM_Pos_x);
        l2.setStartY(LM_Pos_y+2.5);
        l2.setEndX(LM_Pos_x+5);
        l2.setEndY(LM_Pos_y+2.5);

        g.getChildren().addAll(l1,l2);

        return g;
    }

    private Group DrawLongHorizontal(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x+2.5);
        l1.setStartY(LM_Pos_y-10);
        l1.setEndX(LM_Pos_x+2.5);
        l1.setEndY(LM_Pos_y+15);

        g.getChildren().add(l1);

        return g;
    }

    private Group DrawLongVertical(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x-10);
        l1.setStartY(LM_Pos_y+2.5);
        l1.setEndX(LM_Pos_x+15);
        l1.setEndY(LM_Pos_y+2.5);

        g.getChildren().add(l1);

        return g;
    }

    private Group DrawArrowDown(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();
        Line l3 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x-5);
        l1.setStartY(LM_Pos_y-5);
        l1.setEndX(LM_Pos_x+10);
        l1.setEndY(LM_Pos_y-5);

        l2.setStartX(LM_Pos_x-5);
        l2.setStartY(LM_Pos_y-5);
        l2.setEndX(LM_Pos_x+2.5);
        l2.setEndY(LM_Pos_y+10);

        l3.setStartX(LM_Pos_x+2.5);
        l3.setStartY(LM_Pos_y+10);
        l3.setEndX(LM_Pos_x+10);
        l3.setEndY(LM_Pos_y-5);

        g.getChildren().addAll(l1,l2,l3);

        return g;
    }

    private Group DrawArrowLeft(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();
        Line l3 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x+10);
        l1.setStartY(LM_Pos_y-5);
        l1.setEndX(LM_Pos_x-5);
        l1.setEndY(LM_Pos_y+2.5);

        l2.setStartX(LM_Pos_x-5);
        l2.setStartY(LM_Pos_y+2.5);
        l2.setEndX(LM_Pos_x+10);
        l2.setEndY(LM_Pos_y+10);

        l3.setStartX(LM_Pos_x+10);
        l3.setStartY(LM_Pos_y-5);
        l3.setEndX(LM_Pos_x+10);
        l3.setEndY(LM_Pos_y+10);

        g.getChildren().addAll(l1,l2,l3);

        return g;
    }

    private Group DrawArrowRigth(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();
        Line l3 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x-5);
        l1.setStartY(LM_Pos_y-5);
        l1.setEndX(LM_Pos_x-5);
        l1.setEndY(LM_Pos_y+10);

        l2.setStartX(LM_Pos_x-5);
        l2.setStartY(LM_Pos_y-5);
        l2.setEndX(LM_Pos_x+10);
        l2.setEndY(LM_Pos_y+2.5);

        l3.setStartX(LM_Pos_x+10);
        l3.setStartY(LM_Pos_y+2.5);
        l3.setEndX(LM_Pos_x-5);
        l3.setEndY(LM_Pos_y+10);

        g.getChildren().addAll(l1,l2,l3);

        return g;
    }

    private Group DrawArrowUp(Dimension LM_pos){
        Group g = new Group();

        Line l1 = new Line();
        Line l2 = new Line();
        Line l3 = new Line();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        l1.setStartX(LM_Pos_x-5);
        l1.setStartY(LM_Pos_y+10);
        l1.setEndX(LM_Pos_x+10);
        l1.setEndY(LM_Pos_y+10);

        l2.setStartX(LM_Pos_x+2.5);
        l2.setStartY(LM_Pos_y-5);
        l2.setEndX(LM_Pos_x-5);
        l2.setEndY(LM_Pos_y+10);

        l3.setStartX(LM_Pos_x+2.5);
        l3.setStartY(LM_Pos_y-5);
        l3.setEndX(LM_Pos_x+10);
        l3.setEndY(LM_Pos_y+10);

        g.getChildren().addAll(l1,l2,l3);

        return g;
    }

    private Group DrawSquare(Dimension LM_pos,boolean fill){
        Group g = new Group();

        double LM_Pos_x = LM_pos.getWidth();
        double LM_Pos_y = LM_pos.getHeight();

        Polygon p = new Polygon();
        p.getPoints().addAll(new Double[]{
            LM_Pos_x+2.5, LM_Pos_y-10,
            LM_Pos_x+15, LM_Pos_y+2.5,
            LM_Pos_x+2.5, LM_Pos_y+15,
            LM_Pos_x-10, LM_Pos_y+2.5 });
        if(fill)        
            p.setFill(javafx.scene.paint.Color.BLACK);
        else{
            p.setFill(javafx.scene.paint.Color.TRANSPARENT);
            p.setStroke(javafx.scene.paint.Color.BLACK);

        }
        g.getChildren().add(p);
        return g;
    }

    public Group DrawXY(int num,Dimension LM_pos){
        Side sides[] = {Side.LEFT,Side.TOP,Side.RIGHT,Side.BOTTOM};
        switch (num) {
            case -2:
                return DrawHorizontal(LM_pos);

            case -3:
                return DrawVertical(LM_pos);

            case -4:
                return DrawThree(LM_pos);

            case -5:
                return DrawOPThree(LM_pos);

            case -6:
                return DrawOPNine(LM_pos);
            
            case -7:
                return DrawNine(LM_pos);
        
            case -8:
                return DrawX(LM_pos);

            case -9:
                return DrawLongVertical(LM_pos);
            
            case -10:
                return DrawLongHorizontal(LM_pos);

            case -11:
                return DrawArrowLeft(LM_pos);

            case -12:
                return DrawArrowUp(LM_pos);

            case -13:
                return DrawArrowRigth(LM_pos);

            case -14:
                return DrawArrowDown(LM_pos);

            case -15:
                return DrawLongHorizontal(LM_pos);

            case -16:
                return DrawLongVertical(LM_pos);

            case -17:
                return DrawLongHorizontal(LM_pos);

            case -18:
                return DrawLongVertical(LM_pos);

            case -19:
            case -20:
            case -21:
            case -22:
                return DrawSquare(LM_pos, false);
                
            case -23:
            case -24:
            case -25:
            case -26:
                return DrawSquare(LM_pos, true);

            default:
                return DrawX(LM_pos);
        }
    }

    
}