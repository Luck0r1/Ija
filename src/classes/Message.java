package classes;

import classes.*;
/**
 * Class that stores information about single message
 */
public class Message{

    private boolean response;
    private CD_Element function;
    private Actor a1;
    private Actor a2;
    private boolean constructor;
    private String text;
 
    public Message(boolean response, CD_Element func, Actor a1,Actor a2,boolean constructor,String bgnText){
        this.response = response;
        this.function = func;
        this.a1 = a1;
        this.a2 = a2;
        this.text = bgnText;
        this.constructor = constructor;
    }
    /**
     * Gets arguments of message
     * @return
     */
    public String GetText(){
        return this.text;
    }
    /**
     * Sets arguments of messages
     * @param text
     */
    public void SetText(String text){
        this.text = text;
    }
    /**
     * Returns if message is constructor
     * @return
     */
    public boolean IsConstructor(){
        return this.constructor;
    }
    /**
     * Returns the first actor involved
     * @return
     */
    public Actor GetA1(){
        return this.a1;
    }
    /**
     * Returns id of first actor involved
     * @return
     */
    public int GetA1Id(){
        if(this.a1==null)
            return -1;
        else
            return this.a1.GetId();
    }
    /**
     * Returns second actor involved
     * @return
     */
    public Actor GetA2(){
        return this.a2;
    }
    /**
     * Returns id of second actor involved
     * @return
     */
    public int GetA2Id(){
        if(this.a2==null)
            return -1;
        else
            return this.a2.GetId();
    }
    /**
     * Checks if message is response
     * @return
     */
    public boolean GetResponse(){
        return this.response;
    }

    /**
     * Gets function of message
     * @return
     */
    public CD_Element GetFunc(){
        return this.function;
    }
    /**
     * Gets id of function
     * @return
     */
    public int GetFuncId(){
        if(this.function==null){
            return-1;
        }
        else
            return this.function.GetId();
    }
    /**
     * Gets name of funcion
     * @return
     */
    public String GetName(){
        if(this.function==null)
            return "acknowledgment";
        else
            return this.function.GetName();
    }
    /**
     * Gets visuals of message (text in button on screen)
     * @return
     */
    public String GetVisuals(){
        if(this.response)return(" ");
        else if(this.constructor)return("<<create>>("+this.text+")");
        else return this.GetName()+"("+this.text+")";
    }
}