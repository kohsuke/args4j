package org.kohsuke.args4j;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

/**
 * @see https://args4j.dev.java.net/issues/show_bug.cgi?id=10
 */
@SuppressWarnings("unused")
public class Issue10Test extends TestCase {
	@Option(name="-enum", required=false, usage="Enum2")
	private Enum crash;
	
	enum Enum { 
		THIS, ENUM, HAS, A, VERY, LONG, USAGE, LINE,
		BECAUSE, OF, ITS, HUGE, LIST, Of, VALUES,
		SO, IT, WILL, CRASH
	}
	
	public void testIssue10() {
		CmdLineParser parser = new CmdLineParser(this);
		parser.printUsage(new ByteArrayOutputStream());
		// StringIndexOutOfBoundsException with index < 0
		/*
		 * CmdLineParser.printOption() calculates the widht of the four colums:
		 * - 1st column: just one indenting space - length=1
		 * - 3rd column: just separating " : " - length=3
		 * - 2nd column: length of the 'name' and 'metadata' of the option
		 * - 4th column: rest : usageWidht (80) minus length of the other three columns
		 * In this case 
		 * - 'name' is "-enum"
		 * - 'metadata' is the list of all Enum-values
		 * So this list is bigger than 76 (80-1-3) and therefore the width of the 4th columns 
		 * gets negative - which isn't the right think ;-)
		 * 
		 * a)Tweaking: just set the 4th length to 0 if it was positive:
		 * + fast to implement
		 * - long metadata break the maximum columns width
		 * - usage information is hidden because of columnwidth=0
		 * 
		 * b)Changing of the output layout using line wrappers
		 * Line wrapping should be done in nameAndMetadata AND in usage (columns 2+4).
		 * 
		 * BTW: maybe the output could be made more easily with the Java5 printf method ;-)
		 * 
		 */
	}

}
