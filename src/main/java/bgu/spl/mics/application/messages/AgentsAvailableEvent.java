package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

import java.util.List;

public class AgentsAvailableEvent implements Event {
    private List<String> serials;
    private int duration;
    private Future<Boolean> canSendAgents;

     public AgentsAvailableEvent(List<String> serials, int duration, Future<Boolean> canSendAgents){
        this.serials = serials;
        this.duration = duration;
        this.canSendAgents = canSendAgents;
    }

    public List<String> getSerials() {
        return serials;
    }

    public void setSerials(List<String> serials) {
        this.serials = serials;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Future<Boolean> getCanSendAgents() {
        return canSendAgents;
    }
}
