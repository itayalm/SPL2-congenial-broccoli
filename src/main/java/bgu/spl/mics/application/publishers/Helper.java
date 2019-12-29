package bgu.spl.mics.application.publishers;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.LinkedList;
import java.util.TimerTask;

public class Helper extends TimerTask{
    private int tickNum;
    private MessageBroker mb;
    private int duration;
    public Helper(int duration)
    {
        tickNum = 0;
        mb = MessageBrokerImpl.getInstance();
        this.duration = duration;
    }

    @Override
    public void run() {
        if (tickNum < duration) {
            mb.sendBroadcast(new TickBroadcast(tickNum));
            System.out.println(tickNum);
            tickNum++;
        }
        else {
            mb.sendBroadcast(new TerminateBroadcast());
            this.cancel();
        }
    }

    public int getTickNum() {
        return tickNum;
    }
}
