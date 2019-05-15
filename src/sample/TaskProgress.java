package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.concurrent.*;

public class TaskProgress extends Application {

    // Declare components at class level scope

    Label lblProgress;

    ProgressBar progBar;

    Button btnStart;

    Button btnCancel;


    @Override
    public void init(){

        // Instantiate and methodise within the init method

        lblProgress = new Label("Progress:");

        progBar = new ProgressBar(0); // Progress Bar goes from 0-1
        progBar.progressProperty().bind(myTask.progressProperty()); // So here I'm binding the progress bar to the progress
        // of myTask - I need to inititate that task as a thread on clicking the start button

        btnStart = new Button("Start Task");
        btnStart.setPrefWidth(100);
        btnStart.setOnAction(actionEvent -> startTask());


        btnCancel = new Button("Cancel Task");
        btnCancel.setPrefWidth(100);
        btnCancel.setDisable(true);


    } // end of init



    Task myTask = new Task<Void>() {

        @Override
            public Void call() {   // the Void means it returns null
                int maximum = 200000000;
                for (int x = 0; x<=maximum; x++){

                    if (isCancelled()){
                        break;
                    }

                    updateProgress(x,maximum);
                }
                return null;
            } // end of call() method - the call method always returns that


    }; // end of myTask

    public void startTask(){

        btnCancel.setDisable(false);

        Thread myThread = new Thread(myTask);
        myThread.setDaemon(true);
        myThread.start();


        btnCancel.setOnAction(actionEvent -> {

            myTask.cancel();
            startTask();

        });

    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a stage

        primaryStage = new Stage();
        primaryStage.setTitle("Task Progress");
        primaryStage.setWidth(500);
        primaryStage.setHeight(200);
        primaryStage.setResizable(false);


        // Design a layout - will a single vertical box do?

        VBox vb = new VBox();

        progBar.setPrefWidth(450);
        vb.getChildren().addAll(lblProgress, progBar, btnStart, btnCancel);
        vb.setPadding(new Insets(20));
        vb.setSpacing(10);

        // Create a scene

        Scene sc = new Scene (vb);

        // Set the scene on the stage

        primaryStage.setScene(sc);

        // Action - show the stage

        primaryStage.show();




    } // end of primaryStage









    public static void main(String args[]){

        launch(args);


    }



    //To quit - action event on cancel task
    public void exit(){
        Platform.exit();
    }




        }