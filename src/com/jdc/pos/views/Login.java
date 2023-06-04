package com.jdc.pos.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.pos.util.CheckLogin;
import com.jdc.pos.util.PassEncryptor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class Login implements Initializable {

    @FXML
    private Label message;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

/*    public Login() {
    	System.out.println("Message obj : "+ message); Null Htwt
    }
*/
    @FXML
    void cancel() {
//    	Platform.exit(); JavaFX, Java, obj twae akone pyouk b
    	message.getScene().getWindow().hide();
    }

    @FXML
    void login() {
    	
    	try {
			//Check loginId & password
			CheckLogin.login(login.getText(), PassEncryptor.encrypt(password.getText()));
			
			//Show MainFrame
			MainFrame.show();
			
			//Hide login form
			cancel();
			
		} catch (Exception e) {
//			e.printStackTrace();//Developer kyi loh ya ag log ko htote kyi dr
			//Exception obj yk 2 pine salone pr dl, impact fik twr dk code, fik pwar ya dk reasons
			message.setText(e.getMessage());//User bk 
		}
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		message.setText("");
//		System.out.println("Message obj (init) : "+ message);//Obj souk pe tot ma 
		password.setOnKeyPressed(event -> {
			if(event.getCode().equals(KeyCode.ENTER)) {
				login();
			}
		});
	}

}
