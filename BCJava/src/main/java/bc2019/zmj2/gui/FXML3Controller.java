package bc2019.zmj2.gui;

import javax.swing.SwingUtilities;

import bc2019.zmj2.client.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FXML3Controller {

    @FXML
    private Label planCoursesBox;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label intialPicture;


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
    private Label usersEmail;
    
    @FXML
    private Label major_info;
    
    @FXML
    private TextField deptBox;

    @FXML
    private TextField codeBox;


    public void updateInfo() {
    	NameInputHerePls.setText(User.getSessionUser().getName());
    	usersEmail.setText(User.getSessionUser().getEmail());
    	MajorHerePls.setText(User.getSessionUser().getMajor());
    	
    }

    @FXML
    private Button addButt;
    
    @FXML
    private Button searchButt;
    
    @FXML
    void searchClick(MouseEvent event) {

    }

    @FXML
    void submitClick(MouseEvent event) {

    }
}
