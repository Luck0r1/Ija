package classes;

import java.util.ArrayList;
import java.util.List;

//import org.w3c.dom.UserDataHandler;
import classes.*;


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

    public void AddMessage(boolean response,CD_Element func,Actor source,Actor target){
        Message messOfALetter = new Message(response, func, source, target);
        this.loveLetters.add(messOfALetter);
    }

    public void RemoveMessage(Message m){
        this.loveLetters.remove(m);
    }

    public void AddActor(classes.Class toAdd,boolean eternal){
        Actor actor = new Actor(toAdd);
        if(eternal)actor.SetEternal();
        this.cast.add(actor);
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

    public List<Message> FindAssociatedMessages(Actor a){
        List<Message> mList = new ArrayList<Message>();
        for (Message m : this.loveLetters){
            if(m.a1==a || m.a2==a)
                mList.add(m);
        } 
        return mList;
    }

    public void RemoveActorByActor(Actor a){
        List<Message> toRemove = new ArrayList<Message>();
        for(Message m : this.loveLetters){
            if(m.GetA1() == a || m.GetA2() == a)
                toRemove.add(m);
        }
        for(Message m : toRemove)
            this.loveLetters.remove(m);
        
        this.cast.remove(a);
        this.ReId();
    }

    public void RemoveActorByClass(classes.Class c){
        List<Actor> toRemove = new ArrayList<Actor>();
        for(Actor a : this.cast){
            if(a.GetClass()==c)
                toRemove.add(a);

        }
        for(Actor a : toRemove)
            this.RemoveActorByActor(a);
        this.ReId();
    }

    public void RemoveMessageByMessage(Message m){
        this.loveLetters.remove(m);
    }

    public void RemoveMessageByFunction(CD_Element c){
        List<Message> toRemove = new ArrayList<Message>();
        for(Message m : this.loveLetters){
            if(m.function == c)
                toRemove.add(m);
        }
        for(Message m : toRemove)
            this.loveLetters.remove(m);
    }

    public Actor GetAcById(int i){
        for(Actor a : this.cast){
            if(a.GetId()==i)
                return a;
        }
        return null;
    }
}