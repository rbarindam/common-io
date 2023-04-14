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
public class FileSearchingPathVisitor implements CollectingPathVisitor<Set<Path>>, NoopPathVisitor {
	
	final Set<Path> fileMatched = new LinkedHashSet<>();
	final @NonNull Predicate<Path> fileMatch;
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		return matchAndContinue(file);
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return matchAndContinue(file);
	}

	@Override
	public Set<Path> getCollection() {
		return Collections.unmodifiableSet(fileMatched);
	}
	
	FileVisitResult matchAndContinue(Path file) {
		if( fileMatch.test(file) ) {
			fileMatched.add(file);
		}
		
		return CONTINUE;
	}
	
}
