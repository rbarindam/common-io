package org.arindam.common.io.function.visitors;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

import org.arindam.common.io.function.CollectingPathVisitor;
import org.arindam.common.io.function.visitors.FileDeletingPathVisitor.Deleted;

public class FileDeletingPathVisitor implements CollectingPathVisitor<Deleted>, NoopPathVisitor {
	
	final AtomicLong dirDeleted = new AtomicLong(0L);
	final AtomicLong fileDeleted = new AtomicLong(0L);

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		return countAndContinue(fileDeleted, Files.deleteIfExists(file));
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return countAndContinue(fileDeleted, deleteSafe(file));
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return countAndContinue(dirDeleted, deleteSafe(dir));
	}

	@Override
	public Deleted getCollection() {
		return new Deleted(dirDeleted.get(), fileDeleted.get());
	}
	
	FileVisitResult countAndContinue(AtomicLong counter, boolean deleted) {
		if( deleted ) {
			counter.incrementAndGet();
		}
		
		return CONTINUE;
	}
	
	/**
	 * handled both the cases where something's wrong with deleting the file
	 * and where the directory is not empty (i.e. some file inside couldn't be deleted)
	 * @param file the file to attempt for a deletion
	 * @return true if delete was successful, false is failed for whatever reason
	 */
	boolean deleteSafe(Path file) {
		try {
			Files.delete(file);
			return true;
		} catch( Exception e ) {
			return false;
		}
	}
	
	public static record Deleted(long directories, long files) {}
	
}
