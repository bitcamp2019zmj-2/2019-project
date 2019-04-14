

package bc2019.zmj2.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bc2019.zmj2.util.LoginException;
import bc2019.zmj2.util.Util;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

}
