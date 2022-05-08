package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;


import java.awt.Dimension;
import classes.CD_Element;

/**
 * Stores information about single class
 * @author xlukac16
 */
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

    private ClassDiagram cd;

    public Class(String name,ClassDiagram cd){
        this.name = name;
        this.cd = cd;
        this.elements = new ArrayList<CD_Element>();
        this.functiona = new ArrayList<CD_Element>();
    }
    /**
     * Refills connectors of class
     */
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
    /**
     * Get empty places in top connectors
     * @return
     */
    public List<Dimension> GetConTop(){
        return this.top;
    }
    /**
     * Get empty places in bottom connectors
     * @return
     */
    public List<Dimension> GetConBot(){
        return this.bottom;
    }
    /**
     * Get empty places on left connectors
     * @return
     */
    public List<Dimension> GetConLef(){
        return this.left;
    }
    /**
     * Get empty places on rigth connectors
     * @return
     */
    public List<Dimension> GetConRig(){
        return this.rigth;
    }

    /**
     * Sets position of class
     * @return
     */
    public void SetPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    /**
     * Gets x position of class
     * @return
     */
    public int GetPosX(){
        return this.x;
    }
    /**
     * Gets y position of class
     * @return
     */
    public int GetPosY(){
        return this.y;
    }

    /**
     * Gets id of class
     * @return
     */
    public int GetId(){
        return this.id;
    }

    /**
     * Sets id of class
     * @param newId
     */
    public void SetId(int newId){
        this.id=newId;
    }

    /**
     * Gets name of class
     * @return
     */
    public String GetName(){
        return this.name;
    }

    /**
     * Sets name of class
     * @param newName
     */
    public void SetName(String newName){
        this.name = newName;
    }

    /**
     * Gets elements in class
     * @return
     */
    public List<CD_Element> GetElements(){
        return this.elements;
    }

    /**
     * Gets function in class
     * @return
     */
    public List<CD_Element> GetFunca(){
        return this.functiona;
    }

    /**
     * Adds element to class
     * @param newElem
     * @return
     */
    public CD_Element addElement(String newElem){
        CD_Element newObj = new CD_Element(newElem);
        this.elements.add(newObj);
        return newObj;
    }
    /**
     * Adds elements to class by element
     * @param neu
     */
    public void forceAdd(CD_Element neu){
        if(neu.IsFunc()){
            this.functiona.add(neu);
        }else{
            this.elements.add(neu);
        }
    }
    /**
     * Adds function to class
     * @param newFunc
     * @return
     */
    public CD_Element addFunc(String newFunc){
        CD_Element tAdd = new CD_Element(newFunc);
        tAdd.SetFunc(true);
        this.functiona.add(tAdd);
        return tAdd;
    }
    /**
     * Removes element from class
     * @param toRem
     */
    public void remElement(CD_Element toRem){
        this.elements.remove(toRem);
        this.cd.ReId();
    }

    /**
     * Removes function from class
     * @param toRem
     */
    public void remFunction(CD_Element toRem){
        this.functiona.remove(toRem);
        this.cd.ReId();
    }
    /**
     * Renames element
     * @param old
     * @param newN
     */
    public void reNameElement(String old,String newN){
        for(CD_Element elem : this.elements){
            if(elem.GetName()==old){
                elem.ReName(newN);
                break;
            }
        }
    }
    /**
     * Renames function
     * @param old
     * @param newN
     */
    public void reNameFunc(String old,String newN){
        for(CD_Element elem : this.functiona){
            if(elem.GetName()==old){
                elem.ReName(newN);
                break;
            }
        }
    }
    /**
     * Returns width of graphical instance
     * @return
     */
    public int GetGraphicWidth(){
        return 200;
    }
    /**
     * returns heigth of graphical instance
     */
    public int GetGraphicHeigth(){
        return 120+this.elements.size()*25 + this.functiona.size()*25;  
    }
}