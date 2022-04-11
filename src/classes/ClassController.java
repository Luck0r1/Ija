
package classes;

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

import classes.Class;
import classes.MainC;

public class ClassController implements EventHandler<ActionEvent>{

    public Button deleteClass;
    public Button renameClass;
    public List<Button> edditArgButts;
    public List<Button> edditFuncButts;
    public List<Button> removeArgs;
    public List<Button> removeFunc;

    public List<String> args;
    public List<String> funcs;
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

    public VBox GetDrawn(){
        return this.drawnClass;
    }

    private VBox DrawClass(){

        this.args = new ArrayList<String>();
        this.funcs = new ArrayList<String>();
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
            this.args.add(elem.GetName());
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
            this.funcs.add(elem.GetName());
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

        newClass.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

        return newClass;
    }

    public void Refresh(){
        this.main.Refresh();
    }

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
                ElemPopper pop = new ElemPopper(this.main, this,this.curC, this.curC.Get_Element(this.args.get(i)));

                this.main.Refresh();
                return;
            }
            i++;
        }
        i=0;
        for(Button butt : this.edditFuncButts){
            if(event.getSource()==butt){
                ElemPopper pop = new ElemPopper(this.main, this,this.curC, this.curC.Get_Element(this.funcs.get(i)));
                

                this.main.Refresh();
                return;
            }
            i++;
        }
        i=0;

        for(Button butt : this.removeArgs){
            if(event.getSource()==butt){
                this.curC.remElement(this.args.get(i));
                this.main.Refresh();
                return;
            }
            i++;
        }
        i=0;
        
        for(Button butt : this.removeFunc){
            if(event.getSource()==butt){
                this.curC.remFunction(this.funcs.get(i));
                this.main.Refresh();
                return;
            }
            i++;
        }

    }

    class Delta { double x, y; }
}