package bc2019.zmj2.gui;

import java.util.ArrayList;
import java.util.List;

import bc2019.zmj2.client.Course;
import bc2019.zmj2.client.Database;
import bc2019.zmj2.client.PlannedCourse;
import bc2019.zmj2.client.Season;
import bc2019.zmj2.client.User;
import bc2019.zmj2.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FXML3Controller {
	
	
    @FXML
    private TextArea TEXTarea;


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
    
    
    private Course currentCourse;
    
    @FXML
    void searchClick(MouseEvent event) {
    	String dept = deptBox.getText();
    	String codeStr = codeBox.getText();
    	int code = Integer.parseInt(codeStr);
    	Course course = Database.getCourse(dept.trim() + code);
    	currentCourse = course;
    	TEXTarea.setText(course.getDescription());
    	TEXTarea.setDisable(false);
    	TEXTarea.setEditable(false);
    }

    @FXML
    void submitClick(MouseEvent event) {
    	if(currentCourse!=null) {
    		List<PlannedCourse> pl = User.getSessionUser().getPlanned();
    		if(pl == null) {
    			pl = new ArrayList<PlannedCourse>();
    		}
    		pl.add(new PlannedCourse(currentCourse.getDept().toLowerCase() + currentCourse.getNumber(), 2019, Season.FALL, currentCourse));
    		User.getSessionUser().setPlanned(pl);
    		Util.updateUser(User.getSessionUser());
    	}
    }
}
