package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;

import classes.CD_Element;

public class Class{

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

    public void addElement(String newElem){
        this.elements.add( new CD_Element(newElem));
    }

    public void forceAdd(CD_Element neu){
        if(neu.IsFunc()){
            this.functiona.add(neu);
        }else{
            this.elements.add(neu);
        }
    }

    public void addFunc(String newFunc){
        CD_Element tAdd = new CD_Element(newFunc);
        tAdd.SetFunc(true);
        this.functiona.add(tAdd);
    }

    public CD_Element Get_Element(String toSearch){
        for(CD_Element func : this.elements){
            if(func.GetName() == toSearch){
                return func;
            }
        }
        for(CD_Element func : this.functiona){
            if(func.GetName() == toSearch){
                return func;
            }
        }
        return null;
    }

    public void remElement(String toRem){
        for(CD_Element elem : this.elements){
            if(elem.GetName()==toRem){
                this.elements.remove(elem);
                break;
            }
        }
    }

    public void remFunction(String toRem){
        for(CD_Element func : this.functiona){
            if(func.GetName() == toRem){
                this.functiona.remove(func);
                break;
            }
        }
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
}