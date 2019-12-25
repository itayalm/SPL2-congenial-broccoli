package bgu.spl.mics.application.publishers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.LinkedList;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Publisher {
	private MessageBroker mb;
	private LinkedList<MissionInfo> infoList;
	private LinkedList<MissionRecievedEvent> missionList;
	private boolean terminated;
	public Intelligence(String name) {
		super(name);
		mb = MessageBrokerImpl.getInstance();
		infoList = new LinkedList<>();
		missionList = new LinkedList<MissionRecievedEvent>();
		terminated = false;
	}
	public Intelligence(String name, LinkedList<MissionInfo> infoList)
	{
		this(name);
		this.infoList = infoList;
	}
	protected final void terminate() {
		this.terminated = true;
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

	@Override
	public void run() {
		initialize();
		while(!terminated)
		{
			try {
				Broadcast Mes = mb.awaitMessage(this);


			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
