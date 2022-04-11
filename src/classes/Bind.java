package classes;

import java.util.ArrayList;
import java.util.List;

import classes.Class;

public class Bind{

    private List<Class> classes;
    private String name;

    public Bind(String name){
        this.name = name;
        this.classes = new ArrayList<Class>();
    }

    public String GetName(){
        return this.name;
    }

    public void Class_Add(Class oneClss){
        this.classes.add(oneClss);
    }

    public List<Class> GetClasses(){
        return this.classes;
    }


}