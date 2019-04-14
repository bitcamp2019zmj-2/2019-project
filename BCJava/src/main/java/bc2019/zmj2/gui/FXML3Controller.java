package bc2019.zmj2.gui;

import javax.swing.SwingUtilities;

import bc2019.zmj2.client.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class FXML3Controller {

    @FXML
    private Label planCoursesBox;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label intialPicture;

    @FXML
    private java.awt.Label usersEmail;

    @FXML
    private Label name_info;

    @FXML
    private Label NameInputHerePls;

    @FXML
    private Label MajorHerePls;

    @FXML
    private Label MajorHerePls1;
    
    @FXML
    private Label email_info;
    
    @FXML
    private Pane course_desc;

    @FXML
    private Label major_info;

    public void updateInfo() {
    	name_info.setText("Name: " + User.getSessionUser().getName());
    	email_info.setText("Email: " + User.getSessionUser().getEmail());
    	major_info.setText("Major: " + User.getSessionUser().getMajor());
    	
    }

}
