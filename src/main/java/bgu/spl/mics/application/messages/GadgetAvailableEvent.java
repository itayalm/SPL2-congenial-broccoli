package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class GadgetAvailableEvent implements Event {
    private String name;

    public GadgetAvailableEvent(String name){
        this.name = name;
    }

    public String getId() {
        return name;
    }

    public void setId(String name) {
        this.name = name;
    }
}
