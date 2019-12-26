package bgu.spl.mics.application.publishers;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;

import java.util.LinkedList;
import java.util.TimerTask;

public class Helper extends TimerTask{
    private TickBroadcast tick;
    private int tickNum;
    private MessageBroker mb;
    public Helper()
    {
        this.tick = new TickBroadcast();
        tickNum = 0;
        mb = MessageBrokerImpl.getInstance();
    }

    @Override
    public void run() {
        mb.sendBroadcast(tick);// TODO need to implement Broadcast after itay finished tickBroadcast
        tickNum++;
    }

    public int getTickNum() {
        return tickNum;
    }
}
