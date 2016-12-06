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
			requestQueue.get(i).setSeekTime(head - requestQueue.get(i).cylinder);
		}
		requestQueue.sort(new Comparator<Requests>() {
			@Override
			public int compare(Requests o1, Requests o2) {
				return (o1.getSeekTime() < o2.getSeekTime() ) ? 1 : -1;
			}
		});
	}
	
	/**
	 * The head sweeps into the inner portion of the disk platter.
	 */
	private void sweepInwards() {
		int addIndex = 0; //Request insertion pointer.
		 
		for(int i = 0; i < requestQueue.size(); i++) {
			if(requestQueue.get(i).getSeekTime() > 0) {
				requestQueue.add(addIndex++, requestQueue.remove(i));
			}
			else {
				displacementCounter++;
			}
		}
		
		for(int i = addIndex - 1; i >= 0; i--) {
			requestQueue.get(i).setSeekTime(head - requestQueue.get(i).cylinder);
		}
		
		mergeSort(0, addIndex - 1, true);
	}
	
	/**
	 * The head sweeps into the outer portion of the disk platter.
	 */
	private void sweepOutwards() {
		int addIndex = 0; //Request insertion pointer.
		 
		for(int i = 0; i < requestQueue.size(); i++) {
			if(requestQueue.get(i).getSeekTime() < 0) {
				requestQueue.add(addIndex++, requestQueue.remove(i));
			}
			else {
				displacementCounter++;
			}
		}
	}

	/**
	 * MergeSort algorithm for disk scheduling algorithm which has ascending or descending pattern
	 * @param l Lower bound
	 * @param m Middle reference
	 * @param r Upper bound
	 * @param isAsc Flag for ascending or descending sort
	 */
	private void merge(int l, int m, int r, boolean isAsc) {
		int n1 = l;
		int n2 = m + 1;
		int i = l;
		Requests[] arr = new Requests[requestQueue.size()];
		
		if(isAsc) { //Sort in ascending order?
			while(n1 <= m && n2 <= r) {
				arr[i++] = (requestQueue.get(n1).getSeekTime() <= requestQueue.get(n2).getSeekTime()) ? //Sort in ascending
						requestQueue.get(n1++) : requestQueue.get(n2++);
			}
		}
		else {
			while(n1 <= m && n2 <= r) {
				arr[i++] = (requestQueue.get(n1).getSeekTime() >= requestQueue.get(n2).getSeekTime()) ? //Sort in descending
						requestQueue.get(n1++) : requestQueue.get(n2++);
			}
		}
		
		while(n1 <= m)
			arr[i++] = requestQueue.get(n1++);
		
		while(n2 <= r)
			arr[i++] = requestQueue.get(n2++);
		
		for(int z = l; z <= r; z++)
			requestQueue.set(z, arr[z]);
	}

	/**
	 * MergeSort algorithm for sorting. This method is used to execute MergeSort.
	 * This MergeSort is for partial list sorting.
	 * Use {@code list.sort(new Comparator<Requests>())} instead for full list sort.
	 * @param l Lower bound
	 * @param r Upper bound
	 * @param isAsc Flag for ascending or descending sort
	 */
	protected void mergeSort(int l, int r, boolean isAsc) {
		if(l < r) {
			int mid = (l + r) / 2;
			mergeSort(l, mid, isAsc);
			mergeSort(mid + 1, r, isAsc);
			merge(l, mid, r, isAsc);
		}
		else
			return;
	}
}