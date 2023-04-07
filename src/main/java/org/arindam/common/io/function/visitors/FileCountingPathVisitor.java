package org.arindam.common.io.function.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

import org.arindam.common.io.function.CollectingPathVisitor;

public class FileCountingPathVisitor implements CollectingPathVisitor<Long>, NoopPathVisitor {
	
	final AtomicLong counter = new AtomicLong(0L);
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		return countAndContinue();
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return countAndContinue();
	}
	
	@Override
	public Long getCollection() {
		return counter.get();
	}
	
	private FileVisitResult countAndContinue() {
		counter.incrementAndGet();
		return FileVisitResult.CONTINUE;
	}
	
}
