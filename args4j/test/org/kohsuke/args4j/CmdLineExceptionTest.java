/*
 * The MIT License
 *
 * Copyright 2014 Kohsuke Kawaguchi.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kohsuke.args4j;

import java.util.Locale;
import junit.framework.TestCase;

/**
 * Test case for {@link CmdLineException}.
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class CmdLineExceptionTest extends TestCase {
    
    private static class TestBean {
        @Option(name = "-foo")
        String foo;
    }
    
    /***
     * Test for {@link CmdLineException#CmdLineException(org.kohsuke.args4j.CmdLineParser, org.kohsuke.args4j.MessageFormatter, java.lang.String...) }
     * and {@link CmdLineException#getLocalizedMessage() }.
     */
    public void testGetLocalizedMessage() {
        TestBean testBean = new TestBean();
        
        Locale.setDefault(Locale.GERMANY);
        CmdLineParser parser = new CmdLineParser(testBean);
        CmdLineException e = new CmdLineException(parser, Messages.NO_ARGUMENT_ALLOWED, "foofoo");
        
        assertEquals("No argument is allowed: foofoo", e.getMessage());
        assertEquals("Kein Argument erlaubt: foofoo", e.getLocalizedMessage());
        assertSame(parser, e.getParser());
    }
}
