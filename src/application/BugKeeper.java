package application;
import java.util.ArrayList;

/**
 * The BugKeeper class that facilitates the creation, removal and updating of Tickets
 * @author mattopdyke
 *
 */
public class BugKeeper {
	
	// An ArrayList to hold the active bugs
	public ArrayList<Ticket> ticketList = new ArrayList<>();

	/**
	 * Default constructor for BugKeeper
	 */
	public BugKeeper() {}
	
	/**
	 * Facilitates the creation of a new ticket
	 * @param bug: The inputed bug represented as a string
	 * @param line: The inputed line number represented as a string
	 */
	public void createTicket (String bug, String line) {
		
		// Assures that the input is valid
		if (bug == null || bug.equals("")) {
			System.out.println("ERROR: Invalid input, please describe the bug.");
		}
		
		// Creates a new ticket object with the input and updates the time
		Ticket newTick = new Ticket (bug, line);
		newTick.updateTime();
		
		// Adds the newly created ticket to the ticket list
		ticketList.add(newTick);
	}
	
	/**
	 * Retrieves the list of open tickets
	 * @return The list of tickets
	 */
	public ArrayList<Ticket> readTickets(){
		return ticketList;
	}
	
	/**
	 * Updates an existing bug's description
	 * @param ticket: The specified existing ticket
	 * @param newMessage: The message to replace the old message
	 */
	public void updateMessage (Ticket ticket, String newMessage) {
		ticket.bug = newMessage;
	}
	
	/**
	 * Updates an existing bug's line number
	 * @param ticket: The specified existing ticket
	 * @param newLine: The line to replace the old line
	 */
	public void updateLine (Ticket ticket, String newLine) { 
		ticket.line = newLine;
	}
	
	/**
	 * Facilitates the removal of tickets from the open ticket list
	 * @param ticket: The ticket to be removed
	 */
	public void deleteTicket(Ticket ticket) {
		// Iterates over the list of open tickets to find the specified ticket
		for (int i = 0; i < ticketList.size(); i ++) {
			// Remove the ticket from the list if found
			if (ticketList.get(i).equals(ticket)) {
				ticketList.remove(i);
				break;
			}
		}
	}
}
