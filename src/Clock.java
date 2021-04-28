
public class Clock extends Thread{
	long time = System.currentTimeMillis();
	
	public Clock(String name) {
		this.setName(name);
	}
	
	@Override
	public void run() {
		Shared.time = this.time;
		
		while(true) {
			if((System.currentTimeMillis() - Shared.time) == 20000) {
				Shared.timetoBoard.release();
				break;
			}
		}
		
		while(true) {
			if((System.currentTimeMillis() - Shared.time) == 45000) {
				Shared.timetoLand.release();
				break;
			}
		}
		
		try {
			Shared.FlightTerminated.acquire();
			String message = "Clock is now terminating.........";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ message);
			Thread.sleep(2000);
			message = "***********Clock has terminated!!!**********";
			System.out.println("["+(System.currentTimeMillis() - Shared.time)+"] "+ message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
