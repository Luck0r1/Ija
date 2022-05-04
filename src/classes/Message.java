package classes;

import classes.*;
public class Message{

    boolean response;
    CD_Element function;
    Actor a1;
    Actor a2;
 
    public Message(boolean response, CD_Element func, Actor a1,Actor a2){
        this.response = response;
        this.function = func;
        this.a1 = a1;
        this.a2 = a2;
    }

    public Actor GetA1(){
        return this.a1;
    }
    public int GetA1Id(){
        if(this.a1==null)
            return -1;
        else
            return this.a1.GetId();
    }
    public Actor GetA2(){
        return this.a2;
    }
    public int GetA2Id(){
        if(this.a2==null)
            return -1;
        else
            return this.a2.GetId();
    }
    public boolean GetResponse(){
        return this.response;
    }

    public CD_Element GetFunc(){
        return this.function;
    }

    public int GetFuncId(){
        if(this.function==null){
            return-1;
        }
        else
            return this.function.GetId();
    }

    public String GetName(){
        if(this.function==null)
            return "acknowledgment";
        else
            return this.function.GetName();
    }
}