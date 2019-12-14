package bgu.spl.mics.example.messages;

import bgu.spl.mics.Event;

public class ExampleEvent<T> implements Event<T>{

    private T senderName;

    public ExampleEvent(T senderName) {
        this.senderName = senderName;
    }

    public T getSenderName() {
        return senderName;
    }
}