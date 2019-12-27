package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

public class MissionRecievedEvent implements Event {
    private MissionInfo info;

    public MissionRecievedEvent(MissionInfo info){
        this.info = info;

    }

    public MissionInfo getInfo() {
        return info;
    }

    public void setInfo(MissionInfo info) {
        this.info = info;
    }
}
