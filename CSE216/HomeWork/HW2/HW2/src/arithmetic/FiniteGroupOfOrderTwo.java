package arithmetic;

/**
 * * @Date: 4/6/23 9:39 PM
 */

enum PlusOrMinusOne {
    pone(1),
    none(-1);

    public final int fig;

    private PlusOrMinusOne(int e) {
        this.fig = e;
    }

    @Override
    public String toString() {
        return String.valueOf(this.fig);
    }

}

public class FiniteGroupOfOrderTwo implements Group<PlusOrMinusOne> {

    @Override
    public PlusOrMinusOne binaryOperation(PlusOrMinusOne x, PlusOrMinusOne y) {

        if (x.fig * y.fig == 1) {
            return PlusOrMinusOne.pone;
        }
        return PlusOrMinusOne.none;

    }

    @Override
    public PlusOrMinusOne identity() {
        return PlusOrMinusOne.pone;
    }

    @Override
    public PlusOrMinusOne inverseOf(PlusOrMinusOne x) {
        return x;

    }

    @Override
    public PlusOrMinusOne exponent(PlusOrMinusOne x, int k) {
        if (x == PlusOrMinusOne.pone) {
            return x;
        }
        double pow = Math.pow(x.fig, k);
        return pow==1?PlusOrMinusOne.pone:PlusOrMinusOne.none;
    }
}

