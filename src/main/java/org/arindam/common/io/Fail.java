package org.arindam.common.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import org.arindam.common.io.function.PathFilter;
import org.arindam.common.io.function.filters.DirectoryPathFilter;
import org.arindam.common.io.function.filters.FilePathFilter;

public interface Fail {
	
	static UncheckedIOException ioe(String message) {
		throw new UncheckedIOException(new IOException(message));
	}
	static UncheckedIOException ioe(String message, Path path) {
		return ioe("%s | %s".formatted(message, path));
	}
	
	static void ifNot(boolean flag, String message) {
		if( !flag ) ioe(message);
	}
	static void ifNot(boolean flag, String message, Path path) {
		if( !flag ) ioe(message, path);
	}
	
	static void ifUnmatch(PathFilter filter, Path path, String message) {
		ifNot(filter.test(path), message, path);
	}
	
	static void ifNotDirectory(Path directory) {
		ifUnmatch(DirectoryPathFilter.instance, directory, "not a directory");
	}
	static void ifNotFile(Path file) {
		ifUnmatch(FilePathFilter.instance, file, "not a file");
	}
	
}
