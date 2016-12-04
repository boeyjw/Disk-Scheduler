package osc.diskscheduling.algorithm;

/**
 * This is an Object class to holds information on the requests.
 * There should be an aggregation of this.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class Requests {
	public int cylinder;
	private int seekDiff;
	
	/**
	 * Constructor for scheduling algorithm that cannot compute disk head seek time without additional steps.
	 * This constructor will be used in cases where the a portion or the entire queue needs to be sorted.
	 * @param cylinder The cylinder in which the request needs to access
	 */
	public Requests(int cylinder) {
		this.cylinder = cylinder;
		this.setSeekDiff(0);
	}

	public int getSeekDiff() {
		return seekDiff;
	}

	public void setSeekDiff(int seekDiff) {
		this.seekDiff = seekDiff;
	}
}