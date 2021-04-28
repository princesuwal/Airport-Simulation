
public class FlightAttendant extends Thread{
	
	public FlightAttendant(String name) {
		this.setName(name);
	}
	
	@Override
	public void run() {
		String message;
		message = "Begins to call passengers up to the door of the jet bridge";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		
		Shared.boardZone1.release(10);
		message = "Zone 1 Start forming line and Board";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		try {
			Shared.zoneBoarded.acquire();
			message = "*************Zone 1 Finished Boarding************";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Shared.boardZone2.release(10);
		message = "Zone 2 Start forming line and Board";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		try {
			Shared.zoneBoarded.acquire();
			message = "*************Zone 2 Finished Boarding************";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Shared.boardZone3.release(10);
		message = "Zone 3 Start forming line and Board";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		try {
			Shared.zoneBoarded.acquire();
			message = "*************Zone 3 Finished Boarding************";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		message = "*************The plane door has closed*******************";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		message = "Plane take off. All passengers waiting for landing";
		System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
		
		
		try {
			Shared.timetoLand.acquire();
			message = "********************************************************";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			message = "It is landing time. Please stay in your seat and be ready.";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			Thread.sleep(3000);
			Shared.planeLanded.release(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			Shared.allDeparted.acquire();
			message = "All passengers have departed.";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ this.getName() + ": " + message);
			Thread.sleep(3000);
			message = "Flight Attendants are cleaning the plane........";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ message);
			Thread.sleep(3000);
			message = "Flight Attendants have cleaned the plane and are now leaving";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ message);
			Shared.FlightTerminated.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		/*
		int count = 1;
		while(count <= 30) {
			for(int i = 0; i < 30; i++) {
				if(count == Shared.assignedSeat[i]) {
					System.out.println(Shared.assignedSeat[i]);
				}
			}
			count++;
		}*/
	}

}
