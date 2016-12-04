package osc.diskscheduling.algorithm;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Scan, look, cscan and clook share the same principle as being an elevator algorithm.
 * Thus, they share similar code base. This class generalises their shared principle.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public abstract class Scan_Look extends DiskScheduling {
	
	protected int displacementCounter; //Track the number of places an element will need to be displaced
	protected boolean isInner; //Switch to identify whether the head sweep inwards or outwards

	public Scan_Look(Iterator<Integer> itQueue) {
		super(itQueue);
		displacementCounter = 0;
		isInner = false;
	}
	
	/**
	 * This method is only used for scan and look algorithm as they share similar principles.
	 * The algorithm's preferred direction is the shortest seek time between head and end of disk platter.
	 */
	protected void scan_look_default() {
		headSetSeekSigned();
		
		//The head travels to the nearest end of the disk platter from its initial position.
		if((tail - head) > head) {
			sweepInwards();
			isInner = true;
		}
		else {
			sweepOutwards();
			isInner = false;
		}
		
		displacementCounter = requestQueue.size() - displacementCounter;
	}
	
	/**
	 * This method is only used for cscan and clook as they share similar principles.
	 * The algorithm direction is always outer-to-inner.
	 */
	protected void cscan_clook_default() {
		headSetSeekSigned();
		sweepOutwards();
		displacementCounter = requestQueue.size() - displacementCounter;
	}
	
	/**
	 * Sets seek time sequentially based on the distance between head and the request cylinder then sorts them in ascending order.
	 * A negative seek time means the request cylinder is at the outer portion from the head.
	 * A positive seek time means the request cylinder is at the inner portion from the head.
	 */
	private void headSetSeekSigned() {
		for(int i = 0; i < requestQueue.size(); i++) {
			requestQueue.get(i).setSeekDiff(head - requestQueue.get(i).cylinder);
		}
		requestQueue.sort(new Comparator<Requests>() {
			@Override
			public int compare(Requests o1, Requests o2) {
				return (o1.getSeekDiff() < o2.getSeekDiff() ) ? 1 : -1;
			}
		});
	}
	
	/**
	 * The head sweeps into the inner portion of the disk platter.
	 */
	private void sweepInwards() {
		int addIndex = 0; //Request insertion pointer.
		 
		for(int i = 0; i < requestQueue.size(); i++) {
			if(requestQueue.get(i).getSeekDiff() > 0) {
				requestQueue.add(addIndex++, requestQueue.remove(i));
			}
			else {
				displacementCounter++;
			}
		}
		
		for(int i = addIndex - 1; i >= 0; i--) {
			requestQueue.get(i).setSeekDiff(head - requestQueue.get(i).cylinder);
		}
		
		mergeSort(0, addIndex - 1, true);
	}
	
	/**
	 * The head sweeps into the outer portion of the disk platter.
	 */
	private void sweepOutwards() {
		int addIndex = 0; //Request insertion pointer.
		 
		for(int i = 0; i < requestQueue.size(); i++) {
			if(requestQueue.get(i).getSeekDiff() < 0) {
				requestQueue.add(addIndex++, requestQueue.remove(i));
			}
			else {
				displacementCounter++;
			}
		}
	}
}