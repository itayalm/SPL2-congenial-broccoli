package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.Timer;
import java.util.TimerTask;
/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
	private int ticksPassed;
	private int duration;
	private Timer timer;
	private Helper task;

	public TimeService(String name, int duration) {
		super(name);
		this.duration = duration;
		this.ticksPassed = 0;
		timer = new Timer();
		task = new Helper(duration);
	}


	@Override
	protected void initialize() { }

	@Override
	public void run() {
		timer.scheduleAtFixedRate(task,0,100);
		getSimplePublisher().sendBroadcast(new TerminateBroadcast());

	}

}
