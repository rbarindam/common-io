package org.arindam.common.io.function;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface PathFilter extends Predicate<Path> {
	
	@Override
	boolean test(Path path);

    default PathFilter and(PathFilter other) {
        Objects.requireNonNull(other);
        return t -> test(t) && other.test(t);
    }

    @Override
	default PathFilter negate() {
        return t -> !test(t);
    }

    default PathFilter or(PathFilter other) {
        Objects.requireNonNull(other);
        return t -> test(t) || other.test(t);
    }
    
    static PathFilter not(PathFilter target) {
        Objects.requireNonNull(target);
        return target.negate();
    }
    
}
