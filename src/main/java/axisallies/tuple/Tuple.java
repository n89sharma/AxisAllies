package axisallies.tuple;

import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;

public class Tuple<L, R> extends Pair<L, R> {

    private static final long serialVersionUID = -7794098087363848452L;
    private final L left;
    private final R right;

    public Tuple(final L left, final R right) {
        super();
        this.left = left;
        this.right = right;
    }

    public static <L, R> Tuple<L, R> of(final L left, final R right) {
        return new Tuple<>(left, right);
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public R setValue(R value) {
        return value;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map.Entry<?, ?>) {
            final Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
            boolean keyAndValuesMatch =  Objects.equals(getKey(), other.getKey())
                && Objects.equals(getValue(), other.getValue());
            boolean keyAndValuesSwitched = Objects.equals(getKey(), other.getValue())
                && Objects.equals(getValue(), other.getKey());
            return keyAndValuesMatch || keyAndValuesSwitched;
            
        }
        return false;
    }

}