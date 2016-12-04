package osc.diskscheduling.algorithm;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class ControllerBrokerTest {
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	ControllerBroker obj = new ControllerBroker();
	
	private void init() {
		int[] arr = new int[] {50, 95, 180, 34, 119, 11, 123, 62, 64, 199};
		
		for(int k : arr)
			queue.add(new Integer(k));
	}
	
	@Test
	public void testControllerBroker() {
		init();
		LinkedList<Integer> temp = new LinkedList<Integer>();
		
		temp = obj.algorithmSelector(queue, ControllerBroker.FCFS);
		
		assertEquals(new Integer(644), temp.pop()); //Total seek
		assertEquals(queue.getLast(), temp.pop()); //Tail
		assertEquals(queue.getFirst(), temp.getFirst()); //Head
		
		
		temp = obj.algorithmSelector(queue, ControllerBroker.CLOOK);
		assertEquals(new Integer(157), temp.pop());
		assertEquals(queue.getLast(), temp.pop());		
		assertEquals(queue.getFirst(), temp.getFirst());
				
		temp = obj.algorithmSelector(queue, ControllerBroker.CSCAN);
		assertEquals(new Integer(187), temp.pop());
		assertEquals(queue.getLast(), temp.pop());		
		assertEquals(queue.getFirst(), temp.getFirst());		
	}

}
