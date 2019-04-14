

package bc2019.zmj2.gui;

import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bc2019.zmj2.util.LoginException;
import bc2019.zmj2.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLController {
	
	// Sign Up box

    @FXML
    private TextField SignUpVerify_txtbox;

    @FXML
    private TextField signUpPass_txtbox;

    @FXML
    private TextField signUpEmailAddress_txtbox;

    @FXML
    private Button SignUpButton;
	
	
	
	
	
	// brainstorm stuff
    
    @FXML
    private AnchorPane BrainStormLoginPane;
	
	@FXML
	private AnchorPane anchorPaneLogin;
	
    @FXML
    private Button orangeSignUpButton;


	@FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_firstName;

    @FXML
    private Button enterButton;

    @FXML
    private ImageView imageLogin;

    @FXML
    void onClick_showName(ActionEvent event) {
    	String username = txt_firstName.getText();
		String password = txt_password.getText()	;
		try {
			Util.login(username, password);
			JOptionPane.showMessageDialog(new JFrame(),
				    "YEEHAW",
				    "Login Success",
				    JOptionPane.ERROR_MESSAGE);
		} catch (LoginException e1) {
			JOptionPane.showMessageDialog(new JFrame(),
				    "Invalid username or password",
				    "Login Error",
				    JOptionPane.ERROR_MESSAGE);
		}
    }
    
    
    @FXML
    void loadSignUpWindow(ActionEvent event) {

    }

    @FXML
    void signUpFXML(MouseEvent event) throws IOException{
        

    }
    
    @FXML
    void clickedSignUp(MouseEvent event) throws IOException{
    	URL yeet = getClass().getResource("fxml/signUpFXML.fxml");
    	//System.out.println(yeet);
    	AnchorPane pane = FXMLLoader.load(yeet);
        //System.out.println("fk lol");
    	BrainStormLoginPane.getChildren().setAll(pane);

    }
    
    
}
