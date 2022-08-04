package sendmessage;

import java.util.function.BooleanSupplier;

public class WaitUtils {

	public static boolean waitUntil(BooleanSupplier condition, long timeoutms) {
		
		long start = System.currentTimeMillis();
		
		while (!condition.getAsBoolean()) {
			
			if (System.currentTimeMillis() - start > timeoutms ) {
				
				System.err.println(String.format("Condition not met within %s ms", timeoutms));
				return false;
			
			}
		}
		return true;
	}
	
	public static void sleep(long waitTime) {
		
		try {
			
			Thread.sleep(waitTime);
			
		}
		catch (InterruptedException e) {
			
		    Thread.currentThread().interrupt();
		    
		}
		
	}
}
