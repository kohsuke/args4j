## Change Log

### upcoming
others
- [#110](https://github.com/kohsuke/args4j/pull/110) use Utilities.chechNotNull anywhere (@englishman)
- [#115](https://github.com/kohsuke/args4j/pull/115) Typo fix (@douglarek)

### 2.32 (2015/03/23)
changes
- [#102](https://github.com/kohsuke/args4j/pull/102) Print default value in usage (@sfuhrm)
- [#86](https://github.com/kohsuke/args4j/pull/86) Prevent setting value of final field (@dantuch)
- [711af58](https://github.com/kohsuke/args4j/commit/711af58eac1d8772ff8da19602773456bd161ba5) report the class name for better error diagnostics (@kohsuke)

bugs
- [#91](https://github.com/kohsuke/args4j/pull/91) Fix for MessageFormat '{0}' which will print as raw {0}, not the resolve... (@sfuhrm)

others
- [17b565c](https://github.com/kohsuke/args4j/commit/17b565cb2a360d25e1095ef1433ba913b7d93288) So many double-dot releases (@kohsuke)
- [#103](https://github.com/kohsuke/args4j/pull/103) Bumped version of junit framework (@sfuhrm)
- [#101](https://github.com/kohsuke/args4j/pull/101) Mac address option handler test (@sfuhrm)
- [#94](https://github.com/kohsuke/args4j/pull/94) pom: include sources in the bundles (@fhuberts)
- [26740ef](https://github.com/kohsuke/args4j/commit/26740efc93979ba11a8e2f8bd79448f4a94532e6) Fixed links (@kohsuke)
- [#88](https://github.com/kohsuke/args4j/pull/88) add testcase for issue #87 (@dnozay)
- [a961214](https://github.com/kohsuke/args4j/commit/a96121428fce3e002d7b08ff6f0a2e467c438615) bit of simplification (@kohsuke)

### 2.0.31 (2015/03/20)
others
- [500f4f7](https://github.com/kohsuke/args4j/commit/500f4f767de7157de8306b1db08c42e13c902462) Use the latest parent POM (@kohsuke)
- [#95](https://github.com/kohsuke/args4j/pull/95) pom: include the LICENSE in the bundles (@fhuberts)

### 2.0.30 (2014/08/31)
changes
- [#82](https://github.com/kohsuke/args4j/pull/82) Option to disable the at-parsing (@sfuhrm)
- [#79](https://github.com/kohsuke/args4j/pull/79) OptionHandlerRegistry refactoring (@sfuhrm)
- [#78](https://github.com/kohsuke/args4j/pull/78) Naming consistency (@sfuhrm)

others
- [229eacd](https://github.com/kohsuke/args4j/commit/229eacd048c245c1dce6e810fe12e1d6b6e472a2) Use the latest (@kohsuke)
- [#81](https://github.com/kohsuke/args4j/pull/81) Java doc fixes (@sfuhrm)
- [196f71c](https://github.com/kohsuke/args4j/commit/196f71c4464cf9a802fe568a7d1d1a1c9151aadd) Get the version from the parent POM (@kohsuke)

### 2.0.29 (2014/07/05)
changes
- [#73](https://github.com/kohsuke/args4j/pull/73) ability to use key=value in usage for options (@javaerb)
- [#74](https://github.com/kohsuke/args4j/pull/74) ability not to sort options (@javaerb)
- [#77](https://github.com/kohsuke/args4j/pull/77) Provide subcommand names for argument usage help (@andrewgaul)
- [#76](https://github.com/kohsuke/args4j/pull/76) Provide enum values for argument usage help (@andrewgaul)
- [#75](https://github.com/kohsuke/args4j/pull/75) The so-called @-syntax for file-based-option-passing (@sfuhrm)
- [#68](https://github.com/kohsuke/args4j/pull/68) More locale fixes (@sfuhrm)
- [#66](https://github.com/kohsuke/args4j/pull/66) Renamed the files to match the ResourceBundle file lookup logic and fix a small bug. (@sfuhrm)
- [#62](https://github.com/kohsuke/args4j/pull/62) Localized CmdLineExceptions (@sfuhrm)

others
- [304dcb2](https://github.com/kohsuke/args4j/commit/304dcb273e60b7d106e208287a215baee663e449) Refactoring the registry function out of the already too big CmdLineParser class
- [3463862](https://github.com/kohsuke/args4j/commit/3463862ebe44d7834cf19bdb244e1f2330744db1) Using a newer parent POM (@kohsuke)
- [#69](https://github.com/kohsuke/args4j/pull/69) Code cleanups 2 (@sfuhrm)
- [#67](https://github.com/kohsuke/args4j/pull/67) Code cleanups (@sfuhrm)

### 2.0.28 (2014/05/11)
changes
- [#57](https://github.com/kohsuke/args4j/pull/57) Fix for design problem in ArrayFieldSetter (@sfuhrm)
- [#59](https://github.com/kohsuke/args4j/pull/59) PatternOptionHandler plus unit test and error strings (@sfuhrm)
- [#60](https://github.com/kohsuke/args4j/pull/60) Help parameter introduction like seen in JCommander (@sfuhrm)
- [#65](https://github.com/kohsuke/args4j/pull/65) print seperating colon in usage only for the first line (@thomas-mc-work)
- [#64](https://github.com/kohsuke/args4j/pull/64) Port to JSR-269 (@msrb)
- [#63](https://github.com/kohsuke/args4j/pull/63) #41 Possibility to exclude Options if other option is present (@MagIciaNGTAO)

others
- [40fa077](https://github.com/kohsuke/args4j/commit/40fa077b65ba563f1778e8e089da3c46e09760d9) This parameter defaults to TXT (@kohsuke)
- [693456b](https://github.com/kohsuke/args4j/commit/693456b1c0f9e8063ac5d0aed1722511058e0825) &lt;code&gt; around &lt;pre&gt; makes no sense (@kohsuke)
- [66d8529](https://github.com/kohsuke/args4j/commit/66d8529839ad7a02619bce3bb13d389ea76cb3dc) Minor touch up (@kohsuke)
- [ea91148](https://github.com/kohsuke/args4j/commit/ea911482a0dac6e1db825491583b0b604c781dab) In effect this is simpler (@kohsuke)
- [#53](https://github.com/kohsuke/args4j/pull/53) Rename the identifiers '_' to avoid a compiler warning with Java 8 (@ebourg)

### 2.0.27 (2014/04/13)
changes
- [#39](https://github.com/kohsuke/args4j/pull/39) Add LICENSE file (@caniszczyk)
- [#47](https://github.com/kohsuke/args4j/pull/47) Minor smaller text changes, especially in German translations (@sfuhrm)
- [#46](https://github.com/kohsuke/args4j/pull/46) OptionHandler for MAC addresses added. (@teiesti)

bugs
- [#40](https://github.com/kohsuke/args4j/pull/40) Fix incorrect parsing of Key=Value pairs in CmdLineParser.filter(List<OptionHandler> opt, String keyFilter) (@hutm)

others
- [4118b77](https://github.com/kohsuke/args4j/commit/4118b77c6afc244577b7f8948c8452004826ae8f) defaulting it properly (@kohsuke)
- [74eac3e](https://github.com/kohsuke/args4j/commit/74eac3eed745dc88e45d8be8dd52f46be91de636) Using the latest (@kohsuke)
- [#44](https://github.com/kohsuke/args4j/pull/44) Remove the old Maven 1 build (@ebourg)
- [#49](https://github.com/kohsuke/args4j/pull/49) Remove the unused dependency on Ant (@ebourg)
- [#50](https://github.com/kohsuke/args4j/pull/50) readme.md created (@thomas-mc-work)

### 2.0.26 (2013/11/03)
changes
- [#30](https://github.com/kohsuke/args4j/pull/30) added support for args in format -argname=argvalue (@hutm)

bugs
- [#36](https://github.com/kohsuke/args4j/pull/36) Add underflow check to prevent getParameter to throw ArrayOutOfBoundsException and instead throw a sensical exception. (@MikeMatrix)

others
- [#29](https://github.com/kohsuke/args4j/pull/29) Numerous minor edits to documentation (@Zearin)
- [#31](https://github.com/kohsuke/args4j/pull/31) Add missing impl annotation name to Javadoc (@maginatics)

### 2.0.25 (2013/07/06)
changes
- [#14](https://github.com/kohsuke/args4j/pull/14) adding "requires" on Option (@NicolasGeraud)
- [#28](https://github.com/kohsuke/args4j/pull/28) Make Messages enumeration public to ease extension of OptionHandlers creating other implementation in different packages than `org.kohsuke.args4j.spi. (@cyrille-leclerc)

others
- [35aacce](https://github.com/kohsuke/args4j/commit/35aacceb564b1e3c110c36eb515dff7b35d32813) newer version that automatically closes a repository (@kohsuke)

### 2.0.24 (2013/07/01)
changes
- [#20](https://github.com/kohsuke/args4j/pull/20) Introduce hidden options (@maginatics)
- [1acff43](https://github.com/kohsuke/args4j/commit/1acff436c29ed9c0f9ce81ad845b3df7480dde83) Massaged the contribution from @andrewgaul. (@kohsuke)
- [#23](https://github.com/kohsuke/args4j/pull/23) I added a handler for the java 7 Path class (@madkrupt)
- [#21](https://github.com/kohsuke/args4j/pull/21) support for option of type InetAddress (@dlad)
- [06b413b](https://github.com/kohsuke/args4j/commit/06b413b01cafe2dcbedfb82ac9e75a969a8c5ebe) Fix ARGS4J-25 - support args like "-coll val1 val2 -coll val3" with `@Option(handler = StringArrayOptionHandler.class, …)` (@cyrille-leclerc)

others
- [0001d43](https://github.com/kohsuke/args4j/commit/0001d434a585f7cd556c98aea35a6ac7a2075207) fixed URL (@kohsuke)
- [#26](https://github.com/kohsuke/args4j/pull/26) Git Ignore Idea (@cyrille-leclerc)
- [9f29776](https://github.com/kohsuke/args4j/commit/9f297765e50ae09914bf7e1e81c46bbc2b709b12) fixed up the code. Now I feel a little better. (@kohsuke)
- [fc296a0](https://github.com/kohsuke/args4j/commit/fc296a01004a23cfb16b78a14630991cc7fcbec3) Improving the doc (@kohsuke)

### 2.0.23 (2013/03/12)
changes
- [b36291f](https://github.com/kohsuke/args4j/commit/b36291f9cfbf917e5531fb98b4e39104a88bc3e8) added a new OptionHandler that implements a typical sub-command pattern (@kohsuke)
- [a1e5a30](https://github.com/kohsuke/args4j/commit/a1e5a30310a9c39d4bd053cfa3cc915afc876cbe) Allow OptionHandlers to access other annotations (@kohsuke)
- [issue-16](https://github.com/kohsuke/args4j/issues/16) Issue with custom OptionHandler that shall assign a field of type "java.util.Properties"
- [4aa760d](https://github.com/kohsuke/args4j/commit/4aa760d91e7336d794fcc69e6a4e086af6e40322) added Setter that handles an array (@kohsuke)
- [e883b93](https://github.com/kohsuke/args4j/commit/e883b9395b866ed9bd297bee534a04a8bbe83a37) It doesn't make sense for @Option to have multi-value setting. (@kohsuke)
- [issue-12](https://github.com/kohsuke/args4j/issues/12) MapSetter too restricted for custom OptionHandlers
- [#19](https://github.com/kohsuke/args4j/pull/19) Generate OSGi metadata in JAR files (@johnkeeping)

bugs
- [issue-10](https://github.com/kohsuke/args4j/issues/10) Starting index too late results in non-verbose NullPointerException

### 2.0.22 (2012/06/25)
others
- [e02f327](https://github.com/kohsuke/args4j/commit/e02f3273e8d1bc168489445d573cfdb80404ecfd) Let's see if this would let me deploy to central (@kohsuke)

### 2.0.21 (2012/04/27)
others
- [66b564e](https://github.com/kohsuke/args4j/commit/66b564ecb4d9e8015b7f7bd702a1bdb2091c2dc1) Need to have them accept the groupId before I can sync. (@kohsuke)

### 2.0.20 (2012/04/26)
changes
- [#9](https://github.com/kohsuke/args4j/pull/9) Adding support for '-' in enums. (@grossws)

others
- [b9dc50f](https://github.com/kohsuke/args4j/commit/b9dc50fb044e4daf173d50c762b211c17d80c1ff) retargetting to the central deployment (@kohsuke)
- [#8](https://github.com/kohsuke/args4j/pull/8) deprecreated constructor in example (@oluies)

### 2.0.19 (2011/12/12)
changes
- [9c5bc87](https://github.com/kohsuke/args4j/commit/9c5bc87d26bb631612a08404aa5e0f5b90169ad4) Added a handler to support explicitly set booleans, i.e. '-myOpt false' and '-myOpt true' rather than '-myOpt' for true and omitting the option for false (@demobox)
- [b57c129](https://github.com/kohsuke/args4j/commit/b57c1295af888400c43667073070f6f2e35959f8) enable the EnumOptionHandler to handle illegal arguments. (@KengoTODA)

others
- [6fb79d8](https://github.com/kohsuke/args4j/commit/6fb79d861046b7d14085c65bb90c7a00388ca1a6) Adds tests for parsing of arguments to ExampleTest. This shows how arguments are actually parsed. (@leanto)

### 2.0.18 (2011/08/09)
changes
- [#1](https://github.com/kohsuke/args4j/pull/1) TXT file output and args4j maven plugin (@lacostej)

### 2.0.17 (2011/07/28)
changes
- [0b95c1f](https://github.com/kohsuke/args4j/commit/0b95c1f1e4ea44dbe450bc97e5549290e652dda8) updated to MIT license, to be consistent with https://args4j.dev.java.net/
- [ae6f740](https://github.com/kohsuke/args4j/commit/ae6f74097376d0afc0604dc0cd914b14931970ac) New MetadataParser (FieldParser) which makes all Fields available by their names (Foo.bar -> -bar)

others
- [213ba86](https://github.com/kohsuke/args4j/commit/213ba86c60336bb3c9a75d1dd6acb2e13d7332ef) pushing web contents to GitHub pages (@kohsuke)
- [cae3abc](https://github.com/kohsuke/args4j/commit/cae3abc32854d5b4e6c46ff8bba76ecea6ad70ea) partially fixed to deploy on new java.net
- [c4df6af](https://github.com/kohsuke/args4j/commit/c4df6af406b14dd3184065c29712f5b0934e64c9) JavaDocs. Problem with @-sign in package.html, need help here.
- [78067cd](https://github.com/kohsuke/args4j/commit/78067cdd3aa235c43ff8a266f80b547ec99aa12e) Regression: the ' sign wasnt loaded correctly for unit tests, so use " instead
- [ffe93fb](https://github.com/kohsuke/args4j/commit/ffe93fba1387b096dfb751b4c57ab9fe3df5681c) Found some not externalized Messages.
- [9b8dc05](https://github.com/kohsuke/args4j/commit/9b8dc05015eb1a877eedc5445a04f8043e489701) Wasnt sure by myself, so document after a test
- [fc85e79](https://github.com/kohsuke/args4j/commit/fc85e7921d114ee808916b0d00f034f66760b8f7) - write my name without special character like in other classes here


### 2.0.16 (2009/09/04)
changes
- [6259586](https://github.com/kohsuke/args4j/commit/6259586cc463554564f6b0374d95a93cded39d28) 'annotate' a class via XML instead of Java5
- [86a0836](https://github.com/kohsuke/args4j/commit/86a083624034798d9a1bbc35dfa98acaa7cd283d) Prepare for alternative 'markup' (instead of Annotations)
- [d1ad6d4](https://github.com/kohsuke/args4j/commit/d1ad6d47baf38c3fcf1836b5ce89c9dda3f302b7) Make CmdLineParser.addOption / addArgument public for alternatives to parsing class files.
- [7043519](https://github.com/kohsuke/args4j/commit/7043519d09849eeb26e498aa147626bb5b40e1bc) allowing these annotations to be on parameters, so that other higher-level frameworks can use method invocation binding for CLI.
- [27f98ee](https://github.com/kohsuke/args4j/commit/27f98eeec1b29304351afe1e19ac533158cb5200) added a convenient overload.

others
- [4ddab12](https://github.com/kohsuke/args4j/commit/4ddab1204e505d9ced575d6af6d943a264831818) Hint for updating changes.xml
- [40875c1](https://github.com/kohsuke/args4j/commit/40875c1eae65ab935e392c92e4d1ec1ad50802cd) Exposing arguments directly.
- [db7b8d3](https://github.com/kohsuke/args4j/commit/db7b8d3f506d9ab9a755b73700d7e255ebed42e1) doc improvement.
- [3a8d0e6](https://github.com/kohsuke/args4j/commit/3a8d0e697de4bf87e2aeaab3cd5c41c495928740) these files sould be generated from POM

### 2.0.15 (2009/07/30)
changes
- [fb80d05](https://github.com/kohsuke/args4j/commit/fb80d05ab998d90e43b01f9c4148f58c2ab19e52) added a handler type that effectively terminates the option processing

others
- [9331ddf](https://github.com/kohsuke/args4j/commit/9331ddf894b396e8f7bfefc38a87d5074cbb4260) fixed a bug in the error message generation
- [ca0cc9a](https://github.com/kohsuke/args4j/commit/ca0cc9ad4228169441fc9341a13b543641e1fde0) Add a test how to handle thrown exceptions thrown in setters.
- [d808406](https://github.com/kohsuke/args4j/commit/d8084069273126526c62480d7b1a3fb62a617912) doc improvement
- [5a6b7f4](https://github.com/kohsuke/args4j/commit/5a6b7f472fae494a2502849c52b3dd76f76ca1ce) Mac tools.jar handling

### 2.0.14 (2009/05/05)
changes
- [a9084b8](https://github.com/kohsuke/args4j/commit/a9084b8c6e387690bdeb9153145c0948170e27f5) include the parser instance in the thrown CmdLineException.

### 2.0.13 (2009/04/17)
changes
- [6a0c6a9](https://github.com/kohsuke/args4j/commit/6a0c6a9c64e8b75c2741cf32ccd6d7fb9fd382b8) improved line wrapping

others
- [f191d2d](https://github.com/kohsuke/args4j/commit/f191d2d6a7041eef55abd2b42cf5fea69493a7d8) shouln't have project files

### 2.0.12 (2009/04/06)
changes
- [feffdeb](https://github.com/kohsuke/args4j/commit/feffdeb0906e7996ddca26cd319f6c4179a824eb) added a OptionHandler for URI
- [883c29e](https://github.com/kohsuke/args4j/commit/883c29e17c65826042012c251662055c85ef0099) Add German resource bundle

bugs
- [292dcd4](https://github.com/kohsuke/args4j/commit/292dcd4668311a7a73871c693d1750c42a2f190c) wrong meta var

others
- [da455c0](https://github.com/kohsuke/args4j/commit/da455c005534ca92e846217c99dc7be51fd31ed8) updated to the new repository location
- [9019dce](https://github.com/kohsuke/args4j/commit/9019dce6712036880e84b12c482871e00ecc0660) Use the correct syntax for multivalued options: -flag value1 -flag value2
- [45c1048](https://github.com/kohsuke/args4j/commit/45c10480ec217f1a10004e022840a7ef94765c06) When using (and having now) localized resource bundles, we have to use the right one during tests. So for tests use the US one.
- [0ecedc2](https://github.com/kohsuke/args4j/commit/0ecedc21ceb1f2a4dba18205cbe72f8e1455232b) JUnit test for @Option(multiValued=true).

### 2.0.11 (2009/01/30)

### 2.0.10 (2009/01/30)
changes
- [1570253](https://github.com/kohsuke/args4j/commit/15702531559af18d0bdef2f42c0a1b42e076bfbc) make sure we run with Java5
- [c5b1641](https://github.com/kohsuke/args4j/commit/c5b1641cc6a53afb23da3363f5f0efa50265b3dc) added Russian translation from vlad smyshlyaev <albedo072@gmail.com>
- [7492373](https://github.com/kohsuke/args4j/commit/74923739f0a1db5afa249311bfc4e91a9924ac6e) Added URLOptionHandler
- [60d7683](https://github.com/kohsuke/args4j/commit/60d76837de6ebc1ce46ae23a64fdd46ab6426281) applied a fix suggested by Erb Cooper <ecooper@liquidnet.com>
- [5b94b74](https://github.com/kohsuke/args4j/commit/5b94b7411d1ec5ed6ad4278a9e576241ac10b568) fixed a bug pointed out by vlad smyshlyaev <albedo072@gmail.com>
- [b92bf79](https://github.com/kohsuke/args4j/commit/b92bf791c86bc4b34f88339156499d59de380286) Rewrite of printOption()
- [78b3734](https://github.com/kohsuke/args4j/commit/78b37347f4a771947840cadcfa403a288660d88c) Added StringArrayOptionHandler

others
- [d5e2795](https://github.com/kohsuke/args4j/commit/d5e27950fc82cc43dcbec702fc6ec6742fe3eb99) Testcase for Issue10.
- [a1eddee](https://github.com/kohsuke/args4j/commit/a1eddee2191af6fd5f540250cd642ad7e7e1a713) doc fix
- [da2aaed](https://github.com/kohsuke/args4j/commit/da2aaed5ff800444c1a0c1714ec893f62dbf0fa4) I believe this is just a mistake introduced by the IDE when he added this new class. I'm removing this to have this file use the same LICENSE.txt that this project as a whole uses.
- [91a37ef](https://github.com/kohsuke/args4j/commit/91a37efb11da61f02cc02fdf6d128cd08f301c9f) Make Apache Gump happy with non UTF-8 characters.
- [935f709](https://github.com/kohsuke/args4j/commit/935f709e86b71c41484c1d96b1fe9dc16f4827dd) Convenience method for setting the args of the parser.

### 2.0.9 (2008/06/16)
changes
- [53cfbb7](https://github.com/kohsuke/args4j/commit/53cfbb72ee532570c4d5139466344ba09fc36fd7) Support of all native Java types.
- [0c6fd86](https://github.com/kohsuke/args4j/commit/0c6fd86da4e53d7d2c91d43754423f8bca195323) Handler for Maps.

others
- [a6fb6b2](https://github.com/kohsuke/args4j/commit/a6fb6b22a302e25102774d58ddf6cd26b84ab5d8) looks like this configuration is invalid

### 2.0.8 (2008/04/01)
changes
- [c69aadf](https://github.com/kohsuke/args4j/commit/c69aadfcfcd47b29ba8be3328ccd2dbd414d6e65) Add argument handling to args4j. See ArgumentTest for a simple example. Look at @Argument for more options.
- [27cab03](https://github.com/kohsuke/args4j/commit/27cab037bba3ed35fa43f07bc95b25a69fbec779) Add aliases to option names.
- [63d35cd](https://github.com/kohsuke/args4j/commit/63d35cd77540b298eea313dfe7ef395e9dc73a6f) CmdLineParser now correctly identifies long option values (that was missing from the previous commit)
- [7e3d60f](https://github.com/kohsuke/args4j/commit/7e3d60f4b984d5bfdf5ac2b08833e9080fab7233) Bug 4: Better readability of Java5 enums usage

others
- [061ace2](https://github.com/kohsuke/args4j/commit/061ace203f21c248c507e374bb9a1df909a47a8f) Bug 5: Options without "usage" are hidden.
- [d430914](https://github.com/kohsuke/args4j/commit/d430914feacd29ffbcb0ad51492c656ca27ee0f5) converting to Maven2

### 2.0.7 (2006/08/16)
bugs
- Fixed a packaging error where APT service entry was in args4j.jar

### 2.0.6 (2006/04/05)
changes
- [583f657](https://github.com/kohsuke/args4j/commit/583f6579aee63f349432bc8eb1f9003c89cddca2) generified OptionHandler by following malachi's suggestion.
- [d5905e0](https://github.com/kohsuke/args4j/commit/d5905e097d87938fba92ac54647d62ce56eb4ea2) Possibility to specify line width in printUsage. Done via parser configuration.

others
- [61dd2a5](https://github.com/kohsuke/args4j/commit/61dd2a5e8604996c3990c0cc2fc3feef296d923a) Make it easier to start a class using the Starter. Documented in a short tutorial.
- [11e1e3d](https://github.com/kohsuke/args4j/commit/11e1e3d46bdd7126e0c071ef368fbeffb83fc989) Remove dependency to Ant 1.5.
- [8f35602](https://github.com/kohsuke/args4j/commit/8f35602a5f6bbaa4bac21088f51385c3e61e03e5) Args4J is build by Apache Gump

### 2.0.5 (2006/02/15)
changes
- [fa1c0a6](https://github.com/kohsuke/args4j/commit/fa1c0a620764bd15f5f69faf56e4907235490000) - implemented the stop option "--".

others
- [f392dfe](https://github.com/kohsuke/args4j/commit/f392dfe15c70eb6fd20fc83486e7bcaba48deb75) Let Maven compile the examples. Maybe more Ant- than Maven-like, but I know more of Ant than of Maven ;-)
- [5a1f2ae](https://github.com/kohsuke/args4j/commit/5a1f2aeca8a6b69a868fc53e1e1ce57dff2ee732) Usage info for the -custom option and use of that data.
- [e55cce0](https://github.com/kohsuke/args4j/commit/e55cce0b5604bdb5668305d25fe7908c80f90a57) Check the usage messages.
- [2234fd4](https://github.com/kohsuke/args4j/commit/2234fd48074446c808a2ab06c7828ef1ab0bc0e2) How to handle properties? Here some test cases for discussion from the users point of view.
- [e1d44d3](https://github.com/kohsuke/args4j/commit/e1d44d3169553f4be8d2e88384c0ffa84f063d93) Test the inheritence of @Option's. Annotations made in interfaces are NOT handled. Bug or Feature? ;-)

### 2.0.4 (2006/01/29)
changes
- [e4942e3](https://github.com/kohsuke/args4j/commit/e4942e33fe197a606111a56d3c8803fcfcee8645) expanded @Option to allow a Handler to be explicitly specified.

others
- [e905ee3](https://github.com/kohsuke/args4j/commit/e905ee3c243e8c9ed47c1bdab8b6b430c4179b77) Some new tests for the basics.
- [a129834](https://github.com/kohsuke/args4j/commit/a129834b6ab21fe14274a8e07b74433b6946c0be) Tried to rebuild the Apache Ant command line interface.

### 2.0.3 (2006/01/13)
changes
- [12e20a9](https://github.com/kohsuke/args4j/commit/12e20a92a6a2bab6214dcc4530e713192fd7a4c5) implemented issue 1: required option.
- [8418212](https://github.com/kohsuke/args4j/commit/841821273e4c03bbbdeec6eec8ac8aa60006898e) implemented the printExample method.
- [8584254](https://github.com/kohsuke/args4j/commit/85842547ffe5e82247e5c61a020854acdc8d281c) added contributed DoubleOptionHandler

### 2.0.1 (2005/05/12)
changes
- [a9068b2](https://github.com/kohsuke/args4j/commit/a9068b2d4fc2384d1bc086c4ffbd4d561fc9b686) implemented a case-insensitive search.
- [8b702cb](https://github.com/kohsuke/args4j/commit/8b702cb4b922da08987b67d3db70035970aa8e33) implemented a tool that generates the usage XML/HTML.
- [a5f06b3](https://github.com/kohsuke/args4j/commit/a5f06b369490d93f37c354f03f0982d42bf39718) implemented metavariable support.

### 2.0.0 (2005/05/11)
changes
- [882b99d](https://github.com/kohsuke/args4j/commit/882b99d467ac91d3a5efcb09d1c80545fe0c4fb1) moving the repository from SourceForge.
- [12e62c6](https://github.com/kohsuke/args4j/commit/12e62c6f0a0603e2052d4501de0625885dcb3584) check point.
- [b5339c5](https://github.com/kohsuke/args4j/commit/b5339c5a775f89567de53683bbbde3fb187ad5c5) implemented args4j 2.0.
