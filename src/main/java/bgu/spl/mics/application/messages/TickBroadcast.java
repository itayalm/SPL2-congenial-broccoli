package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

public class TickBroadcast implements Broadcast {
    private int tickCount;

    public TickBroadcast(int tickCount){
        this.tickCount = tickCount;

    }

    public int getTickCount() {
        return tickCount;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }
}
