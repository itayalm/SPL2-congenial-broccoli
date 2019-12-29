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
				timeTick = c.getTickCount();
				System.out.println("Tick count: "+c.getTickCount());

			}
		});
		this.subscribeEvent(MissionRecievedEvent.class, new Callback<MissionRecievedEvent>() {

			@Override
			public void call(MissionRecievedEvent c) {
				System.out.println("asd");
				Report report = new Report();//create Report
				MissionInfo info = c.getInfo();
				long start = System.currentTimeMillis();
				report.setTimeIssued(timeTick);
				report.setMissionName(info.getMissionName());
				report.setM(Integer.parseInt(getName()));
				report.setAgentsSerialNumbers(info.getSerialAgentsNumbers());
				report.setTimeIssued(timeTick);
				report.setGadgetName(info.getGadget());// maybe this should be the timeout get
				Future<Trio<String, List<String>,Boolean>> agentFuture = getSimplePublisher().sendEvent(new AgentsAvailableEvent(info.getSerialAgentsNumbers(), info.getDuration()));
				Trio<String, List<String>, Boolean> trio = agentFuture.get(); // wait occurs here
				if (!trio.getThird()) { // if the agent serial isnt right
					diary.incrementTotal();
					return;
				}
				report.setAgentsNames(trio.getSecond()); // maybe this should be the timeout get
				report.setMoneypenny(Integer.parseInt(trio.getFirst()));
				Future<Pair<Integer, Boolean>> gadgetFuture = getSimplePublisher().sendEvent(new GadgetAvailableEvent(info.getGadget()));
				int qTime = gadgetFuture.get().getFirst();
				if (!gadgetFuture.get().getSecond()) { // if the gadget name isnt right
					diary.incrementTotal();
					return;
				}
				report.setQTime(qTime);
				int elapsed = (int)(System.currentTimeMillis() - start)/100;
				timeTick = timeTick + elapsed;
				if (timeTick > info.getTimeExpired()) {
					diary.incrementTotal();
					return;
				}
				diary.addReport(report);
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
