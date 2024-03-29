args4j
======
args4j is a small Java class library that makes it easy to parse command line options/arguments in your CUI
application. See more info at https://args4j.kohsuke.org/

Why should I use it?
--------------------

See [the quick intro](https://web.archive.org/web/20120605024439/http://weblogs.java.net/blog/kohsuke/archive/2005/05/parsing_command.html)

- It makes command line parsing very easy by using annotations
- Generate usage text very easily
- Generate HTML/XML documentation listing all options
- Full localization support
- Designed to parse javac like options, as opposed to GNU-style (where ls -lR is considered to have two options l and
  R).
- Licensed under [the MIT license](https://opensource.org/license/mit/).

How can I use it?
-----------------

1. Check [the sample](https://github.com/kohsuke/args4j/blob/master/args4j/examples/SampleMain.java). This is how your
   code will look like.
2. [Download](https://search.maven.org/search?q=g:args4j%20AND%20a:args4j) the distribution or include the library from
   the Maven Repository.
3. Write your code.

More Resources
--------------

1. [A small tutorial](https://args4j.kohsuke.org/sample.html) for the use of the Starter and Args4J
2. [javadoc](https://args4j.kohsuke.org/args4j/apidocs/)
3. [How to generate a documentation for your CLI](https://args4j.kohsuke.org/apt.html)
4. [Extend args4j to handle other Java types](https://args4j.kohsuke.org/implementOptionhandler.html)
5. [Kohsuke's Blog: Parsing command line options in JDK 5.0 style](https://web.archive.org/web/20120605024439/http://weblogs.java.net/blog/kohsuke/archive/2005/05/parsing_command.html)
6. [A comparison between Commons CLI and Args4j](https://hikage.developpez.com/java/articles/api/cli-vs-args4j/) in
   French
