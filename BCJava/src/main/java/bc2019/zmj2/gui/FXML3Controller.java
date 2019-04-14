package bc2019.zmj2.gui;

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
    private Label usersEmail;

    @FXML
    private Label NameInputHerePls;

    @FXML
    private Label MajorHerePls;

    @FXML
    private Label MajorHerePls1;
    
    @FXML
    private static Label name_info;
    
    public static void updateInfo() {
    	name_info.setText("Name: " + "FUCK");
    }
    
    

}
