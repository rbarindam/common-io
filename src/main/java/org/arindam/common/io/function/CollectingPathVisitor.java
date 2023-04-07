package org.arindam.common.io.function;

public interface CollectingPathVisitor<R> extends PathVisitor {
	
	R getCollection();
	
}
