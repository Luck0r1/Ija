package classes;

import java.util.ArrayList;
import java.util.List;

import classes.Class;

public class Bind{

    private List<Class> classes;
    private String name;
    private String c1C;
    private String c2C;

    public Bind(String name,Class c1,Class c2){
        this.name = name;
        this.classes = new ArrayList<Class>();
        this.classes.add(c1);
        this.classes.add(c2);
        this.c1C=" ";
        this.c2C=" ";

    }

    public String Get_C1(){
        return this.c1C;
    }

    public String Get_C2(){
        return this.c2C;
    }

    public void Set_C1(String in){
        this.c1C = in;
    }

    public void Set_C2(String in){
        this.c2C = in;
    }

    public String GetName(){
        return this.name;
    }

    public void SetName(String name){
        this.name = name;
    }

    public List<Class> GetClasses(){
        return this.classes;
    }


}