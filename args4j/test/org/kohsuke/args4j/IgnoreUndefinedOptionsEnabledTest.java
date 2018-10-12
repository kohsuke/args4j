package org.kohsuke.args4j;

import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


public class IgnoreUndefinedOptionsEnabledTest extends Args4JTestBase<IgnoreUndefinedOptionsEnabledTest>{

	@Option(name = "-one")
	public String one;

	@Option(name = "-two")
	public String two;


	@Override
	public IgnoreUndefinedOptionsEnabledTest getTestObject() {
		return this;
	}


	protected CmdLineParser createParser() {
		ParserProperties parserProperties = ParserProperties.defaults();
		parserProperties.withIgnoreUndefinedOptions(true);
		return new CmdLineParser(testObject, parserProperties);
	}


	public void test(){


		try {
			parser.parseArgument("-one", "value_one", "-two", "value_two", "-three", "value_three");
		} catch (CmdLineException e) {
			Assert.fail(e.getMessage());
		}

	}

}
