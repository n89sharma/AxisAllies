package axisallies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class GameResponse<T> {

    private final T payload;
    private boolean success;
    private List<GameError> gameErrors = new ArrayList<>();

    public static GameResponse successfulGameResponse() {
        return new GameResponse(true);
    }

    private GameResponse(boolean success) {
        this.success = success;
        this.payload = null;
    }

    public static GameResponse unsuccessfulGameResponse(List<String> message) {
        return new GameResponse(message);
    }

    private GameResponse(List<String> message) {
        this.success = false;
        this.gameErrors.add(new GameError(message));
        this.payload = null;
    }

    public static <U> GameResponse<U> successfulGameResponseWithPayload(U payload) {
        return new GameResponse<>(payload);
    }

    private GameResponse(T payload) {
        this.success = true;
        this.payload = payload;
    }

    public static <U> GameResponse<U> unsuccessfulGameResponseWithPayloadAndMessage(
        List<List<String>> message,
        U payload) {

        return new GameResponse<>(false, message, payload);
    }

    private GameResponse(boolean success, List<List<String>> messages, T payload) {
        this.success = success;
        this.gameErrors = messages.stream().map(GameError::new).collect(toList());
        this.payload = payload;
    }

    public boolean isSuccessful() {
        return this.success;
    }

    public T getPayload() {
        return this.payload;
    }

    public static <U> GameResponse<List<U>> createLumpedResponse(Collection<GameResponse<U>> responses) {
        var success = responses.stream().allMatch(GameResponse::isSuccessful);
        var payload = responses.stream().filter(GameResponse::isSuccessful).map(GameResponse::getPayload).collect(toList());
        if(success) {
            return successfulGameResponseWithPayload(payload);
        }
        else {
            var message = responses.stream().filter(r -> !r.isSuccessful()).map(GameResponse::getAllMessages).collect(toList());
            return unsuccessfulGameResponseWithPayloadAndMessage(message, payload);
        }
    }

    public List<String> getAllMessages() {
        return this.gameErrors
            .stream()
            .map(GameError::getMessages)
            .flatMap(Collection::stream)
            .collect(toList());
    }

    public <U> GameResponse<U> map(Function<T, GameResponse<U>> mapper) {
        if(this.success) {
            try{
                return mapper.apply(this.payload);
            }
            catch(Exception e){
                this.getAllMessages().add(e.getMessage());
                @SuppressWarnings("unchecked")
                GameResponse<U> gameResponse = (GameResponse<U>) unsuccessfulGameResponse(this.getAllMessages());
                return gameResponse;
            }
        }
        else {
            @SuppressWarnings("unchecked")
            GameResponse<U> gameResponse = (GameResponse<U>) unsuccessfulGameResponse(this.getAllMessages());
            return gameResponse;
        }
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