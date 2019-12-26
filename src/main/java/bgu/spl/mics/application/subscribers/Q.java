package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inven;
	private GadgetAvailableEvent gadgets;
	public Q(String name) {
		super(name);
		inven = Inventory.getInstance();
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		//need Event implementation for this
		// TODO Implement this
		
	}

}
