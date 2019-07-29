package org.kohsuke.args4j;

import org.junit.Assert;
import org.junit.Test;


public class IgnoreUndefinedOptionsDisabledTest extends Args4JTestBase<IgnoreUndefinedOptionsDisabledTest> {

	@Option(name = "-one")
	public String one;

	@Option(name = "-two")
	public String two;


	@Override
	public IgnoreUndefinedOptionsDisabledTest getTestObject() {
		return this;
	}


	protected CmdLineParser createParser() {
		ParserProperties parserProperties = ParserProperties.defaults();
		parserProperties.withIgnoreUndefinedOptions(false);
		return new CmdLineParser(testObject, parserProperties);
	}


	public void test() {

		try {
			parser.parseArgument("-one", "value_one", "-two", "value_two", "-three", "value_three");
			Assert.fail("Undefined option '-three' should cause CmdLineException");
		} catch (CmdLineException e) {
			//This exception is expected
		}


	}

}
