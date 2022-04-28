package support;

import support.*;

import java.util.*;
import java.awt.Dimension;
import javafx.scene.layout.Pane;
import classes.*;


public class Mapper{

    private int offset = 3;
    ArrayList<ArrayList<Integer>> mapper;

    private void LoadMap(int x,int y){
        this.mapper = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<x/5;i++)
            this.mapper.add(new ArrayList<Integer>(Collections.nCopies(y/5, 0)));
    }

    public void FillSpaceForClass(classes.Class c){

        int strtX = c.GetPosX();
        int strtY = c.GetPosY();
        int endX = strtX + c.GetGraphicWidth()/5;
        int endY = strtY + c.GetGraphicHeigth()/5;

        for (int i = strtX-this.offset;i<endX+this.offset;i++){
            for(int j=strtY-this.offset;j<endY +this.offset;j++){
                this.mapper.get(i).set(j, -1);
            }
        }

        for(int i = strtX; i<endX;i++){
            for (int j = strtY; j<endY;j++){
                this.mapper.get(i).set(j, c.GetId()+1);
            }
        }
    }

    public void UnfilSpaceForClass(classes.Class c){

        int strtX = c.GetPosX();
        int strtY = c.GetPosY();
        int endX = strtX + c.GetGraphicWidth()/5;
        int endY = strtY + c.GetGraphicHeigth()/5;

        for (int i = strtX-this.offset;i<endX +this.offset;i++){
            for(int j=strtY-this.offset;j<endY +this.offset;j++){
                this.mapper.get(i).set(j, 0);
            }
        }
    }

    public int GetVal(int x,int y){
        return mapper.get(x).get(y);
    }

    public int GetMap(double x,double y){
        return this.mapper.get(Converts.ToMapCoordinatesX(x)).get(Converts.ToMapCoordinatesY(y));
    }

    private boolean IsEmpty(int startX,int startY,int width,int height){
        int bgnX = startX-offset;
        int endX = startX + width + this.offset;
        int bgnY = startY - offset;
        int endY = startY + height + this.offset;
        if(bgnX<=0 || bgnY<=0)return false;
        for(int i=startX; i< endX;i++){
            for(int j=startY;j<endY;j++){
                int x;
                try {
                    x=this.GetVal(i, j);
                } catch (Exception e) {
                    return false;
                }
                if(x!=0)
                    return false;
            }
        }
        return true;
    }

    public Dimension FindClosestPlace(classes.Class c,double newX, double newY,int dimX){
        Dimension h = new Dimension(-1,-1);
        int tarPosX = Converts.ToMapCoordinatesX(newX);
        int tarPosY = Converts.ToMapCoordinatesY(newY);
        if(c.GetPosX()!=0)
            this.UnfilSpaceForClass(c);

        if(this.IsEmpty(tarPosX, tarPosY, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
            h.setSize(tarPosX, tarPosY);
            return h;
        }

        for (int d = 1; d<mapper.size()*2; d++)
        {
            for (int i = 0; i < d + 1; i++)
            {
                int x1 = tarPosX - d + i;
                int y1 = tarPosY - i;

                if(this.IsEmpty(x1, y1, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x1, y1);
                    return h;
                }

                int x2 = tarPosX + d - i;
                int y2 = tarPosY + i;

                if(this.IsEmpty(x2, y2, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x2, y2);
                    return h;
                }
            }


            for (int i = 1; i < d; i++)
            {
                int x1 = tarPosX - i;
                int y1 = tarPosY + d - i;

                if(this.IsEmpty(x1, y1, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x1, y1);
                    return h;
                }

                int x2 = tarPosX + i;
                int y2 = tarPosY - d + i;

                if(this.IsEmpty(x2, y2, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(x2, y2);
                    return h;
                }
            }
        }
        /*
        if(this.IsEmpty(tarPosX, tarPosY, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
            h.setSize(tarPosX, tarPosY);
        }else{
            for(int i=1;i<this.dimX/10;i++){
                if(this.IsEmpty(tarPosX+i, tarPosY, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX+i, tarPosY);
                    break;
                }
                if(this.IsEmpty(tarPosX-i, tarPosY, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX-i, tarPosY);
                    break;
                }
                if(this.IsEmpty(tarPosX, tarPosY+i, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX, tarPosY+i);
                    break;
                }
                if(this.IsEmpty(tarPosX, tarPosY-i, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX, tarPosY-i);
                    break;
                }
                if(this.IsEmpty(tarPosX-i, tarPosY-i, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX-i, tarPosY-i);
                    break;
                }
                if(this.IsEmpty(tarPosX+i, tarPosY+i, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX+i, tarPosY+i);
                    break;
                }
                if(this.IsEmpty(tarPosX-i, tarPosY+i, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX-i, tarPosY+i);
                    break;
                }
                if(this.IsEmpty(tarPosX+i, tarPosY-i, c.GetGraphicWidth()/5, c.GetGraphicHeigth()/5)){
                    h.setSize(tarPosX+i, tarPosY-i);
                    break;
                }
            }
        }
        */
        return h;
    }

    private void FillAll(ClassDiagram cd){
        for (classes.Class c : cd.GetClasses()){
            this.FillSpaceForClass(c);
        }
    }

    public Pane DebugMap(){
        Pane rPane = new Pane();
        for(int i=0;i<this.mapper.size();i++){
            for(int j=0;j<this.mapper.get(i).size();j++){
                int x = this.mapper.get(i).get(j);
                    Pane newPane = new Pane();
                    newPane.setPrefHeight(5);
                    newPane.setPrefWidth(5);
                    newPane.setLayoutX(Converts.ToWorldCoordinatesX(i));
                    newPane.setLayoutY(Converts.ToWorldCoordinatesY(j));
                    if(x==0)
                        newPane.setStyle("-fx-background-color: #34eb43");
                    else if(x==-1){
                        newPane.setStyle("-fx-background-color: #ebae34");
                    }
                    else if(x>0){
                        newPane.setStyle("-fx-background-color: #eb3434");
                    }else{
                        newPane.setStyle("-fx-background-color: #000000");
                    }
                    rPane.getChildren().add(newPane);
            }
        }
    
        return rPane;
    }

    public Mapper(int dimX,int dimY,ClassDiagram cd){
        this.LoadMap(dimX-300, dimY);
        this.FillAll(cd);
    }
    
}