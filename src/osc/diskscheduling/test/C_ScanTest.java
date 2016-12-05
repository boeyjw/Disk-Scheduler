package osc.diskscheduling.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Test;

import osc.diskscheduling.algorithm.C_Scan;
import osc.diskscheduling.algorithm.DiskScheduling;

public class C_ScanTest {
	Integer[] arr = new Integer[] {50, 95, 180, 34, 119, 11, 123, 62, 64, 199};
	ListIterator<Integer> ls;
	DiskScheduling obj;
	
	private void init() {
		LinkedList<Integer> itReq = new LinkedList<Integer>();
		for(Integer k : arr)
			itReq.add(k);
		
		obj = new C_Scan(itReq.iterator());
	}
	
	@Test
	public void testScan() {
		init();
		System.out.println("Scan");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.first_scan_look();
		
		System.out.print("Output queue: ");
		obj.display();
		
		ls = obj.getRequestQueue().listIterator();
		assertEquals(230, ls.next().intValue());
		assertEquals(199, ls.next().intValue());
		assertEquals(50, ls.next().intValue());
	}
	
	@Test
	public void testCScan() {
		init();
		System.out.println("CScan");
		
		System.out.print("Input queue: ");
		obj.display();
				
		obj.shortest_cscan_clook();
		
		System.out.print("Output queue: ");
		obj.display();
		
		ls = obj.getRequestQueue().listIterator();
		assertEquals(187, ls.next().intValue());
		assertEquals(199, ls.next().intValue());
		assertEquals(50, ls.next().intValue());
	}
}
