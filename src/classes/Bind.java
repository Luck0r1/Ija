package classes;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import classes.Class;

/**
 * Class that carries information about single relation in class diagram
 * @author xlukac16
 */
public class Bind{

    //First class of bind
    private Class c1;
    //Second class of bind
    private Class c2;
    //name, given by user
    private String name;
    //multiplicity of class 1
    private String c1C;
    //multiplicity of class 2
    private String c2C;
    //type of bind to class 1
    private int typeL;
    //type of bind to class 2
    private int typeR;
    //list of fields in map, stored for simpler manipulation
    private List<Dimension> dims;

    public Bind(String name,Class c1,Class c2){
        this.name = name;
        this.dims = new ArrayList<Dimension>();
        this.c1=c1;
        this.c2=c2;
        this.c1C=" ";
        this.c2C=" ";

        //0 normal
        //1 generalization
        //2 association
        //3 aggregation
        //4 composition

        this.typeL=0;
        this.typeR=0;
    }

    /**
     * Sets type of bind to class 1
     * @param newType
     */
    public void Type_Set_L(int newType){
        this.typeL = newType;
    }
    /**
     * Sets type of bind to class 2
     * @param newType
     */
    public void Type_Set_R(int newType){
        this.typeR = newType;
    }

    /**
     * Gets type of bind to class 1
     * @return int
     */
    public int Type_Get_L(){
        return this.typeL;
    }
    /**
     * Get type of bind to class 2
     * @return int
     */
    public int Type_Get_R(){
        return this.typeR;
    }

    /**
     * returns class 1 of bind
     * @return Class
     */
    public String Get_C1(){
        return this.c1C;
    }
    /**
     * returns class 2 of bind
     * @return Class
     */
    public String Get_C2(){
        return this.c2C;
    }

    /**
     * Sets class 1 of bind
     * @param in
     */
    public void Set_C1(String in){
        this.c1C = in;
    }
    /**
     * Sets class 2 of bind
     * @param in
     */
    public void Set_C2(String in){
        this.c2C = in;
    }

    /**
     * Gets name of bind
     * @return String
     */
    public String GetName(){
        return this.name;
    }
    /**
     * Sets name of bind
     * @param name
     */
    public void SetName(String name){
        this.name = name;
    }
    /**
     * returns list of dimension used for mapping
     * @return List<Dimension>
     */
    public List<Dimension> GetDims(){
        return this.dims;
    }

    /**
     * Resets the list of dimensions
     */
    public void ResetDims(){
        this.dims = new ArrayList<Dimension>();
    }
    /**
     * Returns class 1 of bind
     * @return Class
     */
    public classes.Class GetClass1(){
        return this.c1;
    }
    /**
     * Returns class 2 of bind
     * @return Class
     */
    public classes.Class GetClass2(){
        return this.c2;
    }
}