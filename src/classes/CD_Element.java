package classes;

import support.AccesT;
import java.util.*;
/**
 * Stores information of single element
 */
public class CD_Element{

    private String name;
    private String type; 
    private AccesT acces;
    private boolean func = false;
    private String returnT="";
    private int id;

    public CD_Element(String name){
        this.name=name;
        this.type="";
        this.acces=AccesT.PUBLIC;
    }
    /**
     * Sets id
     * @param id
     */
    public void SetId(int id){
        this.id=id;
    }
    /**
     * Gets id
     * @return
     */
    public int GetId(){
        return this.id;
    }
    /**
     * Gets name
     * @return
     */
    public String GetName(){
        return this.name;
    }
    /**
     * gets type
     * @return
     */
    public String GetType(){
        return this.type;
    }
    /**
     * Gets return type
     * @return
     */
    public String GetReturnT(){
        return this.returnT;
    }
    /**
     * Sets return type
     * @param newT
     */
    public void SetReturnT(String newT){
        this.returnT=newT;
    }
    /**
     * Gets access type
     * @return
     */
    public String GetAccess(){
        if(this.acces==AccesT.PUBLIC)
            return "public";
        else if(this.acces == AccesT.PROTECTED)
            return "protected";
        else
            return "private";
    }  
    /**
     * Sets type
     * @param x
     */
    public void SetType(AccesT x){
        this.acces = x;
    }
    /**
     * Checks if is function
     * @return
     */
    public Boolean IsFunc(){
        return this.func;
    }
    /**
     * Renames element
     * @param name
     */
    public void ReName(String name){
        this.name=name;
    }
    /**
     * Retypes element
     * @param type
     */
    public void ReType(String type){
        this.type = type;
    }
    /**
     * Sets if is a function
     * @param bl
     */
    public void SetFunc(boolean bl){
        if(bl)
            this.func=true;
    }

    /**
     * Edits accesability
     * @return
     */
    private String RetAcces(){
        if(this.acces==AccesT.PUBLIC){
            return "+";
        }
        else if(this.acces==AccesT.PROTECTED){
            return " ";
        }
        else if(this.acces==AccesT.PRIVATE){
            return "-";
        }
        return "I";
    }
    /**
     * Return text on button
     * @return
     */
    public String ToString(){
        if(!this.func)
            return this.RetAcces()+" "+this.name+" : "+this.type;
        else{
            if(returnT=="")
                return this.RetAcces()+" "+this.name+"("+this.type+")";
            else
                return this.RetAcces()+" "+this.name+"("+this.type+") : "+this.returnT;
        }
    }
}