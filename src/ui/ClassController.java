
package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.*;

import javax.swing.ButtonGroup;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import classes.CD_Element;
import classes.Class;
import classes.MainC;
import support.Delta;

/**
 * This class handles inner setting of class diagram
 */
public class ClassController implements EventHandler<ActionEvent>{

    public Button deleteClass;
    public Button renameClass;
    public List<Button> edditArgButts;
    public List<Button> edditFuncButts;
    public List<Button> removeArgs;
    public List<Button> removeFunc;

    public Button newArg;
    public Button newFunc;

    public Class curC;
    public VBox drawnClass;

    public MainC main;

    public ClassController(Class target,MainC main){
        this.curC = target;
        this.drawnClass = this.DrawClass();
        this.main = main;
    }

    /**
     * Returns graphical interface
     * @return
     */
    public VBox GetDrawn(){
        return this.drawnClass;
    }
    /**
     * Draws grpahical representation class
     * @return
     */
    private VBox DrawClass(){

        this.edditArgButts = new ArrayList<Button>();
        this.edditFuncButts = new ArrayList<Button>();
        this.removeArgs = new ArrayList<Button>();
        this.removeFunc = new ArrayList<Button>();
        
        Class curClass = this.curC;
        VBox newClass = new VBox();
        Button c_name = new Button(curClass.GetName());
        this.renameClass = c_name;
        c_name.setOnAction(this);
        Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25);
        c_name.setFont(font);
        
        Label offset = new Label("--------------");
        Font font2 = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 5);
        offset.setFont(font2);

        newClass.getChildren().add(c_name);
        newClass.getChildren().add(offset);
        int i=0;
        for(CD_Element elem : curClass.GetElements()){
            HBox listElem = new HBox();

            Button newEditButt = new Button(elem.ToString());

            Button newDeleteButt = new Button("Remove");
            newEditButt.setOnAction(this);
            newDeleteButt.setOnAction(this);
            listElem.getChildren().add(newEditButt);
            listElem.getChildren().add(newDeleteButt);
            this.removeArgs.add(newDeleteButt);
            this.edditArgButts.add(newEditButt);
            newClass.getChildren().add(listElem);
            i++;
        }

        Label offset2 = new Label("--------------");
        offset.setFont(font2);
        newClass.getChildren().add(offset2);
        i=0;
        for(CD_Element elem : curClass.GetFunca()){
            HBox listElem = new HBox();
            
            Button newEditButt = new Button(elem.ToString());

            Button newDeleteButt = new Button("Remove");
            newDeleteButt.setOnAction(this);
            newEditButt.setOnAction(this);
            listElem.getChildren().add(newEditButt);
            listElem.getChildren().add(newDeleteButt);
            this.removeFunc.add(newDeleteButt);
            this.edditFuncButts.add(newEditButt);

            newClass.getChildren().add(listElem);
            i++;
        }

        HBox butts = new HBox();
        Button addElem = new Button("Add E");
        addElem.setOnAction(this);
        Button addFunc = new Button("Add F");
        addFunc.setOnAction(this);
        this.newArg = addElem;
        this.newFunc= addFunc;
        butts.getChildren().add(addElem);
        butts.getChildren().add(addFunc);
        newClass.getChildren().add(butts);

        newClass.setStyle("-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;"
                            + "-fx-border-color: blue;");

        return newClass;
    }
    /**
     * Refreshes class
     */
    public void Refresh(){
        this.main.Refresh();
    }
    /**
     * handles buttons
     * @param event
     */
    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.newArg){
            this.curC.addElement("new_elem");
            this.main.Refresh();
            return;
        }
        else if(event.getSource() == this.newFunc){
            this.curC.addFunc("new_func");
            this.main.Refresh();
            return;
        }
        else if(event.getSource()==this.renameClass){
            CPopper newPop = new CPopper(this.main,this,this.curC);
        }
        int i = 0;
        for(Button butt : this.edditArgButts){
            if(event.getSource()==butt){
                CD_Element editElem = this.curC.GetElements().get(i);
                ElemPopper pop = new ElemPopper(this.main, this,this.curC, editElem);

                this.main.Refresh();
                return;
            }
            i++;
        }
        i=0;
        for(Button butt : this.edditFuncButts){
            if(event.getSource()==butt){
                CD_Element editFunc = this.curC.GetFunca().get(i);
                ElemPopper pop = new ElemPopper(this.main, this,this.curC, editFunc);
                

                this.main.Refresh();
                return;
            }
            i++;
        }
        i=0;

        for(Button butt : this.removeArgs){
            if(event.getSource()==butt){
                CD_Element editElem = this.curC.GetElements().get(i);
                this.curC.remElement(editElem);
                this.main.Refresh();
                return;
            }
            i++;
        }
        i=0;
        
        for(Button butt : this.removeFunc){
            if(event.getSource()==butt){
                CD_Element editFunc = this.curC.GetFunca().get(i);
                this.curC.remFunction(editFunc);
                this.main.Refresh();
                return;
            }
            i++;
        }

    }
}