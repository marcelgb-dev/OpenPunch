package app.opunch.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository {

    // Utilities
    default <T> Optional<T> returnOptional(List<T> results) {

        if (results.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(results.get(0));
        }
    }
}
