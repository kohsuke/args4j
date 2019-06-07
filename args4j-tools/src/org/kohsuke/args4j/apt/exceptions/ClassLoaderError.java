package org.kohsuke.args4j.apt.exceptions;

public class ClassLoaderError extends Error {

	private static final long serialVersionUID = 1L;
	
	public ClassLoaderError(Exception e) {
		super(e);
	}

}
