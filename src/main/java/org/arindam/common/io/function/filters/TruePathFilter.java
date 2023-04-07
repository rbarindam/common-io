package org.arindam.common.io.function.filters;

import java.nio.file.Path;

import org.arindam.common.io.function.PathFilter;

public enum TruePathFilter implements PathFilter {
	
	/**
	 * the singleton instance,
	 * since there's no parameter involved
	 */
	instance;

	@Override
	public boolean test(Path path) {
		return true;
	}

}
