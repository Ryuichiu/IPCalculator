package de.bs14.ipcalc.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply (T message, U title, V messageType);

    default <X> TriFunction <T, U, V, X> andThen(Function<? super R, ? extends X> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
