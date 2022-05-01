package classes;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.UserDataHandler;

public class SequenceDia{
    private String name;

    private List<Actor> cast; //HAHA like in the movie
    private List<Message> loveLetters; //HAHA im funny now;

    public SequenceDia(String name){
        this.name = name;
        this.cast = new ArrayList<Actor>();
        this.loveLetters = new ArrayList<Message>();
    }

    public void SequenceDia_Rename(String newName){
        this.name = newName;
    }

    public String GetName(){
        return this.name;
    }

    public void AddMessage(){

    }

    public void RemoveMessage(Message m){
        this.loveLetters.remove(m);
    }

    public void AddActor(classes.Class toAdd){
        Actor actor = new Actor(toAdd);
        this.cast.add(actor);
        this.ReId();
    }

    public void RemoveActor(Actor a){
        this.cast.remove(a);
        this.ReId();
    }

    public List<Actor> GetCast(){
        return this.cast;
    }

    public List<Message> GetMessages(){
        return this.loveLetters;
    }

    public void ReId(){
        List<classes.Class> used = new ArrayList<classes.Class>();
        List<Integer> count = new ArrayList<Integer>();
        for(Actor a : this.cast){
            int pos = used.indexOf(a.GetClass());
            if(pos == -1){
                used.add(a.GetClass());
                count.add(1);
                a.reId(1);
            }else{
                count.set(pos, count.get(pos)+1);
                a.reId(count.get(pos));
            }
        }
    }
}