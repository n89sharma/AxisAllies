package axisallies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameResponse <T>{
    
    Set<String> errors = new HashSet<>();
    T result;
    
    public GameResponse() {

    }

    public GameResponse(T result) {
        this.result = result;
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

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    
}