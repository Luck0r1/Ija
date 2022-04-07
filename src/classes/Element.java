
package classes;

public class Element{

    private String name;
    private String type;
    private AccesT acces;

    public Element(String name){
        this.name=name;
    }

    public String GetName(){
        return name;
    }

    public void ReName(String name){
        this.name=name;
    }
}

enum AccesT{
    PUBLIC,
    PRIVATE,
    PROTECTED
}