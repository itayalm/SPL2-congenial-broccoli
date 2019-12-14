package bgu.spl.mics;

import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.publishers.ExampleMessageSender;
import bgu.spl.mics.example.subscribers.ExampleBroadcastSubscriber;
import bgu.spl.mics.example.subscribers.ExampleEventHandlerSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {

    MessageBroker MB;
//    Subscriber broadcastSub;
//    Subscriber  eventSub;
//    Subscriber messagePub;
    Subscriber Sub;
    dumbEvent E;
    dumbBroadcast B;
    @BeforeEach
    public void setUp(){
        MB = new MessageBrokerImpl();
        B = new dumbBroadcast("broadcastTest");
        Sub = new dumbSub("test");
        E = new dumbEvent("eventTest");
    }

    /**
     * Test method for messageBroker getInstance.
     */
    @Test
    public void testGetInstance() {
        MessageBroker temp = MessageBrokerImpl.getInstance();
        assertEquals(true, temp != null);
    }
    /**
     * Test method for MessageBrokerImpl subscribeEvent .
     */
    @Test public void testSubsribeEvent() {// add a thread
        MB.register(Sub);
        MB.subscribeEvent(E.getClass(), Sub);
        boolean M = true;

        Future<String> f = MB.sendEvent(E);
        Thread T = new Thread(() -> {
            try{
                assertEquals(true , MB.awaitMessage(Sub) != null);
            }
            catch(Exception e)
            {

            }
        });
        T.start();
        MB.unregister(Sub);


    }
    /**
     * Test method for MessageBrokerImpl subscribeBroadcast .
     */
    @Test public void testSubscribeBroadcast() {
        MB.register(Sub);
        MB.subscribeBroadcast( B.getClass(), Sub);
        Future<String> f = MB.sendEvent(E);
        Thread T = new Thread(() -> {
            try{
                assertEquals(true , MB.awaitMessage(Sub) != null);
            }
            catch(Exception e)
            {

            }
        });
        T.start();
        MB.unregister(Sub);



    }
    /**
     * Test method for MessageBrokerImpl complete .
     */
    @Test public void testComplete() {
        MB.register(Sub);
        MB.subscribeEvent(E.getClass(), Sub);
        Future<String> f = MB.sendEvent(E);
        assertEquals(false, f.isDone());
        MB.complete(E , "done");
        assertEquals(true, f.isDone());
        assertEquals(true, f.get() == "done");
        MB.unregister(Sub);
    }
    /**
     * Test method for MessageBrokerImpl sendBroadcast .
     */
    @Test public void testSendBroadcast() {
        MB.register(Sub);
        MB.subscribeBroadcast(B.getClass(),Sub);
        MB.sendBroadcast(B);
        try {
            Message M = MB.awaitMessage(Sub);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}
