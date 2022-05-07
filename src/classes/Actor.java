
package classes;
/**
 * This class handles single instance in sequence diagram
 */
public class Actor{

    private classes.Class thisClass;
    private int id;
    private boolean eternal = false;

    public Actor(classes.Class type){
        this.thisClass=type;
    }
    /**
     * Gets name
     * @return
     */
    public String GetName(){
        return this.thisClass.GetName()+" : "+Integer.toString(id);
    }
    /**
     * Gets class
     * @return
     */
    public classes.Class GetClass(){
        return this.thisClass;
    }
    /**
     * Sets id
     * @param newId
     */
    public void reId(int newId){
        this.id=newId;
    }
    /**
     * Gets id
     * @return
     */
    public int GetId(){
        return this.id;
    }

    /**
     * Sets class as eternal
     * Does not every great artist strive to carve his name among the stars for all eternity?
     */
    public void SetEternal(){
        if(!this.eternal)
            this.eternal = true;
        else
            this.eternal = false;
    }

    /**
     * Returns bool if class is eternal
     * @return
     */
    public boolean IsEternal(){
        return this.eternal;
    }


}