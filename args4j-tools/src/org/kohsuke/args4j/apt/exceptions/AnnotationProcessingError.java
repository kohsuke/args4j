package org.kohsuke.args4j.apt.exceptions;

public class AnnotationProcessingError extends Error {
	
	private static final long serialVersionUID = 1L;

	public AnnotationProcessingError(Exception e) {
		super(e);
	}
}
