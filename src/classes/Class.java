package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;

import classes.CD_Element;

public class Class{

    private int id;

    private List<CD_Element> elements;
    private List<CD_Element> functiona;
    private String name;

    private int x;
    private int y;

    public Class(String name){
        this.name = name;
        this.elements = new ArrayList<CD_Element>();
        this.functiona = new ArrayList<CD_Element>();
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

    /*
    public CD_Element Get_Element(String toSearch){
        for(CD_Element func : this.elements){
            if(func.GetName().equals(toSearch)){
                return func;
            }
        }
        return null;
    }

    public CD_Element Get_Func(String toSearch){
        for(CD_Element func : this.functiona){
            if(func.GetName().equals(toSearch)){
                return func;
            }
        }
        return null;
    }*/

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