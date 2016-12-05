package osc.diskscheduling.test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Test;

import osc.diskscheduling.algorithm.C_Look;
import osc.diskscheduling.algorithm.DiskScheduling;

public class C_LookTest {
	Integer[] arr = new Integer[] {50, 95, 180, 34, 119, 11, 123, 62, 64, 199};
	ListIterator<Integer> ls;
	DiskScheduling obj;
	
	private void init() {
		LinkedList<Integer> itReq = new LinkedList<Integer>();
		for(Integer k : arr)
			itReq.add(k);
		
		obj = new C_Look(itReq.iterator());
	}
	
	@Test
	public void testLook() {
		int[] arr_look = {100, 23, 89, 132, 42, 187, 199};
		LinkedList<Integer> itReq = new LinkedList<Integer>();
		for(Integer k : arr_look)
			itReq.add(k);
		
		obj = new C_Look(itReq.iterator());
		
		System.out.println("Look");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.first_scan_look();
		
		System.out.print("Output queue: ");
		obj.display();
		
		ls = obj.getRequestQueue().listIterator();
		assertEquals(241, ls.next().intValue());
		assertEquals(199, ls.next().intValue());
		assertEquals(100, ls.next().intValue());
	}
	
	@Test
	public void testCLook() {
		init();
		System.out.println("CLook");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.shortest_cscan_clook();
		
		System.out.print("Output queue: ");
		obj.display();
		
		ls = obj.getRequestQueue().listIterator();
		assertEquals(157, ls.next().intValue());
		assertEquals(199, ls.next().intValue());
		assertEquals(50, ls.next().intValue());
	}

}
