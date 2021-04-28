import java.util.Random;

public class Passenger extends Thread{
	boolean arrived = false;
	boolean checkedIn = false;
    boolean board = false;
	int seatNo;
	int zoneNo;
	
	public Passenger(String name){
		this.setName(name);
	}
	
	public Passenger(String name, int seat){
		this.setName(name);
		this.seatNo = seat;
	}
	
	public void setSeat(int seat_No) {
		seatNo = seat_No;
	}
	
	@Override
	public void run() {
		Random sleepTime = new Random();
		int randomTime = sleepTime.nextInt(300) + 1;
		
		try {
			Thread.sleep(randomTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.arrived = true;
		String message = "Has arrived at the Airport.";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		try {
			Shared.counterSemaphore.acquire();
			
			Shared.passenger = this;
			Shared.passengerAtCounter.release();
			Shared.seatAssigned.acquire();
			
			Shared.mutexSeatread.acquire();
			this.checkedIn = true;
			this.seatNo = Shared.seat;
			Shared.mutexSeatread.release();
			
			if(this.seatNo >= 1 && this.seatNo <= 10) {
				this.zoneNo = 1;
			}else if(this.seatNo >= 11 && this.seatNo <= 20) {
				this.zoneNo = 2;
			}else if(this.seatNo >= 21 && this.seatNo <= 30) {
				this.zoneNo = 3;
			}
			
			
			message = "Has checked in with SeatNo.: " + this.seatNo +" and in Zone: " + this.zoneNo;
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			if(this.zoneNo == 1) {
				Shared.boardZone1.acquire();
				sleepTime = new Random();
				randomTime = sleepTime.nextInt(3000);
				
					Thread.sleep(randomTime);
				
				message = "Has boarded from Zone 1";
				System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
				Shared.zoneBoardCount++;
				if(Shared.zoneBoardCount >= 10) {
					Shared.zoneBoardCount = 0;
					Shared.zoneBoarded.release();
				}
			}else if(this.zoneNo == 2){
				Shared.boardZone2.acquire();
				sleepTime = new Random();
				randomTime = sleepTime.nextInt(3000);
				
					Thread.sleep(randomTime);
				
				message = "Has boarded from Zone 2";
				System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
				Shared.zoneBoardCount++;
				if(Shared.zoneBoardCount >= 10) {
					Shared.zoneBoardCount = 0;
					Shared.zoneBoarded.release();
				}
			}else {
				Shared.boardZone3.acquire();
				sleepTime = new Random();
				randomTime = sleepTime.nextInt(3000);
				
					Thread.sleep(randomTime);
				
				message = "Has boarded from Zone 3";
				System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
				Shared.zoneBoardCount++;
				if(Shared.zoneBoardCount >= 10) {
					Shared.zoneBoardCount = 0;
					Shared.zoneBoarded.release();
				}
			}
			
			Shared.planeLanded.acquire();
			
			Thread.sleep(this.seatNo*100);
			
			message = "is in Seat :" + this.seatNo +" and departs the plane"; 
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			
			Shared.passengerCount++;
			if(Shared.passengerCount >= 30) {
				Shared.allDeparted.release();
			}
			
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
