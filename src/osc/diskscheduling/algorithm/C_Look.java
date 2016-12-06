package osc.diskscheduling.algorithm;

import java.util.Iterator;

/**
 * Implementation of Look and CLook algorithm.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class C_Look extends Scan_Look {
	
	public C_Look(Iterator<Integer> itQueue) {
		super(itQueue);
	}
	
	/**
	 * Look Algorithm.
	 */
	@Override
	public void first_scan_look() {
		scan_look_default();
		mergeSort(displacementCounter, requestQueue.size() - 1, true);
		absoluteSetSeekTime();
	}
	
	/**
	 * CLook algorithm.
	 */
	@Override
	public void shortest_cscan_clook() {
		cscan_clook_default();
		absoluteSetSeekTime();
		//The jump from innermost request to outermost track has no seek difference.
		requestQueue.get(displacementCounter).setSeekTime(0);
	}
}
