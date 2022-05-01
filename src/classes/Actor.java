
package classes;

public class Actor{

    classes.Class thisClass;
    int id;

    public Actor(classes.Class type){
        this.thisClass=type;
    }

    public String GetName(){
        return this.thisClass.GetName()+" : "+Integer.toString(id);
    }

    public classes.Class GetClass(){
        return this.thisClass;
    }

    public void reId(int newId){
        this.id=newId;
    }


}