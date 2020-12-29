package application;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
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

/**
 * The Main class that handles the running of the program and GUI
 * 
 * @author mattopdyke
 *
 */
public class Main extends Application {
	// Set general variables for the program
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 300;
	private static final String APP_TITLE = "Bug Keeper";

	// Initialize a new BugKeeper object
	BugKeeper bugkeeper = new BugKeeper();

	// Create the stage
	private static Stage stage;

	/**
	 * This method sets the stage, calls the method that creates and displays the
	 * home screen then sets the stage to be displayed
	 * 
	 * @param primaryStage: The stage to be used for execution
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		home();
		primaryStage.show();
	}

	/**
	 * The home method that creates and displays the application's home screen
	 */
	private void home() {
		// Use a VBox as our root
		VBox root = new VBox();

		// Upon clicking the create button, change screen to the "Create Screen"
		Button create = new Button("Log a new bug");
		create.setOnAction(c -> createScreen());

		// Upon clicking the read button, change screen to the "Read Screen"
		Button read = new Button("View logged bugs");
		read.setOnAction(r -> readScreen(0));

		// Upon clicking the update button, change screen to the "Update Screen"
		Button update = new Button("Update a logged bug");
		update.setOnAction(u -> updateScreen());

		// Upon clicking the delete button, change screen to the "Delete Screen"
		Button delete = new Button("Resolved a logged bug");
		delete.setOnAction(d -> deleteScreen());

		// Set the welcome message upon startup
		Label welcomeLabel = new Label("Welcome to Bug Keeper! Select one of the following buttons to begin");

		// Set the ID of the root VBox for styling
		root.setId("home-root");

		// add all of the screen details and apply the scene
		root.getChildren().addAll(welcomeLabel, create, read, update, delete);
		applyScene(root);
		stage.show();
	}

