package org.arindam.common.io.function.filters;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.function.Failable;
import org.arindam.common.io.function.PathFilter;

public enum FilePathFilter implements PathFilter {
	
	/**
	 * the singleton instance,
	 * since there's no parameter involved
	 */
	instance;

	@Override
	public boolean test(Path path) {
		return Failable.test(Files::isRegularFile, path);
	}

}
