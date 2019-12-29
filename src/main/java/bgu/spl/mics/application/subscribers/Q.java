package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Pair;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inven;
	private int timeTick;
	public Q(String name) {
		super(name);
		inven = Inventory.getInstance();
		timeTick = 0;
	}

	@Override
	protected void initialize() {
		this.subscribeBroadcast(TickBroadcast.class, new Callback<TickBroadcast>() {
			@Override
			public void call(TickBroadcast c) {
				timeTick = c.getTickCount();
			}
		});
		this.subscribeEvent(GadgetAvailableEvent.class, new Callback<GadgetAvailableEvent>() {
			@Override
			public void call(GadgetAvailableEvent c) {
				String gadgetNum = c.getId();
				Boolean available = inven.getItem(gadgetNum);
				complete(c, new Pair<Integer, Boolean>(timeTick, available));
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
