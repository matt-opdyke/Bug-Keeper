package application;
import java.util.ArrayList;

public class BugKeeper {

	public ArrayList<Ticket> ticketList = new ArrayList<>();

	/**
	 * Default constructor for BugKeeper
	 */
	public BugKeeper() {}
	
	public boolean createTicket (String bug, String line) {
		
		if (bug == null || bug.equals("")) {
			return false;
		}
		
		Ticket newTick = new Ticket (bug, line);
		newTick.setDate();
		newTick.setTime();
		ticketList.add(newTick);
		return true;
	}
	
	public ArrayList<Ticket> readTickets(){
		return ticketList;
	}
	
	public void updateTime (Ticket ticket) { 
		ticket.setDate();
		ticket.setTime();
	}
	
	public void updateMessage (Ticket ticket, String newMessage) {
		ticket.bug = newMessage;
	}
	
	public void updateLine (Ticket ticket, String newLine) { 
		ticket.line = newLine;
	}
	
	public void deleteTicket(Ticket ticket) {
		for (int i = 0; i < ticketList.size(); i ++) {
			if (ticketList.get(i).equals(ticket)) {
				ticketList.remove(i);
				break;
			}
		}
	}
}
