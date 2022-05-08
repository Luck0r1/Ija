package classes;

import java.util.ArrayList;
import java.util.List;

//import org.w3c.dom.UserDataHandler;
import classes.*;

/**
 * This class has all information about single sequence diagram
 * @author xmacej03
 */
public class SequenceDia{
    private String name;

    private List<Actor> cast; //HAHA like in the movie
    private List<Message> loveLetters; //HAHA im funny now;
    private ClassDiagram cd;
    public SequenceDia(String name,ClassDiagram curC){
        this.name = name;
        this.cd = curC;
        this.cast = new ArrayList<Actor>();
        this.loveLetters = new ArrayList<Message>();
    }

    private void GetClassFunctionsRec(classes.Class c,List<classes.Class>workedList){
        List<Bind> assocBind = this.cd.GetAssociatedClasses(c);
        for(Bind b : assocBind){
            if((b.Type_Get_R()==1 && b.GetClass1()==c)){
                if(workedList.indexOf(b.GetClass2())==-1){
                    workedList.add(b.GetClass2());
                    this.GetClassFunctionsRec(b.GetClass2(), workedList);
                }
            }
            else
            if(b.Type_Get_L()==1 && b.GetClass2()==c){
                if(workedList.indexOf(b.GetClass1())==-1){
                    workedList.add(b.GetClass1());
                    this.GetClassFunctionsRec(b.GetClass1(), workedList);
                }
            }
        }
    }

    public List<CD_Element> GetPossibleFunctions(classes.Class c){
        List<classes.Class> workedClasses = new ArrayList<classes.Class>();
        List<CD_Element> returner = new ArrayList<CD_Element>();
        workedClasses.add(c);
        this.GetClassFunctionsRec(c, workedClasses);
        for (classes.Class clas : workedClasses){
            for(CD_Element elem : clas.GetFunca())
                returner.add(elem);
        }
        return returner;
    }

    /**
     * Renames diagram
     * @param newName
     */
    public void SequenceDia_Rename(String newName){
        this.name = newName;
    }
    /**
     * Gets name of diagram
     * @return
     */
    public String GetName(){
        return this.name;
    }
    /**
     * Adds message to diagran
     * @param response
     * @param func
     * @param source if null its user
     * @param target if null its user
     * @param constructor
     * @param data
     */
    public void AddMessage(boolean response,CD_Element func,Actor source,Actor target,boolean constructor,String data){
        Message messOfALetter = new Message(response, func, source, target,constructor,data);
        this.loveLetters.add(messOfALetter);
    }
    /**
     * Removes message from diagram
     * @param m
     */
    public void RemoveMessage(Message m){
        this.loveLetters.remove(m);
    }
    /**
     * Adds actor to sequence diagram
     * @param toAdd
     * @param eternal
     * @return
     */
    public Actor AddActor(classes.Class toAdd,boolean eternal){
        Actor actor = new Actor(toAdd);
        if(eternal)actor.SetEternal();
        this.cast.add(actor);
        this.ReId();
        return actor;
    }
    /**
     * Gets list of active actors
     * @return
     */
    public List<Actor> GetCast(){
        return this.cast;
    }
    /**
     * Gets all saved messages
     * @return
     */
    public List<Message> GetMessages(){
        return this.loveLetters;
    }
    /**
     * Resets all instance ides after removing class
     */
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
    /**
     * Finds messages associated with deleted class
     * @param a
     * @return
     */
    public List<Message> FindAssociatedMessages(Actor a){
        List<Message> mList = new ArrayList<Message>();
        for (Message m : this.loveLetters){
            if(m.GetA1()==a || m.GetA2()==a)
                mList.add(m);
        } 
        return mList;
    }
    /**
     * Removes instance in sequence diagram
     * @param a
     */
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
    /**
     * Removes all instance of class after removing of class
     * @param c
     */
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

    /**
     * Removes single message
     * @param m
     */
    public void RemoveMessageByMessage(Message m){
        this.loveLetters.remove(m);
    }
    /**
     * Removes all messages that implement certain function
     * @param c
     */
    public void RemoveMessageByFunction(CD_Element c){
        List<Message> toRemove = new ArrayList<Message>();
        for(Message m : this.loveLetters){
            if(m.GetFunc() == c)
                toRemove.add(m);
        }
        for(Message m : toRemove)
            this.loveLetters.remove(m);
    }

    /**
     * Gets actor by id
     * @param i
     * @return
     */
    public Actor GetAcById(int i){
        for(Actor a : this.cast){
            if(a.GetId()==i)
                return a;
        }
        return null;
    }

    /**
     * Gets first appearance in message
     * @param a
     * @return
     */
    public int GetFirstAppearance(Actor a){
        int i= 0;
        for(Message m : this.loveLetters){
            if(m.GetA2()==a)break;
            i++;
        }
        if(i==this.loveLetters.size())return-1;
        else return i;
    }
    /**
     * Repairs diagram after deletion
     */
    public void RepairDia(){
        List<Actor> toRemove = new ArrayList<Actor>();
        for(Actor a : this.GetCast()){
            if(a.IsEternal() )continue;
            if(!this.cd.GetClasses().contains(a.GetClass())){
                toRemove.add(a);
                continue;
            }
            
            if(GetFirstAppearance(a)==-1 || (!this.loveLetters.get(GetFirstAppearance(a)).IsConstructor()) )
                toRemove.add(a);
        }
        for(Actor a : toRemove){
            this.RemoveActorByActor(a);
        }
    }
}