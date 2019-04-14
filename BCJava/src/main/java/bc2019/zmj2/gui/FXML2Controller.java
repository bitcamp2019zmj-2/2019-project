package bc2019.zmj2.gui;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;



public class FXML2Controller {
	


    @FXML
    private AnchorPane SignUpPane;

	    @FXML
	    private TextField SignUpVerify_txtbox;

	    @FXML
	    private TextField signUpPass_txtbox;

	    @FXML
	    private TextField signUpEmailAddress_txtbox;

	    @FXML
	    private Button RegisterButton;
	    
	   

	    @FXML
	    void RegisteredUser(ActionEvent event) throws IOException {
	    	URL yeet = getClass().getResource("fxml/firstLoadout.fxml");
	    	AnchorPane pane = FXMLLoader.load(yeet);
	    	SignUpPane.getChildren().setAll(pane);

	    }

	}



