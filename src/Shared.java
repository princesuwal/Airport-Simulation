import java.util.concurrent.Semaphore;


public class Shared {
	static long time;
	static Semaphore counterSemaphore = new Semaphore(2, true);	// counter available or not 
	static Semaphore passengerAtCounter = new Semaphore(0);		// are passenger lined up for counter
	static Semaphore seatAssigned = new Semaphore(0);			// has the counter assigned seat to passenger
	static Semaphore allCheckedin = new Semaphore(0);			// has all the passengers been checked in at counter
	static Semaphore timetoBoard = new Semaphore(0);			// time to board the plane
	static Semaphore boardZone1 = new Semaphore(0);
	static Semaphore boardZone2 = new Semaphore(0);
	static Semaphore boardZone3 = new Semaphore(0);
	
	static Semaphore mutexSeat = new Semaphore(1);
	static Semaphore mutexSeatread = new Semaphore(1);
	static Semaphore zoneBoarded = new Semaphore(0);
	
	static Semaphore timetoLand = new Semaphore(0);
	static Semaphore planeLanded = new Semaphore(0);
	static Semaphore allDeparted = new Semaphore(0);
	static Semaphore FlightTerminated = new Semaphore(0);
	
	
	static Thread passenger;
	static int assignedSeat[] = new int[30];
	static int passengersCheched = 0;
	static int seat = 0;
	static int zoneBoardCount = 0;
	static int passengerCount = 0;

	static Thread[] passengerList = new Thread[30];
	

	
}
