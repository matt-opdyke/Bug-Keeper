package application;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Queue;

public class BugKeeper {

	public ArrayList<Ticket> tickList = new ArrayList<>();

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
		tickList.add(newTick);
		return true;
	}
	
	public ArrayList<Ticket> readTickets(){
		return tickList;
	}

	public static void main(String args[]) {
		Ticket t = new Ticket("bug", "0");

		t.setDate();
		t.setTime();
		t.formatTicket();
		// System.out.println(t.time);

	}
}
