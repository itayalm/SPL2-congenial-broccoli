package bgu.spl.mics;

public class dumbBroadcast implements Broadcast {
    private String senderId;

    public dumbBroadcast(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderId() {
        return senderId;
    }
}
