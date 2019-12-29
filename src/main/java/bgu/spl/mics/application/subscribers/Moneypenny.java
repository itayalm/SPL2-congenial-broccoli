package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.Trio;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.List;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private Squad squad;

	public Moneypenny(String name) {
		super(name);
		squad = Squad.getInstance();
	}

	@Override
	protected void initialize() {
		this.subscribeEvent(AgentsAvailableEvent.class, new Callback<AgentsAvailableEvent>() {
			@Override
			public void call(AgentsAvailableEvent c) {
				List<String> serial = c.getSerials();
				int duration = c.getDuration();
				boolean b;
				List<String> names;
				synchronized(this) { // so several instances wont access the squad simultaneously
					b = squad.getAgents(serial);
					if (b) {
						squad.sendAgents(serial, duration);
					}
					names = squad.getAgentsNames(serial);
				}
				Trio<String, List<String>, Boolean> t = new Trio(getName(), names, b);
				complete(c, t);
			}
		});
		this.subscribeBroadcast(TerminateBroadcast.class, new Callback<TerminateBroadcast>() {
			@Override
			public void call(TerminateBroadcast c) {
				terminate();
			}
		});
		
	}

}
