package classes;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import classes.Class;

public class Bind{

    private List<Class> classes;
    private String name;
    private String c1C;
    private String c2C;
    private int type;
    private List<Dimension> dims;

    public Bind(String name,Class c1,Class c2){
        this.name = name;
        this.classes = new ArrayList<Class>();
        this.dims = new ArrayList<Dimension>();
        this.classes.add(c1);
        this.classes.add(c2);
        this.c1C=" ";
        this.c2C=" ";

        //0 normal
        //1 generalization
        this.type=0;
    }

    public void Type_Set(int newType){
        this.type = newType;
    }

    public int Type_Get(){
        return this.type;
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

    public List<Dimension> GetDims(){
        return this.dims;
    }

    public void ResetDims(){
        this.dims = new ArrayList<Dimension>();
    }

    public classes.Class GetClass1(){
        return this.classes.get(0);
    }

    public classes.Class GetClass2(){
        return this.classes.get(1);
    }
}