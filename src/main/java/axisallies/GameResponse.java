package axisallies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameResponse {

    Set<String> errors = new HashSet<>();

    public GameResponse() {

    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public void addErrors(Collection<String> errors) {
        this.errors.addAll(errors);
    }

    public boolean hasErrors() {
        return this.errors.isEmpty();
    }
}