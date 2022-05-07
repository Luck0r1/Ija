
package classes;

import classes.Class;
import classes.Bind;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about whole class diagram
 */
public class ClassDiagram{

    private String name;
    private List<Class> classes;
    private List<Bind> bind;
    private List<SequenceDia> sd;

    public ClassDiagram(String name){
        this.name=name;
        this.classes = new ArrayList<Class>();
        this.bind = new ArrayList<Bind>();
        this.sd = new ArrayList<SequenceDia>();
    }
    /**
     * Gets name of class diagram
     * @return
     */
    public String GetName(){
        return this.name;
    }
    /**
     * Sets name of class diagram
     * @param name
     */
    public void ReName(String name){
        this.name=name;
    }
    /**
     * Adds class to diagram
     * @param new_clss
     * @return
     */
    public Class Class_Add(String new_clss){
        Class newObj = new Class(new_clss,this);
        newObj.SetId(this.classes.size());
        this.classes.add( newObj );
        return newObj;
    }
    /**
     * Returns class by id
     * @param id
     * @return
     */
    public Class Class_Get_By_Id(int id){
        for (Class C : this.classes){
            if(id==C.GetId()){
                return C;
            }
        }
        return null;
    }

    /**
     * Returns element by id
     * @param id
     * @return
     */
    public CD_Element Element_Get_By_Id(int id){
        for(classes.Class c : this.classes){
            for(CD_Element elem:c.GetElements()){
                if(elem.GetId()==id)
                    return elem;
            }
            for(CD_Element elem: c.GetFunca()){
                if(elem.GetId()==id)
                    return elem;
            }
        }

        return null;
    }
    /**
     * Deletes class in diagram
     * @param c
     */
    public void Class_Delete(classes.Class c){
        List<Bind> toRemove = this.GetAssociatedClasses(c);
        for(Bind b : toRemove){
            this.bind.remove(b);
        }
        this.classes.remove(c);
        this.ReId();
    }
    /**
     * Adds relation to diagram
     * @param new_bind
     * @param c1
     * @param c2
     */
    public void Bind_Add(String new_bind,Class c1,Class c2){
        this.bind.add(new Bind(new_bind,c1,c2));
    }
    /**
     * Adds already created relation to diagram
     * @param neu
     */
    public void Bind_FAdd(Bind neu){
        this.bind.add(neu);
    }
    /**
     * Gets relation by name
     * @param name_bind
     * @return
     */
    public Bind Bind_Get(String name_bind){
        for(Bind bind : this.bind){
            if(bind.GetName() == name_bind)
                return bind;
        }
        return null;
    }
    /**
     * Deletes relation in diagram
     * @param rem_bind
     */
    public void Bind_Delete(Bind rem_bind){
        this.bind.remove(rem_bind);
    }
    /**
     * Adds new sequence diagram
     * @param name
     */
    public void SequenceDia_Add(String name){
        this.sd.add(new SequenceDia(name,this));
    }
    /**
     * Adss new diagram by diagram
     * @param newD
     */
    public void SequenceDia_FAdd(SequenceDia newD){
        this.sd.add(newD);
    }
    /**
     * Removes diagram
     * @param sd
     */
    public void SequenceDia_Remove(SequenceDia sd){
        this.sd.remove(sd);
    }
    /**
     * Gets classes
     * @return
     */
    public List<Class> GetClasses(){
        return this.classes;
    }
    /**
     * Gets relations in diagram
     * @return
     */
    public List<Bind> GetBinds(){
        return this.bind;
    }
    /**
     * Gets sequence diagrams
     * @return
     */
    public List<SequenceDia> GetSeqD(){
        return this.sd;
    }
    /**
     * Gets binds associated with class
     * @param c
     * @return
     */
    public List<Bind> GetAssociatedClasses(classes.Class c){
        List<Bind> returner = new ArrayList<Bind>();
        for(Bind b : this.bind){
            if(b.GetClass1()==c || b.GetClass2()==c)returner.add(b);
        }
        return returner;
    }

    /**
     * Re ids all class and its elements
     */
    public void ReId(){
        for(int i = 0;i<this.GetClasses().size();i++){
            this.classes.get(i).SetId(i);
        }
        int j=0;
        for(classes.Class c : this.classes){
            for(CD_Element cd : c.GetElements()){
                cd.SetId(j);
                j++;
            }
            for(CD_Element cd : c.GetFunca()){
                cd.SetId(j);
                j++;
            }
        }
    }

}