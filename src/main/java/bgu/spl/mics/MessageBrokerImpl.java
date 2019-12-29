package bgu.spl.mics;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	/**
	 * Retrieves the single instance of this class.
	 */
	private static MessageBroker MB = new MessageBrokerImpl();

	Map<Class<? extends Message> , BlockingQueue<Subscriber>> Topic;
	Map<Event,Future> futures;
	Map<Subscriber, BlockingQueue<Message>> Subscribers;
	private MessageBrokerImpl()
	{
		Topic = new HashMap<>();
		futures = new ConcurrentHashMap<>();
		Subscribers = new ConcurrentHashMap<>();
	}

	public static MessageBroker getInstance() {
		if (MB == null)
		{
			MB = new MessageBrokerImpl();
		}
		return MB;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		BlockingQueue<Subscriber> Subs;
		synchronized (Topic) {
			if (Topic.get(type) != null) {
				System.out.println("subscribing OLD, event type : " + type.getName() + "subscriber :" + m.getName());
				Subs = Topic.get(type);
				Subs.add(m);
			} else {
				System.out.println("subscribing NEW , event type : " + type.getName() + "subscriber :" + m.getName());
				Subs = new LinkedBlockingQueue<Subscriber>();
				Subs.offer(m);
				Topic.put(type, Subs);
			}
		}

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		BlockingQueue<Subscriber> Subs;
		synchronized (Topic) {
			if (Topic.get(type) != null) {
				Subs = Topic.get(type);
				Subs.add(m);
			} else {
				Subs = new LinkedBlockingQueue<Subscriber>();
				Subs.offer(m);
				Topic.put(type, Subs);
			}
		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> f;
		try
		{
			f = futures.get(e);
			f.resolve(result);
		}
		catch (Exception ex)
		{}

	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		if (Topic.get(b.getClass()) != null) {
			for (Subscriber s :
					Topic.get(b.getClass())) {

//				System.out.println("Sending Broadcast: "+b);
				Subscribers.get(s).offer(b);
			}
		}

	}


	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
		Future<T> f;
		if (Topic.get(e.getClass()).isEmpty() != true) {
			try {
				Subscriber s = Topic.get(e.getClass()).take();
				Topic.get(e.getClass()).offer(s);
				Subscribers.get(s).offer(e);
				f = futures.get(e);
				return f;
			}
			catch(Exception ex){return null;}
		}
		else
		{
			return null;
		}
	}

	@Override
	public void register(Subscriber m) {
		if (m != null) {
			if (Subscribers.get(m) == null)
				Subscribers.put(m, new LinkedBlockingQueue<Message>());
		}
	}

	@Override
	public synchronized void unregister(Subscriber m) {
		if (Subscribers.get(m) != null) {
			Subscribers.remove(m);
			for (Map.Entry<Class<? extends Message> , BlockingQueue<Subscriber>> entry : Topic.entrySet()) {
				entry.getValue().remove(m);
				if (entry.getValue().isEmpty())
				{
					Topic.remove(entry);
				}
			}
		}
	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		try
		{
			Message Mes = Subscribers.get(m).take();
			return Mes;
		}
		catch(Exception e)
		{
			throw new IllegalStateException(e);
		}

	}

}
