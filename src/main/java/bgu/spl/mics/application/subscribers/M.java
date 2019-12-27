package bgu.spl.mics.application.subscribers;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private MissionRecievedEvent mission;
	private AgentsAvailableEvent agents;
	private GadgetAvailableEvemt gadget;
	private int timeTick;
	private MessageBroker mb;
	public M(String name) {
		super(name);
		mb = MessageBrokerImpl.getInstance();
		// TODO Implement this, need event implemantation
	}

	@Override
	protected void initialize() throws InterruptedException {
		Message m = mb.awaitMessage(this);
		if(/* m is a timeTick*/)
		{

		}
		else//need event implemantation
		{

		}

	}

}
