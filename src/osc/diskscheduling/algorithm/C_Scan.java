package osc.diskscheduling.algorithm;

import java.util.Iterator;

/**
 * Implementation of Scan and CScan algorithm
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class C_Scan extends Scan_Look {
	
	public C_Scan(Iterator<Integer> itQueue) {
		super(itQueue);
	}
	
	/**
	 * Scan algorithm.
	 */
	@Override
	public void first_scan_look() {
		scan_look_default();
		
		if(isInner) { //The head sweep inwards toward the spindle.
			requestQueue.add(displacementCounter, new Requests(0));
			mergeSort(displacementCounter, requestQueue.size() - 1, false);
		}
		else { //The head sweep outwards toward the end of the disk platter.
			requestQueue.add(displacementCounter, new Requests(tail));
			mergeSort(displacementCounter, requestQueue.size() - 1, true);
		}
		
		absoluteSetSeekTime();
	}
	
	/**
	 * CScan algorithm
	 */
	@Override
	public void shortest_cscan_clook() {
		cscan_clook_default();
		requestQueue.add(displacementCounter, new Requests(tail));
		absoluteSetSeekTime();
		//Makes a jump from innermost track to outermost track, thus inserts outermost track into requests queue 
		requestQueue.add(++displacementCounter, new Requests(0));
		
		/*
		 * Calculate seek time after the jump.
		 * All seek time value are guaranteed to be unsigned.
		 */
		for(int i = displacementCounter + 1; i < requestQueue.size(); i++) {
			requestQueue.get(i).setSeekTime(requestQueue.get(i - 1).cylinder - requestQueue.get(i).cylinder);
		}
	}
}
