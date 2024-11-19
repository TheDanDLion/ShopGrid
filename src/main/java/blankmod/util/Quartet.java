package blankmod.util;

public class Quartet<A, B, C, D> {
    private A first;
    private B second;
    private C third;
    private D fourth;

    public Quartet(A first, B second, C third, D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public C getThird() {
        return third;
    }

    public void setThird(C third) {
        this.third = third;
    }

    public D getFourth() {
        return fourth;
    }

    public void setFourth(D fourth) {
        this.fourth = fourth;
    }

    @Override
    public String toString() {
        return "FourTypeTuple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
                '}';
    }
}