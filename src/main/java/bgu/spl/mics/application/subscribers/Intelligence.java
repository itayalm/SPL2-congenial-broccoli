package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.LinkedList;

/**
 * A Publisher\Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {

	private MessageBroker mb;
	private LinkedList<MissionInfo> infoList;
	private LinkedList<MissionRecievedEvent> missionList;
	public Intelligence(String name) {
		super(name);

		mb = MessageBrokerImpl.getInstance();
		infoList = new LinkedList<>();
		missionList = new LinkedList<MissionRecievedEvent>();
	}
	public Intelligence(String name, LinkedList<MissionInfo> infoList)
	{
		this(name);
		this.infoList = infoList;
	}

	@Override
	protected void initialize() {
		for (MissionInfo m : infoList)
		{
			// TODO implement this create events depends on how itai does them
		}
		mb.register(this);
		mb.subscribeBroadcast(TickBroadcast.class, this);
	}


}
