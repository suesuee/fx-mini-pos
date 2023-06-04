package com.jdc.pos;
	
import com.jdc.pos.views.Login;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox)FXMLLoader.load(Login.class.getResource("Login.fxml"));//VM ka I yay dr twae nar m lal pay mk Javac ka nay tasint nar ll ag lok pay htr dk byte code read pe .class file mhr shi dk kg ko read pe alok lok pay dr;
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			css file ko fxml ka sa loading swel ma khaw mhr moh d mhr business pine pl yay chin loh m tone dr pr;			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
