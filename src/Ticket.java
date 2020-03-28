
import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {
	
	String bug = "";
	int line;
	LocalDate date;
	LocalTime time;
	
	public Ticket (String bug, int line) {
		this.bug = bug;
		this.line = line;
	}
	
	public Ticket () {
		
	}
	
	public void setTime() {
		time = LocalTime.now();
	}
	
	public void setDate () {
		date = LocalDate.now();
	}
	
	public void formatTicket () {
		System.out.println("Bug: " + bug + 
				"\nLine: " + line +
				"\nDate: " + date + 
				"\nTime: " + time);
	}
}
