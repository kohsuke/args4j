@echo off
rem   This shows how to use the Args4J-Starter class
set EXE=java -classpath ../target/classes;../target/examples -Dmainclass=SampleStarter org.kohsuke.args4j.Starter


rem   Error: The system property 'mainclass' must contain the classname to start.
echo java -classpath ../target/classes;../target/examples org.kohsuke.args4j.Starter
java -classpath ../target/classes;../target/examples org.kohsuke.args4j.Starter
echo --------------------------------------------------------------------------


rem   SampleStarter.run(String[])
rem   - args.length: 0
rem   SampleStarter: name='null'  file='null'
echo %EXE%
%EXE%
echo --------------------------------------------------------------------------


rem   "-wrong" is not a valid option
rem   mainclass [options]
rem    -file FILE : Sets the file
rem    -name VAL  : Sets the name
echo %EXE% -wrong
%EXE% -wrong
echo --------------------------------------------------------------------------


rem   SampleStarter.run(String[])
rem   - args.length: 2
rem     - -name
rem     - Test
rem   SampleStarter: name='Test'  file='null'
echo %EXE% -name Test
%EXE% -name Test
echo --------------------------------------------------------------------------


rem   SampleStarter.run(String[])
rem   - args.length: 4
rem     - -file
rem     - .
rem     - -name
rem     - Hello World
rem   SampleStarter: name='Hello World'  file='C:\cvs-repository\args4j\args4j\examples\.'
echo %EXE% -file . -name "Hello World"
%EXE% -file . -name "Hello World"