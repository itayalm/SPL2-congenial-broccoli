package bgu.spl.mics.application.subscribers;
import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.MissionRecievedEvent;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	private int timeTick;
	private Report report;
	public M(String name) {
		super(name);
		report = new Report();
	}

	@Override
	protected void initialize() throws InterruptedException {
		this.subscribeEvent(MissionRecievedEvent.class, new Callback<MissionRecievedEvent>() {
			@Override
			public void call(MissionRecievedEvent c) {
				MissionInfo info = c.getInfo();
				report.
			}
		});

	}

}
