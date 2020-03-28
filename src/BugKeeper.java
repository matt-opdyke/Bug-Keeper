import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Queue;

public class BugKeeper {

	private ArrayList<Ticket> tickList = new ArrayList<>();

	public BugKeeper() {

	}

	public static void main(String args[]) {
		Ticket t = new Ticket("bug", 0);

		t.setDate();
		t.setTime();
		t.formatTicket();
		// System.out.println(t.time);

	}
}
