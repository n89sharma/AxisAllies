package axisallies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GameResponse {

    private boolean success = true;
    private List<GameError> gameErrors = new ArrayList<>();

    public void setUnsuccessful() {
        this.success = false;
    }

    public boolean isSuccessful() {
        return this.success;
    }

    public List<String> getAllMessages() {
        return this.gameErrors
            .stream()
            .map(GameError::getMessages)
            .flatMap(Collection::stream)
            .collect(toList());
    }

    public void addError(GameError gameError) {
        this.gameErrors.add(gameError);
    }

    public static class GameError {

        private List<String> message;

        public GameError(List<String> message) {
            this.message = message;
        }

        public List<String> getMessages() {
            return this.message;
        }
    }
}