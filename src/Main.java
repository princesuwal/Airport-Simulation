import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
	
	public static void main(String args[]) throws InterruptedException {
		ArrayList<Thread> PassengersList = new ArrayList<Thread>();
		int numPassengers = 30;
		String message;
		
		//starting the clock thread
		Clock clock = new Clock("Clock");
		clock.start();
		
		//Counter is open before the passengers arrive
		Counter counter1 = new Counter("Counter_1");
		Counter counter2 = new Counter("Counter_2");
		counter1.start();
		counter2.start();
		
		//passengers starts to arrive
		//passenger thread created by numPassengers
		for(int i = 0; i < numPassengers; i++) {
			Passenger p = new Passenger("Passenger_" + (i+1));
			PassengersList.add(p);
			PassengersList.get(i).start();
			TimeUnit.MILLISECONDS.sleep(100);

		}
		
		try {
			//waiting for all passengers to check in from counter
			Shared.allCheckedin.acquire();
			message = "All passengers have finished checking in";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ ": " + message);
			message = "Passengers arrive at the gate and is waiting for boarding.";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ ": " + message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			Shared.timetoBoard.acquire();
			
			FlightAttendant attendant = new FlightAttendant("FlightAttendant");
			attendant.start();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
