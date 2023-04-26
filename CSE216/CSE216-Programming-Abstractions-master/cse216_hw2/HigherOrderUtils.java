import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.List;
import java.util.function.Function;

public class HigherOrderUtils {


    interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();
    }

    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "add";
        }

        @Override
        public Double apply(Double o, Double o2) {
            return o + o2;
        }

    };


    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "diff";
        }

        public Double apply(Double o, Double o2) {
            return o - o2;
        }

    };

    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "mult";
        }

        public Double apply(Double o, Double o2) {
            return o * o2;
        }

    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        public Double apply(Double o, Double o2) {
            try {
                if ((Double) o2 == 0) throw new ArithmeticException();
                Double o3 = o / o2;
                return o3;

            } catch (ArithmeticException e) {
                System.out.println("Cannot divide by 0.");
                return null;
            }
        }

    };


    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions) {
        if (args.size() != bifunctions.size() + 1) {
            System.out.println("Cannot zip. Args list is not long enough.");
            return null;
        } else {
            for (int i = 0; i < bifunctions.size(); i++) {
                T result = (T) bifunctions.get(i).apply(args.get(i), args.get(i + 1));
                if (result == null) {
                    return null;
                } else
                    args.set(i + 1, result);

                if (i == bifunctions.size() - 1) {
                    return result;
                }
            }
        }
        return null;
    }

    static class FunctionComposition<T, U, R> {
        public BiFunction<Function<T, U>, Function<U, R>, Function<T, R>> composition = new BiFunction<Function<T, U>, Function<U, R>, Function<T, R>>() {
            @Override
            public Function<T, R> apply(Function<T, U> tuFunction, Function<U, R> urFunction) {
                return tuFunction.andThen(urFunction);
            }
        };


    }
}
