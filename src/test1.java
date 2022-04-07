
//My improts
import classes.*;

//FX imports
//Roots
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Buttons
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

//Layouts
import javafx.scene.layout.StackPane;
import javafx.scene.Group; 

//Labels
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.crypto.AEADBadTagException;

import classes.Element;

public class test1 extends Application implements EventHandler<ActionEvent>{

    Stage primaryStage;
    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Dia Builder");
        this.button = new Button("First button");
        this.button.setOnAction(this);
        StackPane layout = new StackPane();
        layout.getChildren().add(this.button);
        Scene scene2 = new Scene(layout,400,450);
        primaryStage.setScene(scene2);
        primaryStage.show();
        this.primaryStage=primaryStage;
        
    }

    public Image getImg(String adress) throws Exception{
        return new Image(new FileInputStream(adress));
    }

    @Override
    public void handle(ActionEvent event){
        if(event.getSource()==this.button){
            Image img;
            try{
                img = getImg("./src/classes/kuk.jpeg");
            }
            catch(Exception e){
                return;
            }
            ImageView imageView = new ImageView(img);
            //Setting the position of the image 
            imageView.setX(0); 
            imageView.setY(0); 
            
            //setting the fit height and width of the image view 
            imageView.setFitHeight(400); 
            imageView.setFitWidth(400); 

            //Setting the preserve ratio of the image view 
            imageView.setPreserveRatio(false);  
        
            //Creating a Group object  
            Group root = new Group(imageView); 
            
            //Creating a scene object 
            Scene scene = new Scene(root, 400, 450,Color.PINK);  
            
            
            //Creating a Label
            Label label = new Label("Harder Daddy");
            Label label2 = new Label("Please dont kill me");
            //Setting font to the label
            Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25);
            Font font2 = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 10);
            label.setFont(font);
            label2.setFont(font2);
            //Filling color to the label
            label.setTextFill(Color.RED);
            label2.setTextFill(Color.BLACK);
            //Setting the position
            label.setTranslateX(100);
            label.setTranslateY(400);
            label2.setTranslateX(150);
            label2.setTranslateY(425);

            root.getChildren().add(label);
            root.getChildren().add(label2);
            //Adding scene to the stage 
            this.primaryStage.setScene(scene);
            
            //Displaying the contents of the stage 
            this.primaryStage.show(); 
        }
    }
}