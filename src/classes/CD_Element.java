package classes;

import support.AccesT;
import java.util.*;

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

    public void SetId(int id){
        this.id=id;
    }
    public int GetId(){
        return this.id;
    }

    public String GetName(){
        return this.name;
    }

    public String GetType(){
        return this.type;
    }

    public String GetReturnT(){
        return this.returnT;
    }

    public void SetReturnT(String newT){
        this.returnT=newT;
    }

    public String GetAccess(){
        if(this.acces==AccesT.PUBLIC)
            return "public";
        else if(this.acces == AccesT.PROTECTED)
            return "protected";
        else
            return "private";
    }

    public void SetType(AccesT x){
        this.acces = x;
    }

    public Boolean IsFunc(){
        return this.func;
    }

    public void ReName(String name){
        this.name=name;
    }
    public void ReType(String type){
        this.type = type;
    }

    public void SetFunc(boolean bl){
        if(bl)
            this.func=true;
    }

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