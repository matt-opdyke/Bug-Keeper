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

	/**
	 * Structures and creates functionality for the create screen where the user
	 * will be able to log a new bug
	 */
	public void createScreen() {
		// Use a VBox as our root
		VBox display = new VBox();

		// Create text fields for the user input
		TextField bugInput = new TextField();
		TextField lineInput = new TextField();

		// Initializes a new GridPane object to organize the text fields with their
		// respective labels
		GridPane gridpane = new GridPane();
		Button ret = new Button("Back"); // Create a return button that returns to the home screen
		ret.setId("home-button"); // Set the ID for styling
		ret.setOnAction(c -> home());
		gridpane.addRow(0, new Label("Please enter a bug "), bugInput);
		gridpane.addRow(1, new Label("Please enter the line the bug is on (if known)"), lineInput);
		gridpane.add(ret, 1, 2);

		// Implements functionality for the "add bug" button
		Button addBug = new Button("Log the bug to the system!");
		addBug.setId("add-bug");
		addBug.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				logNewBug(bugInput.getText(), lineInput.getText());

				// Assures that the confirmation message is only displayed once.
				if (display.getChildren().size() == 2) {
					Label confirmation = new Label("Your bug has been logged!");
					display.getChildren().add(confirmation);
				}
			}
		});
		gridpane.addRow(2, addBug);

		// Add screen elements to the root and apply the scene
		display.getChildren().addAll(gridpane, addBug);
		applyScene(display);
	}

	/**
	 * Structures and creates the functionality for the read screen where the user
	 * will be able to view the open bug tickets
	 * 
	 * @param index: The starting index for viewing
	 */
	public void readScreen(int index) {
		// Use a VBox as our root
		VBox display = new VBox();
		display.setId("read-display"); // Set ID for styling

		// Prints custom message if there are no open bugs
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			// Structure the screen with buttons to navigate the bug list
			display.getChildren()
					.add(new Label("You may use the left and right arrows to look through the logged bugs!"));
			Ticket currTick = bugkeeper.readTickets().get(index);
			Label ticketLabel = new Label(currTick.formatTicket());

			Button leftArrow = new Button("<---");
			leftArrow.setId("arrow-button"); // Set ID for styling
			leftArrow.setOnAction(c -> readScreen(handleLeftArrow(index)));

			Button rightArrow = new Button("--->");
			rightArrow.setId("arrow-button"); // Set ID for styling
			rightArrow.setOnAction(c -> readScreen(handleRightArrow(index, bugkeeper.readTickets().size() - 1)));

			display.getChildren().add(new HBox(leftArrow, ticketLabel, rightArrow));
		}
		// Create the functionality for the home button
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());

		// Add scene elements to the root and apply the scene
		display.getChildren().add(ret);
		applyScene(display);

	}

	/**
	 * Helper method to check the bounds of the left button
	 * 
	 * @param index: The current index of the ticket that the user is viewing
	 * @return
	 */
	public int handleLeftArrow(int index) {
		if (index == 0) {
			return 0;
		} else {
			return index - 1;
		}
	}

	/**
	 * Helper method to check the bounds of the right button
	 * 
	 * @param index: The current index of the ticket that the user is viewing
	 * @return
	 */
	public int handleRightArrow(int index, int upperBound) {
		if (index == upperBound) {
			return index;
		} else {
			return index + 1;
		}

	}

	/**
	 * Structures and creates the functionality for the update screen where the user
	 * will be able to update an open bug ticket
	 * 
	 * @param index: The starting index for viewing
	 */
	public void updateScreen() {
		// Use a VBox as our root
		VBox display = new VBox();

		// Prints custom message if there are no open bugs
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			// Initializes a ticket list of the current tickets
			int i = 0;
			ArrayList<Ticket> ticketList = bugkeeper.readTickets();
			do {
				// Creates a button for the current ticket
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

		// Adds a home button
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());

		// Add scene elements to the root and apply the scene
		display.getChildren().add(ret);
		applyScene(display);
	}

	/**
	 * A helper method to create the screen for updating a ticket, once a ticket is
	 * selected.
	 * 
	 * @param ticket: The selected ticket from the previous screen
	 */
	public void ticketUpdate(Ticket ticket) {
		// Use a VBox as our root
		VBox display = new VBox();

		// Place the data from our Ticket object into variable for cleaner code
		String bug = ticket.bug;
		String line = ticket.line;
		LocalDate date = ticket.date;
		LocalTime time = ticket.time;

		// create text fields if the user would like to update the bug description or
		// the line number
		TextField bugText = new TextField();
		bugText.setText(bug);
		TextField lineText = new TextField();
		lineText.setText(line);

		// Use a drop down menu to determine if a user would like to update the time
		// associated with the open ticket
		ObservableList<String> comboOptions = FXCollections.observableArrayList();
		comboOptions.addAll("Keep the logged Date and time", "Update to current date and time");
		ComboBox<String> dtbox = new ComboBox();
		dtbox.setItems(comboOptions);
		
		// Add the instructions label
		Label instr = new Label("Input the desired information to update a ticket");
		display.getChildren().add(instr);

		// Make use of a GridPane for organization of the screen.
		GridPane gridpane = new GridPane();
		gridpane.addColumn(0, new Label("Bug: "), new Label("Line: "),
				new Label("Date and time: \n  Date: " + date + "\n  Time: " + time));
		gridpane.addColumn(1, bugText, lineText, dtbox);

		// Add the gridpane to the root
		display.getChildren().add(gridpane);

		// Create the update button and add functionality.
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

		// Add the home button and its functionality to this screen as well
		Button ret = new Button("Back");
		ret.setId("home-button"); // Set ID for styling
		ret.setOnAction(c -> home());
		
		// Add the update button to the gridpane
		gridpane.add(update, 1, 3);
		
		// Add the screen elements to the root, then apply the scene
		display.getChildren().add(ret);
		applyScene(display);
	}

	/**
	 * Structures and creates the functionality for the delete screen where the user
	 * will be able to remove an open bug ticket
	 * 
	 */
	public void deleteScreen() {
		// Use VBox as our root
		VBox display = new VBox();

		// Assures that there are existing tickets
		if (bugkeeper.readTickets().size() < 1) {
			display.getChildren().add(new Label("There are no bugs :)"));
		} else {
			int i = 0;
			ArrayList<Ticket> ticketList = bugkeeper.readTickets();
			do {
				// Creates a button for each ticket
				Button b = new Button(ticketList.get(i).formatTicket());
				Ticket currentTicket = ticketList.get(i);

				// On click, switch to delete confirmation screen
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

		// Adds the home button and functionality
		Button ret = new Button("Back");
		ret.setId("home-button");
		ret.setOnAction(c -> home());

		// Add elements to the root and apply the scene
		display.getChildren().add(ret);
		applyScene(display);
	}

	/**
	 * A helper method that will change screens to the deletion confirmation screen.
	 * Upon selecting the "Confirm" button, the ticket will be removed.
	 * 
	 * @param ticket: The ticket to be removed
	 */
	public void deleteConfirmation(Ticket ticket) {
		// Use a VBox as our root
		VBox display = new VBox();

		// Add labels for the screen
		Label message = new Label("Are you sure you would like to resolve this bug?");
		message.setId("delete-confirmation"); // Set ID for styling
		Label bugData = new Label(ticket.formatTicket());
		bugData.setId("delete-confirmation"); // Set ID for styling

		// Add an HBox for the "Confirm" and "Deny" buttons and implement functionality
		HBox options = new HBox();
		Button confirm = new Button("Confirm");
		confirm.setOnAction(c -> {
			bugkeeper.deleteTicket(ticket);
			deleteScreen();
		});
		Button deny = new Button("Deny");
		deny.setOnAction(d -> deleteScreen());
		options.getChildren().addAll(confirm, deny);

		// Add the elements from the screen to the root and apply the scene
		display.getChildren().addAll(message, options);
		applyScene(display);
	}

	/**
	 * The main driver method for the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
