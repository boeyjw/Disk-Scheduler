package osc.diskscheduling.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Superclass for the disk scheduling algorithm.
 * Contains all globally required methods for child class.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public abstract class DiskScheduling {
	
	protected ArrayList<Requests> requestQueue = new ArrayList<Requests>();
	
	protected int head;
	protected int tail;
	
	/**
	 * Gets an Iterator over the input queue.
	 * The Iterator must have the head and tail within the input queue of {@code LinkedList<Integer>}.
	 * Automatically converts Integer to int and insert into working {@code LinkedList<Requests>}.
	 * Head and tail is removed before processing the working list.
	 * @param itQueue The Iterator reference.
	 */
	public DiskScheduling(Iterator<Integer> itQueue) {
		while(itQueue.hasNext()) {
			requestQueue.add(new Requests(itQueue.next().intValue()));
		}
		head = requestQueue.remove(0).cylinder;
		tail = requestQueue.remove(requestQueue.size() - 1).cylinder;
	}
	
	/**
	 * Polymorphism for 3 primitive algorithm.
	 * First Come First Serve, Scan and Look.
	 */
	public abstract void first_scan_look();
	
	/**
	 * Polymorphism for 3 enhanced algorithm from {@link osc.diskscheduling.algorithm.DiskScheduling#first_scan_look()}.
	 * Shortest Seek Time First, CScan, CLook
	 */
	public abstract void shortest_cscan_clook();
	
	/**
	 * Shows the request queue. For debug purposes only
	 */
	public void display() {
		System.out.print("[ ");
		for(int i = 0; i < requestQueue.size(); i++)
			System.out.print(requestQueue.get(i).cylinder + " ");
		System.out.println("]");
	}
	
	/**
	 * Shows the seek difference of each request in the queue. For debug purposes only.
	 */
	public void displaySeek() {
		System.out.print("[ ");
		for(int i = 0; i < requestQueue.size(); i++)
			System.out.print(requestQueue.get(i).getSeekDiff() + " ");
		System.out.println("]");
	}
	
	/**
	 * Set seek time sequentially.
	 * All seek time are unsigned.
	 */
	protected void absoluteSetSeek() {
		requestQueue.get(0).setSeekDiff(Math.abs(head - requestQueue.get(0).cylinder));
		for(int i = 1; i < requestQueue.size(); i++) {
			requestQueue.get(i).setSeekDiff(Math.abs(requestQueue.get(i).cylinder - requestQueue.get(i - 1).cylinder));
		}
	}
	
	/**
	 * Calculates the total seek time for the entire request queue.
	 * This method is directly used for FCFS algorithm.
	 * @return The total seek time
	 */
	private int totalSeek() {
		int seekSum = 0;
		
		for(int i = 0; i < requestQueue.size(); i++) {
			seekSum += requestQueue.get(i).getSeekDiff();
		}
		
		return seekSum;
	}
	
	/**
	 * Gets the modified working list.
	 * The head is the first element of the list.
	 * The total seek and tail are pushed into the end of the list.
	 * @return {@code LinkedList<Integer>} with cylinder values only.
	 */
	public LinkedList<Integer> getRequestQueue() {
		LinkedList<Integer> cylinderOnly = new LinkedList<Integer>();
		
		for(int i = 0; i < requestQueue.size(); i++) {
			cylinderOnly.add(new Integer(requestQueue.get(i).cylinder));
		}
		
		cylinderOnly.push(new Integer(head));
		cylinderOnly.push(new Integer(tail));
		cylinderOnly.push(new Integer(totalSeek()));
				
		return cylinderOnly;
	}
}
