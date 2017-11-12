package org.kohsuke.args4j.apt.exceptions;

public class RunException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public RunException(Exception e) {
		super(e);
	}
}
