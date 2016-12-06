package osc.diskscheduling.algorithm;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Implementation of the First Come First Serve and Shortest Seek Time First algorithm
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class Fcfs_Sstf extends DiskScheduling {
	
	public Fcfs_Sstf(Iterator<Integer> itQueue) {
		super(itQueue);
	}
	
	/**
	 * First Come First Serve algorithm
	 * All request are honoured in a sequential manner. There are no modifications made to the input queue.
	 */
	@Override
	public void first_scan_look() {
		absoluteSetSeekTime();
	}
	
	/**
	 * Shortest Seek Time First algorithm
	 * Request that are close to each other are honoured first.
	 * This is achieved by sorting cylinders closest to the initial position of the head to the furthest.
	 */
	@Override
	public void shortest_cscan_clook() {
		/*
		 * Calculate the seek time between the disk head and every request then
		 * sorts them in ascending order.
		 */
		for(int i = 0; i < requestQueue.size(); i++) {
			requestQueue.get(i).setSeekTime(Math.abs(head - requestQueue.get(i).cylinder));
		}
		
		/*
		 * Sort the queue in ascending order.
		 */
		requestQueue.sort(new Comparator<Requests>() {
			@Override
			public int compare(Requests o1, Requests o2) {
				return (o1.getSeekTime() < o2.getSeekTime()) ? -1 : 1;
			}
		});
		
		absoluteSetSeekTime();
	}
}