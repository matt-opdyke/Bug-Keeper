package application;

/**
 * @author Matt Opdyke
 * Creates the Ticket object and implements functionality
 * Used by the BugKeeper class
 */
import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {

	String bug;
	String line;
	LocalDate date;
	LocalTime time;

	/**
	 * Constructor for Ticket object
	 * 
	 * @param bug:  The bug that is being logged
	 * @param line: The line where the bug is located (optional)
	 */
	public Ticket(String bug, String line) {
		this.bug = bug;
		this.line = line;
	}

	/**
	 * The default constructor for Ticket
	 */
	public Ticket() {
	}

	/**
	 * This method updates the time for the ticket in case the bug needs to be updated
	 */
	public void updateTime() {
		date = LocalDate.now();
		time = LocalTime.now();
	}

	/**
	 * Formats the data held by the Ticket object and prints to console 
	 * USED FOR TESTING PURPOSES
	 */
	public String formatTicket() {
		return "Bug: " + bug + "\nLine: " + line + "\nDate: " + date + "\nTime: " + time;
	}
}
