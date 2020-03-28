package application;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application{
	private List<String> args;
	
	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_TITLE = "Hello World!";
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		args = this.getParameters().getRaw();
		
		VBox root = new VBox();
		root.setSpacing(10);

	    Button create = new Button("Log a new bug");
	    Button read = new Button("View logged bugs");
	    Button update = new Button("Update a logged bug");
	    Button delete = new Button("Resolved a logged bug");
	    
	    Label welcomeLabel = new Label ("Welcome to Bug Keeper! Select one of the following buttons to begin");

	    root.getChildren().addAll(welcomeLabel, create, read, update, delete);
		
		Scene scene = new Scene (root,700, 300);
		scene.getStylesheets().add("GUI.css");
		stage.setScene(scene);
		
		stage.setTitle("Bug Keeper");
		
		stage.show();
		
	}
	
	public static void main(String[] args){
	    launch(args);
	}
	
}
