
package classes;

import java.util.*;

public class CD_Element{

    private String name;
    private String type; 
    private AccesT acces;
    private boolean func = false;

    public CD_Element(String name){
        this.name=name;
        this.type="";
        this.acces=AccesT.PUBLIC;
    }

    public String GetName(){
        return this.name;
    }

    public String GetType(){
        return this.type;
    }

    public String GetAccess(){
        if(this.acces==AccesT.PUBLIC)
            return "0";
        else if(this.acces == AccesT.PROTECTED)
            return "1";
        else
            return "2";
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
        else
            return this.RetAcces()+" "+this.name+"("+this.type+")";
    }
}

enum AccesT{
    PUBLIC,
    PRIVATE,
    PROTECTED
}