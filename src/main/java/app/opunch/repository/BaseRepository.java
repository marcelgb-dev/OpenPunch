package app.opunch.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository {

    // Utilities
    // Returns an empty Optional of type T if there are no results, or the full Optional with the results if they exist
    default <T> Optional<T> returnOptional(List<T> results) {

        if (results.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(results.get(0));
        }
    }
}
