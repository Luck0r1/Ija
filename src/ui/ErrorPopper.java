package ui;

//Buttons
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.scene.layout.Pane;

public class ErrorPopper implements EventHandler<ActionEvent>{

    private Stage secondaryStage;

    private String message;

    private Button b_submitter;

    public ErrorPopper(String message){

        Scene toShow = this.ErrorPop(message);
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(toShow);
        this.secondaryStage.setAlwaysOnTop(true);
        this.secondaryStage.show();
    }

    private Scene ErrorPop(String message){
        Pane errorWindow = new Pane();
        errorWindow.setPrefWidth(900);
        errorWindow.setPrefHeight(600);

        Label l = new Label("Error encountered");
        l.setFont(new Font(30));
        l.setAlignment(Pos.CENTER);
        l.setPrefHeight(40);
        l.setPrefWidth(900);

        Button b = new Button("Ok");
        b.setPrefWidth(50);
        b.setPrefHeight(30);
        b.setLayoutX(275);
        b.setLayoutY(570);
        b.setOnAction(this);
        this.b_submitter=b;
        
        Label l2 = new Label(message);
        l2.setPrefHeight(530);
        l2.setPrefWidth(880);
        l2.setLayoutX(10);
        l2.setLayoutY(40);

        errorWindow.getChildren().addAll(l,l2,b);

        Scene returner = new Scene(errorWindow);
        return returner;
    }

    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.b_submitter){
            this.secondaryStage.close();
        }
    }
}