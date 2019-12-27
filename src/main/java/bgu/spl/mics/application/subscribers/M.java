package bgu.spl.mics.application.subscribers;
import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.List;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private Diary diary;
	private int timeTick;
	public M(String name) {
		super(name);
		timeTick = 0;
		diary = Diary.getInstance();
	}

	@Override
	protected void initialize() throws InterruptedException {
		this.subscribeBroadcast(TickBroadcast.class, new Callback<TickBroadcast>() {
			@Override
			public void call(TickBroadcast c) {
				timeTick++;
			}
		});
		this.subscribeEvent(MissionRecievedEvent.class, new Callback<MissionRecievedEvent>() {
			Report report = new Report();//create Report
			@Override
			public void call(MissionRecievedEvent c) {

				MissionInfo info = c.getInfo();
				report.setTimeIssued(timeTick);
				report.setMissionName(info.getMissionName());
				report.setM(Integer.parseInt(getName()));
				report.setAgentsSerialNumbers(info.getSerialAgentsNumbers());
				report.setTimeIssued(timeTick);
				report.setGadgetName(info.getGadget());// maybe this should be the timeout get
				Future<Trio<String, List<String>,Boolean>> agentFuture = getSimplePublisher().sendEvent(new AgentsAvailableEvent(info.getSerialAgentsNumbers(), info.getDuration()));
				Trio<String, List<String>, Boolean> trio = agentFuture.get(); // wait occurs here
				if (!trio.getThird()) { // if the agent serial isnt right
					abortMission(info);
					return;
				}
				report.setAgentsNames(trio.getSecond()); // maybe this should be the timeout get
				report.setMoneypenny(Integer.parseInt(trio.getFirst()));
				Future<Pair<Integer, Boolean>> gadgetFuture = getSimplePublisher().sendEvent(new GadgetAvailableEvent(info.getGadget()));
				int qTime = gadgetFuture.get().getFirst();
				if (!gadgetFuture.get().getSecond()) { // if the gadget name isnt right
					abortMission(info);
					return;
				}
				report.setQTime(qTime);
				if (timeTick > info.getTimeExpired()) { // probably need to do this with a local time function cause we wont recieve broadcasts while this is happening
					abortMission(info);
					return;
				}
				diary.addReport(report);
				getSimplePublisher().sendEvent(new ReleaseAgentsEvent(info.getSerialAgentsNumbers()));

			}

			public void abortMission(MissionInfo info)
			{
				getSimplePublisher().sendEvent(new ReleaseAgentsEvent(info.getSerialAgentsNumbers()));
				diary.incrementTotal();
			}
		});

	}

}
