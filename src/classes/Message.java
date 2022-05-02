package classes;

import classes.*;
public class Message{

    boolean response;
    boolean constructor;
    CD_Element function;
    Actor a1;
    Actor a2;
 
    public Message(boolean response, boolean constructor,CD_Element func, Actor a1,Actor a2){
        this.response = response;
        this.constructor = constructor;
        this.function = func;
        this.a1 = a1;
        this.a2 = a2;
    }
}