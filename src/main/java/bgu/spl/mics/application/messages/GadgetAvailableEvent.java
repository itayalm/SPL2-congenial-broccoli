package bgu.spl.mics.application.messages;

public class GadgetAvailableEvent {
    private int id;

    public GadgetAvailableEvent(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
