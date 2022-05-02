
package classes;

public class Actor{

    classes.Class thisClass;
    int id;
    boolean eternal = false;

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

    /**
     * Does not every great artist strive to carve his name among th stars for all eternity?
     */
    public void SetEternal(){
        if(!this.eternal)
            this.eternal = true;
        else
            this.eternal = false;
    }

    public boolean IsEternal(){
        return this.eternal;
    }

}