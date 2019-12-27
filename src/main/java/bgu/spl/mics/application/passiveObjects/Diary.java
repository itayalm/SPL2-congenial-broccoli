package bgu.spl.mics.application.passiveObjects;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {
	/**
	 * Retrieves the single instance of this class.
	 */
	private static Diary diary_singleton = new Diary();

	private final AtomicBoolean lock;

	private List<Report> reports;
	private int total;

	/**
	 * Retrieves the single instance of this class.
	 */
	private Diary()
	{
		this.lock = new AtomicBoolean(false);
		reports = new LinkedList<Report>();
		total = 0;
	}
	public static Diary getInstance() {
		if(diary_singleton == null)
		{
			diary_singleton = new Diary();
		}
		return diary_singleton;
	}

	public List<Report> getReports() {
		return reports; //CMNT
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public void addReport(Report reportToAdd){
		while(!lock.compareAndSet(false, true));
		try {
			reports.add(reportToAdd);
		}
		finally {
			lock.set(false);
		}
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		//TODO: Implement this
	}

	public void incrementTotal(){
		while(!lock.compareAndSet(false,true));
		try {
			total++;
		}
		finally {
			lock.set(false);
		}
	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){ //CMNT synchronized??
		return total;
	}
}
