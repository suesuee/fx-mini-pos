package com.jdc.pos.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.pos.repo.ItemRepo;
import com.jdc.pos.util.MiniPosException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainFrame implements Initializable{

    @FXML
    private MenuItem uploadMenu;
    
    @FXML
    private Label title;

    @FXML
    private HBox button;

    @FXML
    private SVGPath icon;

    @FXML
    private StackPane content;

    @FXML
    private Label message;
    
    private static Label output;//Null, form loading tat lr yin (look at init);

    private static final String POS = "M20 48c0 2.188-1.813 4-4 4s-4-1.813-4-4 1.813-4 4-4 4 1.813 4 4zM48 48c0 2.188-1.813 4-4 4s-4-1.813-4-4 1.813-4 4-4 4 1.813 4 4zM52 14v16c0 1-0.781 1.875-1.781 2l-32.625 3.812c0.156 0.719 0.406 1.438 0.406 2.188 0 0.719-0.438 1.375-0.75 2h28.75c1.094 0 2 0.906 2 2s-0.906 2-2 2h-32c-1.094 0-2-0.906-2-2 0-0.969 1.406-3.312 1.906-4.281l-5.531-25.719h-6.375c-1.094 0-2-0.906-2-2s0.906-2 2-2h8c2.094 0 2.156 2.5 2.469 4h37.531c1.094 0 2 0.906 2 2z";
    private static final String REPORT = "M43.022 10.738c-1.041-1.42-2.493-3.080-4.086-4.674s-3.254-3.045-4.674-4.086c-2.418-1.773-3.59-1.978-4.262-1.978h-23.25c-2.068 0-3.75 1.682-3.75 3.75v40.5c0 2.068 1.682 3.75 3.75 3.75h34.5c2.068 0 3.75-1.682 3.75-3.75v-29.25c0-0.672-0.205-1.845-1.978-4.262zM36.814 8.186c1.439 1.439 2.569 2.737 3.402 3.814h-7.217v-7.216c1.077 0.833 2.376 1.963 3.814 3.402zM42 44.25c0 0.407-0.343 0.75-0.75 0.75h-34.5c-0.406 0-0.75-0.343-0.75-0.75v-40.5c0-0.406 0.344-0.75 0.75-0.75 0 0 23.248-0 23.25 0v10.5c0 0.828 0.672 1.5 1.5 1.5h10.5v29.25z";
    
    private static EventHandler<MouseEvent> showReport;
    private static EventHandler<MouseEvent> showPos;
    
    private ItemRepo itemRepo; //Interface ko datatype anay nk pay,file uplaod poh;
    private FileChooser chooser; 
    
    @FXML
    void about() {

    }

    @FXML
    void close() {

    }

    @FXML
    void upload() {
    	try {
    		
    		File file = chooser.showOpenDialog(null);
    		
    		//if click cancel
    		if(null == file) {
    			throw new MiniPosException("Please select a text file to upload!!!");
    			
    		}
    		
    		itemRepo.add(file.getAbsolutePath()); //memory mhr shi dk add();
    		loadView("Pos.fxml");
    		
/*    		int size = itemRepo.search(null, null).size();//list.size() nk tutu pl;
    		System.out.println("Uploaded =>"+ size);
*/			
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
  
    
    public static void show() { //show MainFrame User Interface
    	
//		System.out.println("Login success...");
    	
    	try {
    		
    		// load fxml file
			Parent root = FXMLLoader.load(MainFrame.class.getResource("MainFrame.fxml"));
			
			// create window (stage)
			Stage stage = new Stage();//arg anay nk framework ka m souk pay ng loh; main mhr kya launch method ka nay stage obj ko arg anay nk souuk pe htae pay htr loh;
	    	
	    	// set scene object
			Scene scene = new Scene(root);
	    	
	    	// set scene in stage
			stage.setScene(scene);
	    	
	    	//show window (stage)
			stage.show();
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		itemRepo = ItemRepo.getInstance();
		chooser = new FileChooser();
		
		output = message;//same obj ko kyi nay ml;
		
		loadView("Pos.fxml");
		
		//event win lr yin mouse nk click lok lyk yin d hr twae lok pr;
		showReport = event -> {
			icon.setContent(REPORT);
			loadView("Report.fxml");
			title.setText("Sale History");
			button.setOnMouseClicked(showPos);
			uploadMenu.setVisible(false);
		};
		
		showPos = event -> {
			icon.setContent(POS);
			loadView("Pos.fxml");
			title.setText("Mini POS");
			button.setOnMouseClicked(showReport);
			uploadMenu.setVisible(true);
		};
		
		button.setOnMouseClicked(showReport);//Default View
	}
	
	public static void clearOutput() {
		output.setText("");
	}
	
	public static void showOutput(String message) {
		output.setText(message);
	}
	
	//d view mhr pl tone mhr moh private, instance method
	  private void loadView(String fxml) {
	    	
	    	try {
				Parent root = FXMLLoader.load(getClass().getResource(fxml));
				content.getChildren().clear();//ayin shi pe thar old view shin htote;
				content.getChildren().add(root);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

}
