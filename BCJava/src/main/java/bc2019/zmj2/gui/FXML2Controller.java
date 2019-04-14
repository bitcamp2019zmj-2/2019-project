package bc2019.zmj2.gui;

import java.io.IOException;
import java.net.URL;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;
import com.google.gson.JsonObject;

import bc2019.zmj2.client.Database;
import bc2019.zmj2.util.SignupException;
import bc2019.zmj2.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
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
	    
	    
	  //COURSE + INFO PANE
	    // ALLOW THE NAME AND MAJOR AND EMAIL TO BE SET INTO INFO PANE
	    // THERE IS A DESCRIPTION BOX BUT THAT'S FOR LATER IG IF YOU WANT
	    // MEH... 
	    

	    @FXML
	    private Label usersEmail;

	    @FXML
	    private Label NameInputHerePls;

	    @FXML
	    private Label MajorHerePls;
	    
	    @FXML
	    private Label UserDescriptionBoxPls;

	


	    @FXML
	    void RegisteredUser(ActionEvent event) throws IOException {
	    	String username = signUpEmailAddress_txtbox.getText();
	    	String pswd = signUpPass_txtbox.getText();
	    	String vrfy = SignUpVerify_txtbox.getText();
	    	if(!username.equals("") && pswd.equals(vrfy)) {
	    		try {
					Util.signup(username, pswd);
			    	URL yeet = getClass().getResource("fxml/firstLoadout.fxml");
			    	TabPane pane = FXMLLoader.load(yeet);
			    	SignUpPane.getChildren().setAll(pane);
				} catch (SignupException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    



}
	    
	    

	    


