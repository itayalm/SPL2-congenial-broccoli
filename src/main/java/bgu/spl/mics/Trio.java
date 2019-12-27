package bgu.spl.mics;

public class Trio<S,T,K>{
    private S first;
    private T second;
    private K third;
    public Trio(S first , T second, K third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public S getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public K getThird() { return third; }
}
