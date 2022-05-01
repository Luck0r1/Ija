package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;


import java.awt.Dimension;
import classes.CD_Element;

public class Class{

    private int id;

    private List<CD_Element> elements;
    private List<CD_Element> functiona;
    private String name;

    private List<Dimension> top;
    private List<Dimension> bottom;
    private List<Dimension> left;
    private List<Dimension> rigth;

    private int x;
    private int y;

    public Class(String name){
        this.name = name;
        this.elements = new ArrayList<CD_Element>();
        this.functiona = new ArrayList<CD_Element>();
    }

    public void RefillConnectors(){
        this.top = new ArrayList<Dimension>();
        this.bottom = new ArrayList<Dimension>();
        this.left = new ArrayList<Dimension>();
        this.rigth = new ArrayList<Dimension>();

        int xb = this.GetPosX()+2;
        int yb = this.GetPosY()-3;
        while (xb < this.GetPosX() + this.GetGraphicWidth()/5){
            this.top.add(new Dimension(xb,yb));
            xb+=5;
        }

        xb = this.GetPosX()+2;
        yb = this.GetPosY() + GetGraphicHeigth()/5 + 2;
        while (xb < this.GetPosX() + this.GetGraphicWidth()/5){
            this.bottom.add(new Dimension(xb,yb));
            xb+=5;
        }

        xb = this.GetPosX()-3;
        yb = this.GetPosY()+2;
        while (yb < this.GetPosY() + this.GetGraphicHeigth()/5){
            this.left.add(new Dimension(xb,yb));
            yb +=5;
        }

        xb = this.GetPosX()+GetGraphicWidth()/5+2;
        yb = this.GetPosY()+2;
        while (yb < this.GetPosY() + this.GetGraphicHeigth()/5){
            this.rigth.add(new Dimension(xb,yb));
            yb +=5;
        }
    }

    public List<Dimension> GetConTop(){
        return this.top;
    }
    public List<Dimension> GetConBot(){
        return this.bottom;
    }
    public List<Dimension> GetConLef(){
        return this.left;
    }
    public List<Dimension> GetConRig(){
        return this.rigth;
    }

    public void SetPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int GetPosX(){
        return this.x;
    }

    public int GetPosY(){
        return this.y;
    }

    public int GetId(){
        return this.id;
    }

    public void SetId(int newId){
        this.id=newId;
    }

    public String GetName(){
        return this.name;
    }

    public void SetName(String newName){
        this.name = newName;
    }

    public List<CD_Element> GetElements(){
        return this.elements;
    }

    public List<CD_Element> GetFunca(){
        return this.functiona;
    }

    public CD_Element addElement(String newElem){
        CD_Element newObj = new CD_Element(newElem);
        this.elements.add(newObj);
        return newObj;
    }

    public void forceAdd(CD_Element neu){
        if(neu.IsFunc()){
            this.functiona.add(neu);
        }else{
            this.elements.add(neu);
        }
    }

    public CD_Element addFunc(String newFunc){
        CD_Element tAdd = new CD_Element(newFunc);
        tAdd.SetFunc(true);
        this.functiona.add(tAdd);
        return tAdd;
    }

    public void remElement(CD_Element toRem){
        this.elements.remove(toRem);
    }

    public void remFunction(CD_Element toRem){
        this.functiona.remove(toRem);
    }

    public void reNameElement(String old,String newN){
        for(CD_Element elem : this.elements){
            if(elem.GetName()==old){
                elem.ReName(newN);
                break;
            }
        }
    }

    public void reNameFunc(String old,String newN){
        for(CD_Element elem : this.functiona){
            if(elem.GetName()==old){
                elem.ReName(newN);
                break;
            }
        }
    }

    public int GetGraphicWidth(){
        return 200;
    }

    public int GetGraphicHeigth(){
        return 120+this.elements.size()*25 + this.functiona.size()*25;  
    }
}