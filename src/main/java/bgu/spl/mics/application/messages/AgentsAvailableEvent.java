package bgu.spl.mics.application.messages;

import java.util.List;

public class AgentsAvailableEvent {
    private List<String> serials;
    private int duration;

    private AgentsAvailableEvent(List<String> serials, int duration){
        this.serials = serials;
        this.duration = duration;
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
}
