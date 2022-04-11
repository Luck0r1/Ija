
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

    public void Class_Add(String new_clss){
        this.classes.add( new Class(new_clss) );
    }

    public Class Class_Get(String name_clss){
        for(Class clss : this.classes){
            if(clss.GetName() == name_clss)
                return clss;
        }
        return null;
    }

    public void Class_Delete(String rem_clss){
        for(Class clss : this.classes){
            if(clss.GetName() == rem_clss)
                this.classes.remove(clss);
        }
    }

    public void Bind_Add(String new_bind){
        this.bind.add(new Bind(new_bind));
    }

    public Bind Bind_Get(String name_bind){
        for(Bind bind : this.bind){
            if(bind.GetName() == name_bind)
                return bind;
        }
        return null;
    }

    public void Bind_Delete(String rem_bind){
        for(Bind bind : this.bind){
            if(bind.GetName() == rem_bind)
                this.bind.remove(bind);
        }
    }

    public List<Class> GetClasses(){
        return this.classes;
    }

    public List<Bind> GetBinds(){
        return this.bind;
    }
}