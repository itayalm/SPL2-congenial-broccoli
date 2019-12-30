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
	private int serialNumber;

	public M(String name) {
		super(name);
		timeTick = 0;
		diary = Diary.getInstance();
		serialNumber = Integer.parseInt(name.split(" ")[1]);
	}

	@Override
	protected void initialize() throws InterruptedException {
		this.subscribeBroadcast(TickBroadcast.class, new Callback<TickBroadcast>() {
			@Override
			public void call(TickBroadcast c) {
				timeTick = c.getTickCount();
			}
		});
		this.subscribeEvent(MissionRecievedEvent.class, new Callback<MissionRecievedEvent>() {
			@Override
			public void call(MissionRecievedEvent c) {
				diary.incrementTotal();
				Future<Boolean> canSendAgents = new Future<Boolean>();
				Report report = new Report();//create Report
				MissionInfo info = c.getInfo();
				long start = System.currentTimeMillis();
				report.setTimeIssued(timeTick);
				report.setMissionName(info.getMissionName());
				report.setM(serialNumber);
				report.setAgentsSerialNumbers(info.getSerialAgentsNumbers());
				report.setTimeIssued(timeTick);
				report.setGadgetName(info.getGadget());// maybe this should be the timeout get
				System.out.println("sending agents available event");
				Future<Trio<String, List<String>, Boolean>> agentFuture = getSimplePublisher().sendEvent(new AgentsAvailableEvent(info.getSerialAgentsNumbers(), info.getDuration(), canSendAgents));
				if (agentFuture == null) {
					System.out.println("Agent Future is terminating, mission : " + c.getInfo().getMissionName());
					terminate();
					return;
				}
				System.out.println("resolving agents future ");
				canSendAgents.resolve(true);
				System.out.println("getting agent Future , mission : " + c.getInfo().getMissionName());
				Trio<String, List<String>, Boolean> trio = agentFuture.get(); // wait occurs here

				System.out.println("after agentFuture.get , mission : " + c.getInfo().getMissionName());
				if (!trio.getThird()) { // if the agent serial isn't right
					System.out.println("agent serial ain't right , , mission : " + c.getInfo().getMissionName());
					return;
				}
				System.out.println("setAgentNames , , mission : s" + c.getInfo().getMissionName());
				report.setAgentsNames(trio.getSecond()); // maybe this should be the timeout get
				report.setMoneypenny(Integer.parseInt(trio.getFirst().split(" ")[1]));
				System.out.println("before calling GadgetAvailableEvent");
				Future<Pair<Integer, Boolean>> gadgetFuture = getSimplePublisher().sendEvent(new GadgetAvailableEvent(info.getGadget()));
				if (gadgetFuture == null) {
					System.out.println("Gadget Future is terminating, mission : " + c.getInfo().getMissionName());
					terminate();
					return;
				}

				if (!gadgetFuture.get().getSecond()) { // if the gadget name isnt right
					System.out.println("gadget name ain't right , mission : " + c.getInfo().getMissionName());
					return;
				}
				int qTime = gadgetFuture.get().getFirst();
				report.setQTime(qTime);
				int elapsed = (int) (System.currentTimeMillis() - start) / 100;
				timeTick = timeTick + elapsed;
				if (timeTick > info.getTimeExpired()) {
					System.out.println("time passed expired , mission : " + c.getInfo().getMissionName());
					return;
				}
				System.out.println("added report , mission : " + c.getInfo().getMissionName());
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
