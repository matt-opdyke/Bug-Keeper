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
	 * @param bug  the bug that is being logged
	 * @param line the line where the bug is located (optional)
	 */
	public Ticket(String bug, String line) {
		this.bug = bug;
		this.line = line;
	}

	/**
	 * default constructor for Ticket
	 */
	public Ticket() {

	}

	public void updateTime() {
		date = LocalDate.now();
		time = LocalTime.now();
	}

	/**
	 * Formats the data held by the Ticket object and prints to console USED FOR
	 * TESTING PURPOSES
	 */
	public String formatTicket() {
		return "Bug: " + bug + "\nLine: " + line + "\nDate: " + date + "\nTime: " + time;
	}
}
