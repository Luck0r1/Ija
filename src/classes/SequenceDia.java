package classes;


public class SequenceDia{
    String name;

    public SequenceDia(String name){
        this.name = name;
    }

    public void SequenceDia_Rename(String newName){
        this.name = newName;
    }

    public String GetName(){
        return this.name;
    }
}