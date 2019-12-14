package bgu.spl.mics;

public class dumbEvent implements Event<String> {
    private String senderName;

    public dumbEvent(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }
}
