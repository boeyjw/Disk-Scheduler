package osc.diskscheduling.algorithm;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Concrete class that serves as a layer of abstraction from the back-end algorithm.
 * GUI code only need to instantiate this class and use the algorithmSelector method to select the algorithm to simulate.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class ControllerBroker {
	
	private DiskScheduling dsFunc;
	
	public static final int FCFS 	= 1;
	public static final int SSTF 	= 2;
	public static final int SCAN 	= 3;
	public static final int CSCAN 	= 4;
	public static final int LOOK 	= 5;
	public static final int CLOOK 	= 6;
	
	/**
	 * The bridging method. 
	 * This method does not modify the input list.
	 * @param inputQueue The input list.
	 * @param operation Specify which algorithm from {@code public static final int ALGORITHM}.
	 * @return The modified list.
	 */
	public LinkedList<Integer> algorithmSelector(LinkedList<Integer> inputQueue, int operation) {
		Iterator<Integer> itQueue = inputQueue.iterator();
		LinkedList<Integer> modQueue = new LinkedList<Integer>();
		
		if(operation == FCFS || operation == SSTF) {
			dsFunc = new Fcfs_Sstf(itQueue);
		}
		else if(operation == SCAN || operation == CSCAN) {
			dsFunc = new C_Scan(itQueue);
		}
		else if(operation == LOOK || operation == CLOOK) {
			dsFunc = new C_Look(itQueue);
		}
		
		if(operation == FCFS || operation == SCAN || operation == LOOK) {
			dsFunc.first_scan_look();
		}
		else if(operation == SSTF || operation == CSCAN || operation == CLOOK) {
			dsFunc.shortest_cscan_clook();
		}
		
		modQueue.addAll(dsFunc.getRequestQueue());
		
		return modQueue;
	}
}
