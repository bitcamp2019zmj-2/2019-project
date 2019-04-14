package bc2019.zmj2.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;


public class MainAppx extends Application {

	private Stage primaryStage;
	private AnchorPane mainLayout;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Bitcamp App");
		// load document 
		showLoginScreen();
		
	}

	
	private void showLoginScreen() throws IOException {
		
		//load doc into main class
		FXMLLoader loader = new FXMLLoader();
		//loaded
		loader.setLocation(MainAppx.class.getResource("fxml/LoginScreen.fxml"));
		// tells to load and == main layout
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		// tells primary stage to put scene inside
		primaryStage.setScene(scene);
		//display
		primaryStage.show();
		
	}
	
	public static void begin(String[] args) {
		launch(args);
	}
	
	
}
