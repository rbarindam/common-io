package org.arindam.common.io.function.visitors;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.arindam.common.io.function.CollectingPathVisitor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectorySearchingPathVisitor implements CollectingPathVisitor<Set<Path>>, NoopPathVisitor {
	
	final Set<Path> dirMatched = new LinkedHashSet<>();
	final @NonNull Predicate<Path> dirMatch;
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return matchAndContinue(dir);
	}

	@Override
	public Set<Path> getCollection() {
		return Collections.unmodifiableSet(dirMatched);
	}
	
	FileVisitResult matchAndContinue(Path file) {
		if( dirMatch.test(file) ) {
			dirMatched.add(file);
		}
		
		return CONTINUE;
	}
	
}
