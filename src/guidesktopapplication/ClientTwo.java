/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package guidesktopapplication;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author moham
 */
public class ClientTwo extends Application {
TextArea textArea;
Label chatLabel ;
TextField inputField;
Button btn ;


    Socket sc;
    DataInputStream dIn;
    PrintStream dOut;


    @Override
    public void start(Stage primaryStage) {
               MenuBar bar = new MenuBar();

        textArea=new TextArea("Chat Messages: \n");
        textArea.setEditable(false);
        chatLabel = new Label("Enter Your Message : ");
        inputField = new TextField();
        btn = new Button();
        btn.setText("Send");


/********************************************* File Menu *************************************************/

        Menu file= new Menu("File");
       
        MenuItem newItem = new MenuItem("New");
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        MenuItem openItem = new MenuItem("Open...");
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+e"));

        file.getItems().addAll(newItem,openItem,saveItem,separator,exitItem);
 
        bar.getMenus().add(file);
/*********************************************** Events ****************************************************/
          
            /******************************* New Items  ***********************************/
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            
                           @Override
                          public  void handle(ActionEvent event)
                          {
                                      if(textArea.getText().trim().length() !=0)
                                      {
                                                   FileChooser fileChooser = new FileChooser();

                                                   //Set extension filter for text files
                                                   FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                                                   fileChooser.getExtensionFilters().add(extFilter);

                                                   //Show save file dialog
                                                    try{
                                                                                                 //Show save file dialog
                                                    File file2 = fileChooser.showSaveDialog(primaryStage);
                                                    FileWriter fw = new FileWriter(file2);
                                                    fw.write(textArea.getText());
                                                    fw.close();
                                                        } 
                                               catch (IOException ex) {
                                                                    Logger.getLogger(GUIDesktopApplication.class.getName()).log(Level.SEVERE, null, ex);
                                                       }

                                                  textArea.clear();
                                      }
                          }
                });

   
            /******************************* Open Items  ***********************************/
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            
                           @Override
                          public  void handle(ActionEvent event)
                          {
                                      FileChooser fileChooser = new FileChooser();
                                      fileChooser.setTitle("Open Resource File");
                                      fileChooser.getExtensionFilters().addAll(
                                              new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                                              new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                                              new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                                              new FileChooser.ExtensionFilter("All Files", "*.*"));
                                      File selectedFile = fileChooser.showOpenDialog(primaryStage);

                             if(selectedFile != null) 
                                  {
                                      String   path = selectedFile.getPath();
                                    try{
                                                     DataInputStream  dataIn = new DataInputStream(new FileInputStream(path));
                                                      byte[] data = new  byte[(int)selectedFile.length()];
                                                      dataIn.read(data);
//                                                      textArea.setText(new String(data));
                                                      textArea.appendText(new String(data));
                                                     dataIn.close();
                                          } 
                                              catch (IOException ex) {    
                                              Logger.getLogger(GUIDesktopApplication.class.getName()).log(Level.SEVERE, null, ex);
                                          }    
                                }
                          }
                });

            /******************************* Save Items  ***********************************/
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            
                           @Override
                          public  void handle(ActionEvent event)
                          {
                                                   FileChooser fileChooser = new FileChooser();

                                                   //Set extension filter for text files
                                                   FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                                                   fileChooser.getExtensionFilters().add(extFilter);

                                                try{
                                                                                                 //Show save file dialog
                                                    File file2 = fileChooser.showSaveDialog(primaryStage);
                                                    FileWriter fw = new FileWriter(file2);
                                                    fw.write(textArea.getText());
                                                    fw.close();
                                                        } 
                                               catch (IOException ex) {
                                                                    Logger.getLogger(GUIDesktopApplication.class.getName()).log(Level.SEVERE, null, ex);
                                                       }
                            }
                });

           /******************************* Exit Event  ***********************************/
            exitItem.setOnAction(new EventHandler<ActionEvent>() {
            
                           @Override
                          public  void handle(ActionEvent event)
                          {
                                     Platform.exit();
                          }
                });


/**************************************************** Button Action *******************************************/

        btn.setOnAction(new EventHandler<ActionEvent>() {    
            @Override
            public void handle(ActionEvent event) {
                dOut.println(inputField.getText());
                inputField.setText("");
            }
        });

/*********************************************** Pane *********************************/
        FlowPane fpane =new FlowPane();
        fpane.getChildren().addAll(chatLabel,inputField,btn);
        fpane.setHgap(10);
        BorderPane root = new BorderPane();
        root.setTop(bar);
        root.setCenter(textArea);
        root.setBottom(fpane);
        Scene scene = new Scene(root, 420, 700);
   
/******************************************* Stage ************************************/

        primaryStage.setTitle("Chat Room");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

  @Override
    public void init() throws IOException{

 try{
            sc = new Socket("127.0.0.1", 5005);
            dIn = new DataInputStream(sc.getInputStream());
            dOut = new PrintStream(sc.getOutputStream());
        } catch (IOException e) {
            sc.close();
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String reply = dIn.readLine();
                        textArea.appendText(reply+"\n");
                        System.err.println(reply);
                    } catch (IOException e) {
                    }
                }
            }
        });
        th.start();

    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
