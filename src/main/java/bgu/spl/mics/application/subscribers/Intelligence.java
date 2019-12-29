package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
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

	private LinkedList<MissionInfo> infoList;
	private int ticks;
	public Intelligence(String name) {
		super(name);
		ticks = 0;
		infoList = new LinkedList<>();
	}
	public Intelligence(String name, LinkedList<MissionInfo> infoList)
	{
		this(name);
		this.infoList = infoList;
	}
	public void addMissionInfo(MissionInfo info)
	{
		infoList.add(info);
	}

	@Override
	protected void initialize() {
		this.subscribeBroadcast(TickBroadcast.class, new Callback<TickBroadcast>() {
			@Override
			public void call(TickBroadcast c) {
				for (MissionInfo m : infoList)
				{
					if (m.getTimeIssued() == ticks)
					{
						getSimplePublisher().sendEvent(new MissionRecievedEvent(m));
					}
				}
				ticks = c.getTickCount();

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
