package org.kohsuke.args4j.apt.exceptions;

public class ProcessMethodError extends Error {

	private static final long serialVersionUID = 1L;
	
	public ProcessMethodError(Exception e) {
		super(e);
	}
	
	public ProcessMethodError(String message) {
		super(message);
	}

}
