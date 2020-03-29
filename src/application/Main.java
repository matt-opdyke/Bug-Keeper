package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	private List<String> args;

	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 300;
	private static final String APP_TITLE = "Hello World!";
	BugKeeper bugkeeper = new BugKeeper();
	private static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		args = this.getParameters().getRaw();

		home();

		primaryStage.show();

	}

	private void home() {
		GridPane root = new GridPane();
		Button ret = new Button("Return to the home page");
		ret.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				applyScene(root);
			}
		});

		Button create = new Button("Log a new bug");
		create.setOnAction(c -> create());
		Button read = new Button("View logged bugs");
		read.setOnAction(d -> read());
		Button update = new Button("Update a logged bug");
		update.setOnAction(c -> displayUpdate());
		Button delete = new Button("Resolved a logged bug");
		delete.setOnAction(c -> displayDelete());
		Label welcomeLabel = new Label("Welcome to Bug Keeper! Select one of the following buttons to begin");
		root.addColumn(0, welcomeLabel, create, read, update, delete);

		applyScene(root);

		stage.setTitle("Bug Keeper");

		stage.show();
	}

	private void applyScene(Parent parent) {
		Scene scene = new Scene(parent, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(("GUI.css"));
		stage.setScene(scene);
	}

	public void logNewBug(String bug, String line) {
		bugkeeper.createTicket(bug, line);
	}

	public void create() {
		VBox display = new VBox();
		TextField bugInput = new TextField();
		TextField lineInput = new TextField();
		GridPane gridpane = new GridPane();
		Button ret = new Button("Back to home");
		ret.setOnAction(c -> home());
		gridpane.addRow(0, new Label("Please enter a bug "), bugInput);
		gridpane.addRow(1, new Label("Please enter the line the bug is on (if known)"), lineInput);
		gridpane.add(ret, 1, 2);

		Button addBug = new Button("Log the bug to the system!");
		addBug.setOnAction(c -> logNewBug(bugInput.getText(), lineInput.getText()));
		display.getChildren().addAll(gridpane, addBug);

		applyScene(display);
	}

	public void read() {
		VBox display = new VBox();
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			display.getChildren()
					.add(new Label("You may use the left and right arrows to look through the logged bugs!"));
			int currIndex = 0;
			Ticket currTick = bugkeeper.readTickets().get(currIndex);
			GridPane gridpane = new GridPane();
			ArrayList <Ticket> tickArr = bugkeeper.readTickets();
			
			for (int i = 0; i < bugkeeper.readTickets().size(); i++) {
				gridpane.add(new Label (tickArr.get(i).formatTicket()),i + 1,0);
			}
			
			Label tickLabel = new Label(currTick.formatTicket());
			gridpane.add(tickLabel,0,0);
			display.getChildren().add(gridpane);
		}
		Button ret = new Button("Back to home");
		ret.setOnAction(c -> home());
		display.getChildren().add(ret);
		applyScene(display);

	}

	/**
	 * Handles functionality in the event that the read button is clicked
	 */
	public ArrayList<Ticket> displayRead() {
		ArrayList<Ticket> tickArr = new ArrayList<>();
		tickArr = bugkeeper.readTickets();
		return tickArr;
	}

	public int handleLeftArrow(int index, int upperBound) {
		if (index == 0) {
			return 0;
		} else {
			return index++;
		}
	}

	public void displayUpdate() {

	}

	public void displayDelete() {

	}

	public static void main(String[] args) {
		launch(args);
	}

}
