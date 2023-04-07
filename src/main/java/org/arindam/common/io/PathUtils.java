package org.arindam.common.io;

import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.function.Failable;
import org.arindam.common.io.function.CollectingPathVisitor;
import org.arindam.common.io.function.PathFilter;
import org.arindam.common.io.function.filters.DirectoryPathFilter;
import org.arindam.common.io.function.filters.FilePathFilter;
import org.arindam.common.io.function.filters.TruePathFilter;

public interface PathUtils {
	
	static List<Path> list(Path directory) {
		return list(directory, TruePathFilter.instance);
	}
	static List<Path> listFiles(Path directory) {
		return list(directory, FilePathFilter.instance);
	}
	static List<Path> listFiles(Path directory, PathFilter filter) {
		return list(directory, FilePathFilter.instance.and(filter));
	}
	static List<Path> listDirectories(Path directory) {
		return list(directory, DirectoryPathFilter.instance);
	}
	static List<Path> listDirectories(Path directory, PathFilter filter) {
		return list(directory, DirectoryPathFilter.instance.and(filter));
	}
	static List<Path> list(Path directory, PathFilter filter) {
		Fail.ifNotDirectory(directory);
		return Failable.apply(Files::list, directory)
				.filter(filter)
				.toList();
	}
	
	static List<Path> walk(Path directory, FileVisitOption...options) {
		return walk(directory, TruePathFilter.instance, options);
	}
	static List<Path> walkFiles(Path directory, FileVisitOption...options) {
		return walk(directory, FilePathFilter.instance, options);
	}
	static List<Path> walkFiles(Path directory, PathFilter filter, FileVisitOption...options) {
		return walk(directory, FilePathFilter.instance.and(filter), options);
	}
	static List<Path> walkDirectories(Path directory, FileVisitOption...options) {
		return walk(directory, DirectoryPathFilter.instance, options);
	}
	static List<Path> walkDirectories(Path directory, PathFilter filter, FileVisitOption...options) {
		return walk(directory, DirectoryPathFilter.instance.and(filter), options);
	}
	static List<Path> walk(Path directory, PathFilter filter, FileVisitOption...options) {
		Fail.ifNotDirectory(directory);
		return Failable.apply(Files::walk, directory, options)
				.filter(filter)
				.toList();
	}
	
	static <R> R collect(Path directory, CollectingPathVisitor<R> collector) {
		return collect(directory, Integer.MAX_VALUE, collector);
	}
	static <R> R collect(Path directory, CollectingPathVisitor<R> collector, FileVisitOption...options) {
		return collect(directory, Integer.MAX_VALUE, collector, options);
	}
	static <R> R collect(Path directory, int maxDepth, CollectingPathVisitor<R> collector) {
		return collect(directory, maxDepth, collector, EnumSet.noneOf(FileVisitOption.class));
	}
	static <R> R collect(Path directory, int maxDepth, CollectingPathVisitor<R> collector, FileVisitOption...options) {
		return collect(directory, maxDepth, collector, Set.of(options));
	}
	static <R> R collect(Path directory, int maxDepth, CollectingPathVisitor<R> collector, Set<FileVisitOption> options) {
		Fail.ifNotDirectory(directory);
		Failable.run(() -> Files.walkFileTree(directory, options, maxDepth, collector));
		return collector.getCollection();
	}
	
}
