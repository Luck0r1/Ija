
package classes;

import classes.Class;
import classes.Bind;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram{

    private String name;
    private List<Class> classes;
    private List<Bind> bind;

    public ClassDiagram(String name){
        this.name=name;
        this.classes = new ArrayList<Class>();
        this.bind = new ArrayList<Bind>();
    }

    public String GetName(){
        return this.name;
    }

    public void ReName(String name){
        this.name=name;
    }

    public Class Class_Add(String new_clss){
        Class newObj = new Class(new_clss);
        newObj.SetId(this.classes.size());
        this.classes.add( newObj );
        return newObj;
    }

    public Class Class_Get_By_Id(int id){
        for (Class C : this.classes){
            if(id==C.GetId()){
                return C;
            }
        }
        return null;
    }

    public int ClassN_Get(String namer){
        int i=0;
        for(Class clss : this.classes){
            if(clss.GetName() == namer)
                return i;
            i++;
        }
        return -1;
    }

    public void Class_Delete(String rem_clss){
        for(Class clss : this.classes){
            if(clss.GetName() == rem_clss)
                this.classes.remove(clss);
        }
    }

    public void Bind_Add(String new_bind,Class c1,Class c2){
        this.bind.add(new Bind(new_bind,c1,c2));
    }

    public void Bind_FAdd(Bind neu){
        this.bind.add(neu);
    }

    public Bind Bind_Get(String name_bind){
        for(Bind bind : this.bind){
            if(bind.GetName() == name_bind)
                return bind;
        }
        return null;
    }

    public void Bind_Delete(Bind rem_bind){
        this.bind.remove(rem_bind);
    }

    public List<Class> GetClasses(){
        return this.classes;
    }

    public List<Bind> GetBinds(){
        return this.bind;
    }
}