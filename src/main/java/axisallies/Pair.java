package axisallies;

import java.util.Arrays;
import java.util.List;

import static axisallies.GameResponse.createLumpedResponse;
import static axisallies.GameResponse.successfulGameResponseWithPayload;
import static axisallies.GameResponse.unsuccessfulGameResponse;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

//TODO: equals will break on null. fix later.
public class Pair<L, R> {

    private L left;
    private R right;

    public static <A, B> Pair<A, B> pair(A left, B right) {
        return new Pair<>(left, right);
    }

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Pair that = (Pair) o;
        if (that.getLeft().getClass() != this.getLeft().getClass()) return false;
        if (that.getRight().getClass() != this.getLeft().getClass()) return false;

        return this.getLeft().equals(that.getLeft()) && this.getRight().equals(that.getRight());
    }

    @Override
    public int hashCode() {
        return this.getRight().hashCode() + this.getLeft().hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", this.left.toString(), this.right.toString());
    }

    /**
     * Only handles strings that are of the form "(int, int)" or "int, int"
     *
     * @param string
     * @return
     */
    public static GameResponse<Pair<Integer, Integer>> integerPair(String string) {
        var numbers = string.replaceAll("[()]", "").split(",");
        var message = String.format("Cannot make a pair from %s. Pair expecting 2 numbers with the format: (int, int) or int, int.", string);
        if (numbers.length != 2) {
            @SuppressWarnings("unchecked")
            var response = (GameResponse<Pair<Integer, Integer>>) unsuccessfulGameResponse(singletonList(message));
            return response;
        } else {
            try {
                var left = Integer.parseInt(numbers[0].trim());
                var right = Integer.parseInt(numbers[1].trim());
                return successfulGameResponseWithPayload(pair(left, right));
            } catch (NumberFormatException e) {

                @SuppressWarnings("unchecked")
                var response = (GameResponse<Pair<Integer, Integer>>) unsuccessfulGameResponse(singletonList(message));
                return response;
            }
        }
    }

    public static GameResponse<List<Pair<Integer, Integer>>> integerPairs(String string) {
        var responses = Arrays.stream(string.split(";"))
            .map(String::trim)
            .map(Pair::integerPair)
            .collect(toList());
        return createLumpedResponse(responses);
    }

}
