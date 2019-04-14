

package bc2019.zmj2.gui;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLController {

	@FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_firstName;

    @FXML
    private Button enterButton;

    @FXML
    private ImageView imageLogin;
    Image image1 = new Image(MainAppx.class.getResourceAsStream("lights.png"));

    @FXML
    void onClick_showName(ActionEvent event) {

    }

}