	/**
	 * Helper method that applies the parent changes to the stage and updates which
	 * screen is displayed
	 * 
	 * @param parent
	 */
	private void applyScene(Parent parent) {
		Scene scene = new Scene(parent, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(("GUI.css"));
		stage.setTitle(APP_TITLE);
		stage.setScene(scene);
	}

	/**
	 * Helper method that will create a new ticket within the BugKeeper object with
	 * the bug description and the line number that are inputed
	 * 
	 * @param bug
	 * @param line
	 */
	public void logNewBug(String bug, String line) {
		bugkeeper.createTicket(bug, line);
	}

	public void createScreen() {
		VBox display = new VBox();
		TextField bugInput = new TextField();
		TextField lineInput = new TextField();
		GridPane gridpane = new GridPane();
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());
		gridpane.addRow(0, new Label("Please enter a bug "), bugInput);
		gridpane.addRow(1, new Label("Please enter the line the bug is on (if known)"), lineInput);
		gridpane.add(ret, 1, 2);
		Button addBug = new Button("Log the bug to the system!");
		addBug.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				logNewBug(bugInput.getText(), lineInput.getText());
				if (display.getChildren().size() == 2) {
					Label confirmation = new Label("Your bug has been logged!");
					display.getChildren().add(confirmation);
				}
			}
		});
		display.getChildren().addAll(gridpane, addBug);

		applyScene(display);
	}

	public void readScreen(int index) {
		VBox display = new VBox();
		display.setId("read-display");
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			System.out.print(index);
			display.getChildren()
					.add(new Label("You may use the left and right arrows to look through the logged bugs!"));
			Ticket currTick = bugkeeper.readTickets().get(index);
			// GridPane gridpane = new GridPane();
			Label ticketLabel = new Label(currTick.formatTicket());

			Button leftArrow = new Button("<---");
			leftArrow.setId("arrow-button");
			leftArrow.setOnAction(c -> readScreen(handleLeftArrow(index)));

			Button rightArrow = new Button("--->");
			rightArrow.setId("arrow-button");
			rightArrow.setOnAction(c -> readScreen(handleRightArrow(index, bugkeeper.readTickets().size() - 1)));

			/*
			 * for (int i = 0; i < tickArr.size(); i++) { gridpane.add(new
			 * Label(tickArr.get(i).formatTicket()), i + 1, 0); }
			 * display.getChildren().add(gridpane);
			 */

			display.getChildren().add(new HBox(leftArrow, ticketLabel, rightArrow));
		}
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());
		display.getChildren().add(ret);
		applyScene(display);

	}

	public int handleLeftArrow(int index) {
		if (index == 0) {
			return 0;
		} else {
			return index - 1;
		}
	}

	public int handleRightArrow(int index, int upperBound) {
		if (index == upperBound) {
			return index;
		} else {
			return index + 1;
		}

	}

	/**
	 * facilitates the creation of the update screen
	 */
	public void updateScreen() {
		VBox display = new VBox();
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			int i = 0;
			ArrayList<Ticket> ticketList = bugkeeper.readTickets();
			do {
				Button b = new Button(ticketList.get(i).formatTicket());
				Ticket currentTicket = ticketList.get(i);
				b.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						ticketUpdate(currentTicket);
					}
				});
				display.getChildren().add(b);
				i++;
			} while (i < ticketList.size());
		}
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());
		display.getChildren().add(ret);
		applyScene(display);
	}

	/**
	 * changes to the ticket specific screen for updating ticket
	 * 
	 * @param ticket
	 */
	public void ticketUpdate(Ticket ticket) {
		VBox display = new VBox();
		String bug = ticket.bug;
		String line = ticket.line;
		LocalDate date = ticket.date;
		LocalTime time = ticket.time;
		TextField bugText = new TextField();
		bugText.setText(bug);
		TextField lineText = new TextField();
		lineText.setText(line);
		ObservableList<String> comboOptions = FXCollections.observableArrayList();
		comboOptions.addAll("Keep the logged Date and time", "Update to current date and time");
		ComboBox<String> dtbox = new ComboBox();
		dtbox.setItems(comboOptions);
		Label instr = new Label("Input the desired information to update a ticket");
		display.getChildren().add(instr);

		GridPane gridpane = new GridPane();
		gridpane.addColumn(0, new Label("Bug: "), new Label("Line: "),
				new Label("Date and time: \n  Date: " + date + "\n  Time: " + time));
		gridpane.addColumn(1, bugText, lineText, dtbox);

		display.getChildren().add(gridpane);

		Button update = new Button("Update logged bug!");
		update.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (dtbox.getValue().equals(comboOptions.get(1))) {
					ticket.updateTime();
				}
				ticket.bug = bugText.getText();
				ticket.line = lineText.getText();
				Label upsuc = new Label("The bug was updated correctly!");
				gridpane.add(upsuc, 0, 3);
			}
		});

		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());
		gridpane.add(update, 1, 3);
		display.getChildren().add(ret);
		applyScene(display);
	}

	/**
	 * facilitates the creation of the delete screen
	 */
	public void deleteScreen() {
		VBox display = new VBox();
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			int i = 0;
			ArrayList<Ticket> ticketList = bugkeeper.readTickets();
			do {
				Button b = new Button(ticketList.get(i).formatTicket());
				Ticket currentTicket = ticketList.get(i);
				b.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						deleteConfirmation(currentTicket);
					}
				});
				display.getChildren().add(b);
				i++;
			} while (i < ticketList.size());
		}
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());
		display.getChildren().add(ret);
		applyScene(display);
	}

	public void deleteConfirmation(Ticket ticket) {
		VBox display = new VBox();
		Label message = new Label("Are you sure you would like to resolve this bug?");
		message.setId("delete-confirmation");
		Label bugData = new Label(ticket.formatTicket());
		bugData.setId("delete-confirmation");

		HBox options = new HBox();
		Button confirm = new Button("Confirm");
		confirm.setOnAction(c -> {
			bugkeeper.deleteTicket(ticket);
			deleteScreen();
		});
		Button deny = new Button("Deny");
		deny.setOnAction(d -> deleteScreen());
		options.getChildren().addAll(confirm, deny);

		display.getChildren().addAll(message, options);
		applyScene(display);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
