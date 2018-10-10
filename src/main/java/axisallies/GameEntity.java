package axisallies;

import java.util.UUID;

public class GameEntity {
    private UUID uuid = UUID.randomUUID();

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        return this.uuid.equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
