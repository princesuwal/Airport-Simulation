import java.util.Random;

public class Counter extends Thread{
	int numPassengers = 30;
	
	public Counter(String name) {
		this.setName(name);
	}
	
	boolean alreadyAssigned(int seat) {
		for(int i = 0; i < 30; i++) {
			if(Shared.assignedSeat[i] == seat) {
				return true;
			}
		}
		return false;
	}
	
	void assignSeats(int index) {
		Random random = new Random();
		int randomSeat = random.nextInt(30)+1;
		
		while(alreadyAssigned(randomSeat)) {
			randomSeat = random.nextInt(30)+1;
		}
		Shared.assignedSeat[index] = randomSeat;
		String message = "is attending " + Shared.passenger.getName();
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName()+": " + message);
		message = "Has assigned SeatNo.: " + randomSeat + " to " + Shared.passenger.getName();
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName()+": " + message);
		Shared.seat = randomSeat;
		Shared.seatAssigned.release();
	}
	
	@Override
	public void run() {
		
		String message = this.getName() + " is OPEN";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		while(true) {
			if(Shared.passengerAtCounter.tryAcquire()) {
				try {
					Shared.mutexSeat.acquire();
					this.assignSeats(Shared.passengersCheched);	//Counter assigns seat no to passengers
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					Random sleepTime = new Random();
					int randomTime = sleepTime.nextInt(1000);
					Thread.sleep(randomTime);	//assuming time taken at counter
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Shared.passengersCheched++;
				Shared.mutexSeat.release();
				Shared.counterSemaphore.release();
			}
			if(Shared.passengersCheched >= numPassengers) {
				break;
			}
		}
		
		message = this.getName() + " is CLOSED for today";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		Shared.allCheckedin.release();		//all passengers have checked in
	}

}
