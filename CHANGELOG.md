## Change Log

### upcoming (2015/11/01 01:47 +00:00)
- [#110](https://github.com/kohsuke/args4j/pull/110) use Utilities.chechNotNull anywhere (@englishman)
- [#115](https://github.com/kohsuke/args4j/pull/115) Typo fix (@douglarek)

### 2.32 (2015/03/23)
- [17b565c](https://github.com/kohsuke/args4j/commit/17b565cb2a360d25e1095ef1433ba913b7d93288) So many double-dot releases (@kohsuke)
- [#102](https://github.com/kohsuke/args4j/pull/102) Print default value in usage (@sfuhrm)
- [cac778d](https://github.com/kohsuke/args4j/commit/cac778dbe5c18411da9973d3d368fe3e5439b6ce) getValue() collides with existing method on FieldSetter (@kohsuke)
- [8a1492a](https://github.com/kohsuke/args4j/commit/8a1492ac0584486cf26243c818aa83ca72b3172a) OptionHandler should be responsible for printing values. (@kohsuke)
- [da60dae](https://github.com/kohsuke/args4j/commit/da60dae1d8b1cf83994b6f81c3168e44975214ab) Better to explicitly deal with multi-valueness. (@kohsuke)
- [acdcf12](https://github.com/kohsuke/args4j/commit/acdcf127844cb2f74380f728005ca8aa292b050c) String.valueOf() already does this. (@kohsuke)
- [#103](https://github.com/kohsuke/args4j/pull/103) Bumped version of junit framework (@sfuhrm)
- [#101](https://github.com/kohsuke/args4j/pull/101) Mac address option handler test (@sfuhrm)
- [#94](https://github.com/kohsuke/args4j/pull/94) pom: include sources in the bundles (@fhuberts)
- [57a97ce](https://github.com/kohsuke/args4j/commit/57a97ce56d2cf874aef0c63a57ca6e85fc3ae4a5) Fixing the issue raised in pull request #91 (@kohsuke)
- [26740ef](https://github.com/kohsuke/args4j/commit/26740efc93979ba11a8e2f8bd79448f4a94532e6) Fixed links (@kohsuke)
- [#88](https://github.com/kohsuke/args4j/pull/88) add testcase for issue #87 (@dnozay)
- [#86](https://github.com/kohsuke/args4j/pull/86) Prevent setting value of final field (@dantuch)
- [711af58](https://github.com/kohsuke/args4j/commit/711af58eac1d8772ff8da19602773456bd161ba5) report the class name for better error diagnostics (@kohsuke)
- [a961214](https://github.com/kohsuke/args4j/commit/a96121428fce3e002d7b08ff6f0a2e467c438615) bit of simplification (@kohsuke)

### 2.0.31 (2015/03/20)
- [500f4f7](https://github.com/kohsuke/args4j/commit/500f4f767de7157de8306b1db08c42e13c902462) Use the latest parent POM (@kohsuke)
- [#95](https://github.com/kohsuke/args4j/pull/95) pom: include the LICENSE in the bundles (@fhuberts)

### 2.0.30 (2014/08/31)
- [229eacd](https://github.com/kohsuke/args4j/commit/229eacd048c245c1dce6e810fe12e1d6b6e472a2) Use the latest (@kohsuke)
- [0b2928c](https://github.com/kohsuke/args4j/commit/0b2928ccb071560b852cfd164dc384cc7a248784) Fixing the coding style to the Sun coding convention (@kohsuke)
- [#82](https://github.com/kohsuke/args4j/pull/82) Option to disable the at-parsing (@sfuhrm)
- [#81](https://github.com/kohsuke/args4j/pull/81) Java doc fixes (@sfuhrm)
- [c5dfcfe](https://github.com/kohsuke/args4j/commit/c5dfcfe0ae1d5d90e5783b81279aba38ad99a11b) Rely on static imports (@kohsuke)
- [#79](https://github.com/kohsuke/args4j/pull/79) OptionHandlerRegistry refactoring (@sfuhrm)
- [#78](https://github.com/kohsuke/args4j/pull/78) Naming consistency (@sfuhrm)
- [196f71c](https://github.com/kohsuke/args4j/commit/196f71c4464cf9a802fe568a7d1d1a1c9151aadd) Get the version from the parent POM (@kohsuke)

### 2.0.29 (2014/07/05)
- [b1aa90f](https://github.com/kohsuke/args4j/commit/b1aa90f37eaf2c7997b899e6a65cd27b451c2210) deprecated the old methods
- [6f675b0](https://github.com/kohsuke/args4j/commit/6f675b09114ee50c607cf004fd0b03f06187cb6a) Added unit test (with some integrational parts)
- [304dcb2](https://github.com/kohsuke/args4j/commit/304dcb273e60b7d106e208287a215baee663e449) Refactoring the registry function out of the already too big CmdLineParser class
- [#73](https://github.com/kohsuke/args4j/pull/73) ability to use key=value in usage for options (@javaerb)
- [#74](https://github.com/kohsuke/args4j/pull/74) ability not to sort options (@javaerb)
- [bdf4777](https://github.com/kohsuke/args4j/commit/bdf477741713a43e83489a112812715161f65671) Merge branch 'master' of github.com:kohsuke/args4j (@kohsuke)
- [#77](https://github.com/kohsuke/args4j/pull/77) Provide subcommand names for argument usage help (@andrewgaul)
- [#76](https://github.com/kohsuke/args4j/pull/76) Provide enum values for argument usage help (@andrewgaul)
- [3463862](https://github.com/kohsuke/args4j/commit/3463862ebe44d7834cf19bdb244e1f2330744db1) Using a newer parent POM (@kohsuke)
- [#75](https://github.com/kohsuke/args4j/pull/75) The so-called @-syntax for file-based-option-passing (@sfuhrm)
- [1f3b557](https://github.com/kohsuke/args4j/commit/1f3b557606dc292e1588f565926e74197a4e5f8d) Improved error diagnostics (@kohsuke)
- [94eb6d9](https://github.com/kohsuke/args4j/commit/94eb6d9043f545f85c943529f76c1835598c8fda) More generic to allow Comparator, not just a boolean (@kohsuke)
- [db91092](https://github.com/kohsuke/args4j/commit/db910920b40da318a9c63ac547ad6aae22be06de) With ParserProperties, this became redundant (@kohsuke)
- [3713ac6](https://github.com/kohsuke/args4j/commit/3713ac60555e456234bcc2f7f35f911024c2df73) typo (@kohsuke)
- [bf6798e](https://github.com/kohsuke/args4j/commit/bf6798e6befd263c514e68a189602badb90e165e) Formatting chanegs (@kohsuke)
- [#69](https://github.com/kohsuke/args4j/pull/69) Code cleanups 2 (@sfuhrm)
- [#68](https://github.com/kohsuke/args4j/pull/68) More locale fixes (@sfuhrm)
- [2719403](https://github.com/kohsuke/args4j/commit/27194036389b9d15e9fa8c01e87abf739ae6cd1b) Caching the default resource doesn't work well when we switch locales in tests (@kohsuke)
- [0385d7f](https://github.com/kohsuke/args4j/commit/0385d7fbcd2c243129c74ad6f789fdaec8accbf0) CmdLineException separates message vs localized message now, so no need to mess with locales (@kohsuke)
- [#67](https://github.com/kohsuke/args4j/pull/67) Code cleanups (@sfuhrm)
- [#66](https://github.com/kohsuke/args4j/pull/66) Renamed the files to match the ResourceBundle file lookup logic and fix a small bug. (@sfuhrm)
- [994b172](https://github.com/kohsuke/args4j/commit/994b1728b33f49d00c430950258318a577fc2be0) I think this name better reflect what it does (@kohsuke)
- [0e6c9c0](https://github.com/kohsuke/args4j/commit/0e6c9c0dc2a99e356a0e2f9c841a3ba1414564f1) restore the locale at the end (@kohsuke)
- [#62](https://github.com/kohsuke/args4j/pull/62) Localized CmdLineExceptions (@sfuhrm)

### 2.0.28 (2014/05/11)
- [40fa077](https://github.com/kohsuke/args4j/commit/40fa077b65ba563f1778e8e089da3c46e09760d9) This parameter defaults to TXT (@kohsuke)
- [d56e472](https://github.com/kohsuke/args4j/commit/d56e472f9d3e8e696ca3b4799fbbc2ca2fabd0e3) Fixing English (@kohsuke)
- [693456b](https://github.com/kohsuke/args4j/commit/693456b1c0f9e8063ac5d0aed1722511058e0825) &lt;code&gt; around &lt;pre&gt; makes no sense (@kohsuke)
- [e81427d](https://github.com/kohsuke/args4j/commit/e81427d13bd55c6a2a9d807da67712083e1084ef) Tweaking the message name (@kohsuke)
- [a954df4](https://github.com/kohsuke/args4j/commit/a954df450aa632c2be84c64f3df96c28472a39ba) Flipped the method name as it does the opposite of what it says (@kohsuke)
- [eaf7bde](https://github.com/kohsuke/args4j/commit/eaf7bdead14858ebb6b5b6a779fa120bdcf7a550) Merge remote-tracking branch 'origin/master' (@kohsuke)
- [66d8529](https://github.com/kohsuke/args4j/commit/66d8529839ad7a02619bce3bb13d389ea76cb3dc) Minor touch up (@kohsuke)
- [ea91148](https://github.com/kohsuke/args4j/commit/ea911482a0dac6e1db825491583b0b604c781dab) In effect this is simpler (@kohsuke)
- [#57](https://github.com/kohsuke/args4j/pull/57) Fix for design problem in ArrayFieldSetter (@sfuhrm)
- [#59](https://github.com/kohsuke/args4j/pull/59) PatternOptionHandler plus unit test and error strings (@sfuhrm)
- [#60](https://github.com/kohsuke/args4j/pull/60) Help parameter introduction like seen in JCommander (@sfuhrm)
- [#65](https://github.com/kohsuke/args4j/pull/65) print seperating colon in usage only for the first line (@thomas-mc-work)
- [#64](https://github.com/kohsuke/args4j/pull/64) Port to JSR-269 (@msrb)
- [#63](https://github.com/kohsuke/args4j/pull/63) #41 Possibility to exclude Options if other option is present (@MagIciaNGTAO)
- [#53](https://github.com/kohsuke/args4j/pull/53) Rename the identifiers '_' to avoid a compiler warning with Java 8 (@ebourg)

### 2.0.27 (2014/04/13)
- [4118b77](https://github.com/kohsuke/args4j/commit/4118b77c6afc244577b7f8948c8452004826ae8f) defaulting it properly (@kohsuke)
- [74eac3e](https://github.com/kohsuke/args4j/commit/74eac3eed745dc88e45d8be8dd52f46be91de636) Using the latest (@kohsuke)
- [#40](https://github.com/kohsuke/args4j/pull/40) Fix incorrect parsing of Key=Value pairs in CmdLineParser.filter(List<OptionHandler> opt, String keyFilter) (@hutm)
- [#39](https://github.com/kohsuke/args4j/pull/39) Add LICENSE file (@caniszczyk)
- [#44](https://github.com/kohsuke/args4j/pull/44) Remove the old Maven 1 build (@ebourg)
- [4559287](https://github.com/kohsuke/args4j/commit/45592874222cc2fdc1a0be43ded6da0c8aec78eb) trim off brackets around it (@kohsuke)
- [8b2870d](https://github.com/kohsuke/args4j/commit/8b2870d719796bade5aad22383af1086fca39fb4) doc improvement (@kohsuke)
- [#47](https://github.com/kohsuke/args4j/pull/47) Minor smaller text changes, especially in German translations (@sfuhrm)
- [#49](https://github.com/kohsuke/args4j/pull/49) Remove the unused dependency on Ant (@ebourg)
- [06f6364](https://github.com/kohsuke/args4j/commit/06f63649a1d22cbac2c076a9d7ceae7a7c370687) Update README.md (@kohsuke)
- [#50](https://github.com/kohsuke/args4j/pull/50) readme.md created (@thomas-mc-work)
- [#46](https://github.com/kohsuke/args4j/pull/46) OptionHandler for MAC addresses added. (@teiesti)

### 2.0.26 (2013/11/03)
- [#30](https://github.com/kohsuke/args4j/pull/30) added support for args in format -argname=argvalue (@hutm)
- [92aa48d](https://github.com/kohsuke/args4j/commit/92aa48d6144b51c3293b551f6d2d6f18a706e40b) commenting what this is about. (@kohsuke)
- [#29](https://github.com/kohsuke/args4j/pull/29) Numerous minor edits to documentation (@Zearin)
- [#31](https://github.com/kohsuke/args4j/pull/31) Add missing impl annotation name to Javadoc (@maginatics)
- [#36](https://github.com/kohsuke/args4j/pull/36) Add underflow check to prevent getParameter to throw ArrayOutOfBoundsException and instead throw a sensical exception. (@MikeMatrix)

### 2.0.25 (2013/07/06)
- [35aacce](https://github.com/kohsuke/args4j/commit/35aacceb564b1e3c110c36eb515dff7b35d32813) newer version that automatically closes a repository (@kohsuke)
- [df010e3](https://github.com/kohsuke/args4j/commit/df010e3fb956910ecfce9bb0ff238a03ab1efba9) TAB->WS (@kohsuke)
- [#14](https://github.com/kohsuke/args4j/pull/14) adding "requires" on Option (@NicolasGeraud)
- [0ad366a](https://github.com/kohsuke/args4j/commit/0ad366aa3e78fbe34cd78c21f38fb4598334276c) made sure that aliases can be used (@kohsuke)
- [9dcac82](https://github.com/kohsuke/args4j/commit/9dcac8288d48a4eba9f4c2a36d7e5c343ee43d1e) Since we already have @Option.required, I think it's better to name this bit differently to avoid confusion (@kohsuke)
- [#28](https://github.com/kohsuke/args4j/pull/28) Make Messages enumeration public to ease extension of OptionHandlers creating other implementation in different packages than `org.kohsuke.args4j.spi. (@cyrille-leclerc)

### 2.0.24 (2013/07/01)
- [0001d43](https://github.com/kohsuke/args4j/commit/0001d434a585f7cd556c98aea35a6ac7a2075207) fixed URL (@kohsuke)
- [d381dad](https://github.com/kohsuke/args4j/commit/d381dadc393f9cb70b300f558ae5b8d04c323671) turns out I have a.b.c.d.COMPANY.COM (@kohsuke)
- [#20](https://github.com/kohsuke/args4j/pull/20) Introduce hidden options (@maginatics)
- [1acff43](https://github.com/kohsuke/args4j/commit/1acff436c29ed9c0f9ce81ad845b3df7480dde83) Massaged the contribution from @andrewgaul. (@kohsuke)
- [26d7381](https://github.com/kohsuke/args4j/commit/26d7381ef9cda6c4ed788924925807d72919f15a) opening this up for subtypes to better control visibility (@kohsuke)
- [#23](https://github.com/kohsuke/args4j/pull/23) I added a handler for the java 7 Path class (@madkrupt)
- [346c8b8](https://github.com/kohsuke/args4j/commit/346c8b82ef999b814bdd6b92e99cfcd6fb5fd805) TAB -> WS (@kohsuke)
- [8452398](https://github.com/kohsuke/args4j/commit/845239896fcead92f5d4c5d9e644c3ac78a1e0d1) doc improvements (@kohsuke)
- [560e6ac](https://github.com/kohsuke/args4j/commit/560e6ac534a9910716e7e419897aa9bd63f5d7a6) code should still run on Java6 (@kohsuke)
- [#21](https://github.com/kohsuke/args4j/pull/21) support for option of type InetAddress (@dlad)
- [#26](https://github.com/kohsuke/args4j/pull/26) Git Ignore Idea (@cyrille-leclerc)
- [d361b18](https://github.com/kohsuke/args4j/commit/d361b18871fbeedb7fa42ad1720106dc4ab478fc) doc clarification (@kohsuke)
- [e462e1b](https://github.com/kohsuke/args4j/commit/e462e1b9011edce9987163275bdc4b35aec5fd20) Revisted ARGS4J-25 patch. (@kohsuke)
- [06b413b](https://github.com/kohsuke/args4j/commit/06b413b01cafe2dcbedfb82ac9e75a969a8c5ebe) Fix ARGS4J-25 - support args like "-coll val1 val2 -coll val3" with `@Option(handler = StringArrayOptionHandler.class, …)` (@cyrille-leclerc)
- [9f29776](https://github.com/kohsuke/args4j/commit/9f297765e50ae09914bf7e1e81c46bbc2b709b12) fixed up the code. Now I feel a little better. (@kohsuke)
- [fc296a0](https://github.com/kohsuke/args4j/commit/fc296a01004a23cfb16b78a14630991cc7fcbec3) Improving the doc (@kohsuke)

### 2.0.23 (2013/03/12)
- [a9fd083](https://github.com/kohsuke/args4j/commit/a9fd083a59e1b34708ecaf75c519a16b60193562) doc improvement (@kohsuke)
- [b36291f](https://github.com/kohsuke/args4j/commit/b36291f9cfbf917e5531fb98b4e39104a88bc3e8) added a new OptionHandler that implements a typical sub-command pattern (@kohsuke)
- [a1e5a30](https://github.com/kohsuke/args4j/commit/a1e5a30310a9c39d4bd053cfa3cc915afc876cbe) Allow OptionHandlers to access other annotations (@kohsuke)
- [d729f9a](https://github.com/kohsuke/args4j/commit/d729f9a6700aa9cef64b574c46237d7c8be4a4fb) Fixed issue #10. (@kohsuke)
- [1370c9e](https://github.com/kohsuke/args4j/commit/1370c9e890d9f5dd7e7647eb1055fed14f40318e) Added Setter.asFieldSetter() (@kohsuke)
- [7960ba1](https://github.com/kohsuke/args4j/commit/7960ba1b5a4b30bfc1ab430baf765d80174afdbe) Fixed the way MapOptionHandler works (@kohsuke)
- [4aa760d](https://github.com/kohsuke/args4j/commit/4aa760d91e7336d794fcc69e6a4e086af6e40322) added Setter that handles an array (@kohsuke)
- [e883b93](https://github.com/kohsuke/args4j/commit/e883b9395b866ed9bd297bee534a04a8bbe83a37) It doesn't make sense for @Option to have multi-value setting. (@kohsuke)
- [03134f5](https://github.com/kohsuke/args4j/commit/03134f5253ec57afec5a6664fff1228f19d80feb) fixed issue #12 but differently from pull request #15 (@kohsuke)
- [#19](https://github.com/kohsuke/args4j/pull/19) Generate OSGi metadata in JAR files (@johnkeeping)

### 2.0.22 (2012/06/25)
- [e02f327](https://github.com/kohsuke/args4j/commit/e02f3273e8d1bc168489445d573cfdb80404ecfd) Let's see if this would let me deploy to central (@kohsuke)

### 2.0.21 (2012/04/27)
- [66b564e](https://github.com/kohsuke/args4j/commit/66b564ecb4d9e8015b7f7bd702a1bdb2091c2dc1) Need to have them accept the groupId before I can sync. (@kohsuke)

### 2.0.20 (2012/04/26)
- [b9dc50f](https://github.com/kohsuke/args4j/commit/b9dc50fb044e4daf173d50c762b211c17d80c1ff) retargetting to the central deployment (@kohsuke)
- [#8](https://github.com/kohsuke/args4j/pull/8) deprecreated constructor in example (@oluies)
- [#9](https://github.com/kohsuke/args4j/pull/9) Adding support for '-' in enums. (@grossws)

### 2.0.19 (2011/12/12)
- [9c5bc87](https://github.com/kohsuke/args4j/commit/9c5bc87d26bb631612a08404aa5e0f5b90169ad4) Added a handler to support explicitly set booleans, i.e. '-myOpt false' and '-myOpt true' rather than '-myOpt' for true and omitting the option for false (@demobox)
- [b57c129](https://github.com/kohsuke/args4j/commit/b57c1295af888400c43667073070f6f2e35959f8) enable the EnumOptionHandler to handle illegal arguments. (@KengoTODA)
- [3c49565](https://github.com/kohsuke/args4j/commit/3c495651b297da1c55c5cb319529d32ed7bd6f05) remove unnecessary lines. sorry. (@KengoTODA)
- [6fb79d8](https://github.com/kohsuke/args4j/commit/6fb79d861046b7d14085c65bb90c7a00388ca1a6) Adds tests for parsing of arguments to ExampleTest. This shows how arguments are actually parsed. (@leanto)

### 2.0.18 (2011/08/09)
- [a4ba15b](https://github.com/kohsuke/args4j/commit/a4ba15be429a0a9f899f9b37e84e126ee6220023) aligned version numbers (@kohsuke)
- [#1](https://github.com/kohsuke/args4j/pull/1) TXT file output and args4j maven plugin (@lacostej)

### 2.0.17 (2011/07/28)
- [5b00df6](https://github.com/kohsuke/args4j/commit/5b00df666283ef176cd7efcef3160df7b689f1d8) fixed links (@kohsuke)
- [b915e61](https://github.com/kohsuke/args4j/commit/b915e6138acce9a62826c23cfdb7ada214726388) Ignore generated files. (@kohsuke)
- [28a10d2](https://github.com/kohsuke/args4j/commit/28a10d26d89b596903c5d65f03bd21c7923c4fc2) moved website (@kohsuke)
- [213ba86](https://github.com/kohsuke/args4j/commit/213ba86c60336bb3c9a75d1dd6acb2e13d7332ef) pushing web contents to GitHub pages (@kohsuke)
- [cae3abc](https://github.com/kohsuke/args4j/commit/cae3abc32854d5b4e6c46ff8bba76ecea6ad70ea) partially fixed to deploy on new java.net
- [0b95c1f](https://github.com/kohsuke/args4j/commit/0b95c1f1e4ea44dbe450bc97e5549290e652dda8) updated to MIT license, to be consistent with https://args4j.dev.java.net/
- [c4df6af](https://github.com/kohsuke/args4j/commit/c4df6af406b14dd3184065c29712f5b0934e64c9) JavaDocs. Problem with @-sign in package.html, need help here.
- [329c0e5](https://github.com/kohsuke/args4j/commit/329c0e552375a4f9649a41e836f075b8bacf1831) annotationType cannot change in the middle, so it should be final.
- [78067cd](https://github.com/kohsuke/args4j/commit/78067cdd3aa235c43ff8a266f80b547ec99aa12e) Regression: the ' sign wasnt loaded correctly for unit tests, so use " instead
- [ae6f740](https://github.com/kohsuke/args4j/commit/ae6f74097376d0afc0604dc0cd914b14931970ac) New MetadataParser (FieldParser) which makes all Fields available by their names (Foo.bar -> -bar)
- [c8c81c5](https://github.com/kohsuke/args4j/commit/c8c81c5f04853f2495dc911d984aac7d30d68067) More I18N
- [ffe93fb](https://github.com/kohsuke/args4j/commit/ffe93fba1387b096dfb751b4c57ab9fe3df5681c) Found some not externalized Messages.
- [9b8dc05](https://github.com/kohsuke/args4j/commit/9b8dc05015eb1a877eedc5445a04f8043e489701) Wasnt sure by myself, so document after a test
- [fc85e79](https://github.com/kohsuke/args4j/commit/fc85e7921d114ee808916b0d00f034f66760b8f7) - write my name without special character like in other classes here

### 2.0.16 (2009/09/04)
- [54fe739](https://github.com/kohsuke/args4j/commit/54fe739dcefafb0183b02a85899fee37ad0232a6) updating control files
- [7043519](https://github.com/kohsuke/args4j/commit/7043519d09849eeb26e498aa147626bb5b40e1bc) allowing these annotations to be on parameters, so that other higher-level frameworks can use method invocation binding for CLI.
- [27f98ee](https://github.com/kohsuke/args4j/commit/27f98eeec1b29304351afe1e19ac533158cb5200) added a convenient overload.
- [db7b8d3](https://github.com/kohsuke/args4j/commit/db7b8d3f506d9ab9a755b73700d7e255ebed42e1) doc improvement.
- [40875c1](https://github.com/kohsuke/args4j/commit/40875c1eae65ab935e392c92e4d1ec1ad50802cd) Exposing arguments directly.
- [15aa33b](https://github.com/kohsuke/args4j/commit/15aa33b42db7398fec119fa52ce2d749c162cad3) - The point of exposing CmdLineParser.addOption and addArgument is to open up metadata parsing to the caller. So in that spirit, there's no need for CmdLineParser to sniff the incoming bean for how to read metadata. It's best to leave that to the caller.
- [83bf6d7](https://github.com/kohsuke/args4j/commit/83bf6d79ccb1cd6be4b75180bbc38c532a3ca73d) - removed dupliation.
- [4ddab12](https://github.com/kohsuke/args4j/commit/4ddab1204e505d9ced575d6af6d943a264831818) Hint for updating changes.xml
- [6259586](https://github.com/kohsuke/args4j/commit/6259586cc463554564f6b0374d95a93cded39d28) - final implementation of XML parsing
- [1cc855e](https://github.com/kohsuke/args4j/commit/1cc855e946670a94428d8014ee881dfbcdbe17dc) this must be a left-over debug statement.
- [3767a51](https://github.com/kohsuke/args4j/commit/3767a51ece6afba00417fa68fcf3581482a320c2) 2.0.15 is already released, so the next version should be 2.0.16
- [3a8d0e6](https://github.com/kohsuke/args4j/commit/3a8d0e697de4bf87e2aeaab3cd5c41c495928740) these files sould be generated from POM
- [e46b847](https://github.com/kohsuke/args4j/commit/e46b8479b132d2a3964ef4d4e06b710433a2e584) Prepare for alternative 'markup' (instead of Annotations)
- [86a0836](https://github.com/kohsuke/args4j/commit/86a083624034798d9a1bbc35dfa98acaa7cd283d) Prepare for alternative 'markup' (instead of Annotations)
- [d1ad6d4](https://github.com/kohsuke/args4j/commit/d1ad6d47baf38c3fcf1836b5ce89c9dda3f302b7) Make CmdLineParser.addArgument() + .addOption() public.

### 2.0.15 (2009/07/30)
- [9331ddf](https://github.com/kohsuke/args4j/commit/9331ddf894b396e8f7bfefc38a87d5074cbb4260) fixed a bug in the error message generation
- [150698b](https://github.com/kohsuke/args4j/commit/150698b523cca1600431521f16647da006d900e6) It's a kind of assert-method, so name it assert*
- [ca0cc9a](https://github.com/kohsuke/args4j/commit/ca0cc9ad4228169441fc9341a13b543641e1fde0) Add a test how to handle thrown exceptions thrown in setters.
- [fb80d05](https://github.com/kohsuke/args4j/commit/fb80d05ab998d90e43b01f9c4148f58c2ab19e52) added a handler type that effectively terminates the option processing
- [ccc09f4](https://github.com/kohsuke/args4j/commit/ccc09f4b428ccc76091df0447192cb5bfa91f8c3) added a method to return the # of remaining tokens.
- [d808406](https://github.com/kohsuke/args4j/commit/d8084069273126526c62480d7b1a3fb62a617912) doc improvement
- [5a6b7f4](https://github.com/kohsuke/args4j/commit/5a6b7f472fae494a2502849c52b3dd76f76ca1ce) applied a patch from Olav Reinert about Mac tools.jar handling
- [ce399fb](https://github.com/kohsuke/args4j/commit/ce399fb71974fea8528e83cdc929d8ad04ad83f2) working around a bug in the java.net cvs updater
- [b229f81](https://github.com/kohsuke/args4j/commit/b229f816b8ec79ec1ee4eaa0762c5e55cd2bb486) This commit was generated by cvs2svn to compensate for changes in r351,
- [dbe9d28](https://github.com/kohsuke/args4j/commit/dbe9d281244f43fe4ecbffe6c1753c39f3257f5b) This commit was generated by cvs2svn to compensate for changes in r348,
- [fb5d1d0](https://github.com/kohsuke/args4j/commit/fb5d1d05bb31f4754645a51727acbdc2966711a4) working around a bug in the java.net cvs updater

### 2.0.14 (2009/05/05)
- [240b9c9](https://github.com/kohsuke/args4j/commit/240b9c94ccd69780ed1e2df9139c114dd65cefad) ignore *.ipr
- [a9084b8](https://github.com/kohsuke/args4j/commit/a9084b8c6e387690bdeb9153145c0948170e27f5) applied a patch from Daniel <daniel.j.larsson@gmail.com>
- [ebb14ec](https://github.com/kohsuke/args4j/commit/ebb14ec4aea8a13c98d7ef44915e2180f907f8cd) working around a bug in the java.net cvs updater
- [42eace0](https://github.com/kohsuke/args4j/commit/42eace024dfc2018124f5c8a47dff1737ea4dd6c) This commit was generated by cvs2svn to compensate for changes in r338,
- [9531746](https://github.com/kohsuke/args4j/commit/9531746e624458dc26ef4ed338f2280b0e12e4e7) This commit was generated by cvs2svn to compensate for changes in r335,
- [abd9cb6](https://github.com/kohsuke/args4j/commit/abd9cb6eb785cf9b4a1bba7f00f2354b2e308689) working around a bug in the java.net cvs updater

### 2.0.13 (2009/04/17)
- [f191d2d](https://github.com/kohsuke/args4j/commit/f191d2d6a7041eef55abd2b42cf5fea69493a7d8) shouln't have project files
- [6a0c6a9](https://github.com/kohsuke/args4j/commit/6a0c6a9c64e8b75c2741cf32ccd6d7fb9fd382b8) improved line wrapping
- [2c0443e](https://github.com/kohsuke/args4j/commit/2c0443e6507fd4ed02428e5a0a48dfb874f21341) avoid showing whitespace at the beginning of lines
- [ad92b59](https://github.com/kohsuke/args4j/commit/ad92b59adf555682418d4c806f690764040d24ce) working around a bug in the java.net cvs updater
- [5f9423a](https://github.com/kohsuke/args4j/commit/5f9423a2d309904e001f937745b31cf44e0ef17e) This commit was generated by cvs2svn to compensate for changes in r324,
- [a0df45c](https://github.com/kohsuke/args4j/commit/a0df45c7aae9564f3687a068b89b1fdd02c10394) This commit was generated by cvs2svn to compensate for changes in r321,
- [2eef265](https://github.com/kohsuke/args4j/commit/2eef265b10dea6c029f24f23ee1f519e7272fdab) working around a bug in the java.net cvs updater

### 2.0.12 (2009/04/06)
- [db50cc2](https://github.com/kohsuke/args4j/commit/db50cc2a20a6cf451b2df623cc248f7ba657f28c) preparing for 1.0.12 release
- [feffdeb](https://github.com/kohsuke/args4j/commit/feffdeb0906e7996ddca26cd319f6c4179a824eb) added a OptionHandler for URI
- [292dcd4](https://github.com/kohsuke/args4j/commit/292dcd4668311a7a73871c693d1750c42a2f190c) wrong meta var
- [da455c0](https://github.com/kohsuke/args4j/commit/da455c005534ca92e846217c99dc7be51fd31ed8) updated to the new repository location
- [9019dce](https://github.com/kohsuke/args4j/commit/9019dce6712036880e84b12c482871e00ecc0660) Use the correct syntax for multivalued options: -flag value1 -flag value2
- [883c29e](https://github.com/kohsuke/args4j/commit/883c29e17c65826042012c251662055c85ef0099) Add German resource bundle
- [45c1048](https://github.com/kohsuke/args4j/commit/45c10480ec217f1a10004e022840a7ef94765c06) When using (and having now) localized resource bundles, we have to use the right one during tests. So for tests use the US one.
- [0ecedc2](https://github.com/kohsuke/args4j/commit/0ecedc21ceb1f2a4dba18205cbe72f8e1455232b) JUnit test for @Option(multiValued=true).
- [4a92220](https://github.com/kohsuke/args4j/commit/4a9222045c75d97f9ecfe093902fada684c446a5) working around a bug in the java.net cvs updater
- [abf4197](https://github.com/kohsuke/args4j/commit/abf41974ccbbc3a76499941468723129cb876a7d) This commit was generated by cvs2svn to compensate for changes in r305,
- [1b24cb6](https://github.com/kohsuke/args4j/commit/1b24cb6e0c40df2866b1f72b63f804f7d08b0746) This commit was generated by cvs2svn to compensate for changes in r302,
- [1aa9980](https://github.com/kohsuke/args4j/commit/1aa99809742a5e6c4cb609c1434cf42c7b3cf594) working around a bug in the java.net cvs updater

### 2.0.11 (2009/01/30)
- [e986348](https://github.com/kohsuke/args4j/commit/e986348a626c3541d39e1579f0491f8f5395468c) since 2.0.10 is already pushed. pushing 2.0.11 to be sure that people can depend on new bits
- [620dd1f](https://github.com/kohsuke/args4j/commit/620dd1f75b1ffdd1fcead21d704fa19415c6d222) working around a bug in the java.net cvs updater
- [0b37a18](https://github.com/kohsuke/args4j/commit/0b37a1885296d95f6d483702f4e8f2ee39335f2f) This commit was generated by cvs2svn to compensate for changes in r293,
- [a51e34d](https://github.com/kohsuke/args4j/commit/a51e34ddd8db8ceb535440b4a6c5d184d5381639) working around a bug in the java.net cvs updater
- [7b4e95b](https://github.com/kohsuke/args4j/commit/7b4e95bf04a1b09a8934ed481eb46c9bbfd56069) This commit was generated by cvs2svn to compensate for changes in r289,

### 2.0.10 (2009/01/30)
- [d870560](https://github.com/kohsuke/args4j/commit/d870560ed3a5bc5a4e8c3fc58ccba3ce7ffd0bef) ignore IDE project files
- [9cc19e6](https://github.com/kohsuke/args4j/commit/9cc19e6d34a87b44c7b095d38f0ddd849050e817) now I remember how to do a release here
- [16c1516](https://github.com/kohsuke/args4j/commit/16c15168b9960a62124b7dd5748659a7740f8206) working around a bug in the java.net cvs updater
- [921b41f](https://github.com/kohsuke/args4j/commit/921b41f7aca409c465bf306c193383dc46434b55) This commit was generated by cvs2svn to compensate for changes in r278,
- [5ece2bd](https://github.com/kohsuke/args4j/commit/5ece2bd4be9fc49774e49328bbc2896db8985a8e) working around a bug in the java.net cvs updater
- [46a67ad](https://github.com/kohsuke/args4j/commit/46a67ad1c51d5ae151dea320349c59bb2e68ed38) This commit was generated by cvs2svn to compensate for changes in r275,
- [431c180](https://github.com/kohsuke/args4j/commit/431c1807af494bf5b2798547676e33e90573028a) working around a bug in the java.net cvs updater
- [751f7cf](https://github.com/kohsuke/args4j/commit/751f7cf2f45f06bed29fa0661d0c93c72ca4c8f9) doing this differently
- [9f61ca4](https://github.com/kohsuke/args4j/commit/9f61ca4e0f224f54d5080f76edac1c542ccdcd0c) doing this differently
- [ca87b26](https://github.com/kohsuke/args4j/commit/ca87b262bc25d0eb99a35cb5779d569fae1caae1) make sure we run with Java5
- [1570253](https://github.com/kohsuke/args4j/commit/15702531559af18d0bdef2f42c0a1b42e076bfbc) make sure we run with Java5
- [60d7683](https://github.com/kohsuke/args4j/commit/60d76837de6ebc1ce46ae23a64fdd46ab6426281) applied a fix suggested by Erb Cooper <ecooper@liquidnet.com>
- [5b94b74](https://github.com/kohsuke/args4j/commit/5b94b7411d1ec5ed6ad4278a9e576241ac10b568) fixed a bug pointed out by vlad smyshlyaev <albedo072@gmail.com>
- [183709c](https://github.com/kohsuke/args4j/commit/183709c83e1637bed5cb4017abd243ed0a84fb22) working around a bug in the java.net cvs updater
- [ca9245d](https://github.com/kohsuke/args4j/commit/ca9245de2fca645355e6e6aa928d5fce76d19d4d) This commit was generated by cvs2svn to compensate for changes in r262,
- [80e4814](https://github.com/kohsuke/args4j/commit/80e4814570945da3f2bbf7595f4f14eb6edeed8d) working around a bug in the java.net cvs updater
- [648d247](https://github.com/kohsuke/args4j/commit/648d24723ea5560d7bdd5d4e4b0337dc7c4200e5) This commit was generated by cvs2svn to compensate for changes in r259,
- [3ac39ad](https://github.com/kohsuke/args4j/commit/3ac39adf075b2e3fb65da9ce169c8f89fd485382) working around a bug in the java.net cvs updater
- [57c97b6](https://github.com/kohsuke/args4j/commit/57c97b645365aa95103582b39626c1c37e5f3889) just a formatting change
- [c5b1641](https://github.com/kohsuke/args4j/commit/c5b1641cc6a53afb23da3363f5f0efa50265b3dc) added Russian translation from vlad smyshlyaev <albedo072@gmail.com>
- [3921fdf](https://github.com/kohsuke/args4j/commit/3921fdfdecb9941feffeae306d05459da5108187) bug fix pointed out by Constantine Plotnikov.
- [9d6b89c](https://github.com/kohsuke/args4j/commit/9d6b89c70b5e4f6ebf0df4ee954bc353bf465b59) working around a bug in the java.net cvs updater
- [754e22a](https://github.com/kohsuke/args4j/commit/754e22addad7566f01e70b3deb1b4ad4ffe549f0) This commit was generated by cvs2svn to compensate for changes in r249,
- [5972ddf](https://github.com/kohsuke/args4j/commit/5972ddfef3bae539336c332d088a2b3b34eebb90) working around a bug in the java.net cvs updater
- [042c6d2](https://github.com/kohsuke/args4j/commit/042c6d21e7e6736dd87d6981f9333250b6a523b1) This commit was generated by cvs2svn to compensate for changes in r246,
- [6ff618d](https://github.com/kohsuke/args4j/commit/6ff618d53b642705fd22a3a9797df951d825ec1d) working around a bug in the java.net cvs updater
- [7492373](https://github.com/kohsuke/args4j/commit/74923739f0a1db5afa249311bfc4e91a9924ac6e) Added URLOptionHandler
- [a1eddee](https://github.com/kohsuke/args4j/commit/a1eddee2191af6fd5f540250cd642ad7e7e1a713) doc fix
- [309a1c5](https://github.com/kohsuke/args4j/commit/309a1c5d600adb3deab072ab9cc5eb92ecf32eaf) worked around an issue with Javac1.5
- [78b3734](https://github.com/kohsuke/args4j/commit/78b37347f4a771947840cadcfa403a288660d88c) Contributed by Ľubomír Varga <varga@plaintext.sk>
- [da2aaed](https://github.com/kohsuke/args4j/commit/da2aaed5ff800444c1a0c1714ec893f62dbf0fa4) I believe this is just a mistake introduced by the IDE when he added this new class. I'm removing this to have this file use the same LICENSE.txt that this project as a whole uses.
- [4010b9a](https://github.com/kohsuke/args4j/commit/4010b9a4e98cff0886d8316afdb81c3150187d0d) Update "current version"
- [ce6164a](https://github.com/kohsuke/args4j/commit/ce6164a7ae5d677f49d6eb3d6bfbc1449d66bbff) 1st line wrapping: split hard on character position (than on words)
- [91a37ef](https://github.com/kohsuke/args4j/commit/91a37efb11da61f02cc02fdf6d128cd08f301c9f) Make Apache Gump happy with non UTF-8 characters.
- [935f709](https://github.com/kohsuke/args4j/commit/935f709e86b71c41484c1d96b1fe9dc16f4827dd) Convenience method for setting the args of the parser.
- [9417e47](https://github.com/kohsuke/args4j/commit/9417e47b5716641d16a58f124f4fb4700c570e83) Issue10 fixed
- [b92bf79](https://github.com/kohsuke/args4j/commit/b92bf791c86bc4b34f88339156499d59de380286) Rewrite of printOption()
- [d5e2795](https://github.com/kohsuke/args4j/commit/d5e27950fc82cc43dcbec702fc6ec6742fe3eb99) Testcase for Issue10.
- [9b9412d](https://github.com/kohsuke/args4j/commit/9b9412d193359f48fa210b5428908e807edb2d78) re-pushing 2.0.9 docs
- [d2f5e06](https://github.com/kohsuke/args4j/commit/d2f5e062e0047bd3cbcb8e2d9acd5650bbe59827) Pushing new API docs.
- [22e9f87](https://github.com/kohsuke/args4j/commit/22e9f8726093365c34ebcb4567c2180a7cd83dcd) working around a bug in the java.net cvs updater
- [babace2](https://github.com/kohsuke/args4j/commit/babace289efa9b141b0dcecc679cf1d3fec7a7c2) working around a bug in the java.net cvs updater
- [62e6e7e](https://github.com/kohsuke/args4j/commit/62e6e7e4d5ea493c504a9e00cc19b564920fc27c) This commit was generated by cvs2svn to compensate for changes in r225,
- [85c7f3b](https://github.com/kohsuke/args4j/commit/85c7f3bc60a4acc5b19d8e23851675aaae3b1034) This commit was generated by cvs2svn to compensate for changes in r222,
- [989892e](https://github.com/kohsuke/args4j/commit/989892e36e712801d8c9178eaa48808f631383fa) working around a bug in the java.net cvs updater

### 2.0.9 (2008/06/16)
- [1a9c986](https://github.com/kohsuke/args4j/commit/1a9c986017693c0ea886efe150dc94a6cd5a1509) Fix error handling while parsing Map-Input
- [0c6fd86](https://github.com/kohsuke/args4j/commit/0c6fd86da4e53d7d2c91d43754423f8bca195323) Handler for Maps.
- [f441ae2](https://github.com/kohsuke/args4j/commit/f441ae243f0251f1e171941ad7b2efd239024486) Refactoring
- [53cfbb7](https://github.com/kohsuke/args4j/commit/53cfbb72ee532570c4d5139466344ba09fc36fd7) Support of all native Java types.
- [a6fb6b2](https://github.com/kohsuke/args4j/commit/a6fb6b22a302e25102774d58ddf6cd26b84ab5d8) looks like this configuration is invalid

### 2.0.8 (2008/04/01)
- [018c772](https://github.com/kohsuke/args4j/commit/018c772c910648e768cd23dd796d3544df2ca506) added wagon-svn
- [d430914](https://github.com/kohsuke/args4j/commit/d430914feacd29ffbcb0ad51492c656ca27ee0f5) converting to Maven2
- [27da861](https://github.com/kohsuke/args4j/commit/27da86175f307fc562dc1875b3ffbdfa9dcdafaf) I found a (french) comparison between Commons CLI and Args4J
- [20a1660](https://github.com/kohsuke/args4j/commit/20a16606a28c23d40b25a47992a244a057855f40) Add Mark as developer.
- [d1df405](https://github.com/kohsuke/args4j/commit/d1df4054316643d6bd55a0d21d516ee75dc6d6ce) working around a bug in the java.net cvs updater
- [1dfdec3](https://github.com/kohsuke/args4j/commit/1dfdec38030a5f653bb2392410f8ead0ce28eac3) This commit was generated by cvs2svn to compensate for changes in r197,
- [5a12471](https://github.com/kohsuke/args4j/commit/5a12471eb93a49219e7b7c59e4a3724bddc43e82) working around a bug in the java.net cvs updater
- [8378079](https://github.com/kohsuke/args4j/commit/8378079887381c72f4f5537d35d9cda58b3b2276) This commit was generated by cvs2svn to compensate for changes in r194,
- [5eb93b7](https://github.com/kohsuke/args4j/commit/5eb93b7063502b35f25f11835453d9783ed93540) working around a bug in the java.net cvs updater
- [06371fc](https://github.com/kohsuke/args4j/commit/06371fc6206335c2ae51bfdec88023a0ea1fe75d) bumping up to 2.0.8project.xml
- [34988e9](https://github.com/kohsuke/args4j/commit/34988e94637232dc150b9386765f899c07767eec) Add some notes about @option on setter methods. Indent.
- [7e3d60f](https://github.com/kohsuke/args4j/commit/7e3d60f4b984d5bfdf5ac2b08833e9080fab7233) Bug 4: Better readability of Java5 enums usage
- [061ace2](https://github.com/kohsuke/args4j/commit/061ace203f21c248c507e374bb9a1df909a47a8f) Bug 5: Options without "usage" are hidden.
- [63d35cd](https://github.com/kohsuke/args4j/commit/63d35cd77540b298eea313dfe7ef395e9dc73a6f) CmdLineParser now correctly identifies long option values (that was missing from the previous commit)
- [27cab03](https://github.com/kohsuke/args4j/commit/27cab037bba3ed35fa43f07bc95b25a69fbec779) Obtained from: Igor Lankin
- [b94fb5c](https://github.com/kohsuke/args4j/commit/b94fb5cd7bc9c2701dc19b761548628f53e4b0af) Refactoring on CmdLineParser for more commonality between options and arguments
- [38e6d29](https://github.com/kohsuke/args4j/commit/38e6d2925ee56762c8ec6ce3ef6c67e28ec63573) Submitted by:  Mark Sinke
- [c69aadf](https://github.com/kohsuke/args4j/commit/c69aadfcfcd47b29ba8be3328ccd2dbd414d6e65) Submitted by:  Mark Sinke
- [7aec2f4](https://github.com/kohsuke/args4j/commit/7aec2f4bac2d530bae4db923188115bd35743681) deleting as it's currently not used.
- [75551e5](https://github.com/kohsuke/args4j/commit/75551e56bf63f486258ffc34e751b54545b8c242) this is an experiment I did locally a long time ago.
- [393bdf8](https://github.com/kohsuke/args4j/commit/393bdf8a14722c7f3be37cbb3cc9b0061847cf69) I cant remember of doing a change ;-)
- [505fe83](https://github.com/kohsuke/args4j/commit/505fe839c0b05831816310994206e01d9cf3b64a) working around a bug in the java.net cvs updater
- [fe69c7e](https://github.com/kohsuke/args4j/commit/fe69c7eb0dd23bdfff9e726c18c68535734cdd81) This commit was generated by cvs2svn to compensate for changes in r175,
- [fd68661](https://github.com/kohsuke/args4j/commit/fd686612dbb8def17fb7da0620420ae20b589814) working around a bug in the java.net cvs updater
- [e5b8b40](https://github.com/kohsuke/args4j/commit/e5b8b40edd4acf54fa50ad12be9a6d2946d95b36) This commit was generated by cvs2svn to compensate for changes in r172,
- [de2e769](https://github.com/kohsuke/args4j/commit/de2e7695ecdbad1c73e8b4bd2bd0f91d5eff0ed1) working around a bug in the java.net cvs updater

### 2.0.7 (2006/08/16)
- [642a304](https://github.com/kohsuke/args4j/commit/642a3041aaed2e7aa61d36baca77e1f17bf83b1c) posting 2.0.7 to fix a packaging problem
- [726ed3c](https://github.com/kohsuke/args4j/commit/726ed3cf9d3e5ac662b2a8c8e90c72366f32ae97) Added a link to RSS
- [b9802bd](https://github.com/kohsuke/args4j/commit/b9802bd9f78a82e1c44be1ddabc27f27565cf4dd) working around a bug in the java.net cvs updater
- [b0a9dfb](https://github.com/kohsuke/args4j/commit/b0a9dfb8097a2a6a254264b0494545b114d67a3e) This commit was generated by cvs2svn to compensate for changes in r163,
- [00a0916](https://github.com/kohsuke/args4j/commit/00a091616ff43f3acfdb5528c138a808172be420) set the release date
- [4943a8a](https://github.com/kohsuke/args4j/commit/4943a8af7193ddc1bbe66398e45e6d86884f3fd2) working around a bug in the java.net cvs updater
- [1208798](https://github.com/kohsuke/args4j/commit/1208798fa4d0c84aea22bc0f4c1316f6c5ce0a61) This commit was generated by cvs2svn to compensate for changes in r159,
- [3381595](https://github.com/kohsuke/args4j/commit/3381595f0d97804aa8312efebf3941ed87929eb3) working around a bug in the java.net cvs updater
- [a955e21](https://github.com/kohsuke/args4j/commit/a955e2114a070367599c2268964de5247e3e6b54) This commit was generated by cvs2svn to compensate for changes in r154,
- [83713b2](https://github.com/kohsuke/args4j/commit/83713b224f025aef272935660457fea3318421b5) working around a bug in the java.net cvs updater

### 2.0.6 (2006/04/05)
- [29b6336](https://github.com/kohsuke/args4j/commit/29b6336818e260908d01179cd0ba771e450efcb2) ups .... had forgotten one place...
- [5caa130](https://github.com/kohsuke/args4j/commit/5caa130c37286199ac0e0b252ba6dc188f5e3bd8) Gump found the first error.... I use that class for setting the key-value pairs (without success at the moment).
- [81ad500](https://github.com/kohsuke/args4j/commit/81ad500a8e0daa190bbc5f66208d851c9fcd31f8) Document the  setUsageWidth()  method.
- [d5905e0](https://github.com/kohsuke/args4j/commit/d5905e097d87938fba92ac54647d62ce56eb4ea2) Issue number:  3
- [2a314da](https://github.com/kohsuke/args4j/commit/2a314daa775f88c9c9560c8c13b58d5333194f75) I hope I found the failing test for Gump...
- [45407aa](https://github.com/kohsuke/args4j/commit/45407aa8d6066d1f04def7883bcac6f33074aa24) Gump doesnt like this testcase. Maybe I had forgotten to deactivate them while implementing the MapHandler.
- [8f35602](https://github.com/kohsuke/args4j/commit/8f35602a5f6bbaa4bac21088f51385c3e61e03e5) Args4J is build by Apache Gump
- [11e1e3d](https://github.com/kohsuke/args4j/commit/11e1e3d46bdd7126e0c071ef368fbeffb83fc989) Remove dependency to Ant 1.5.
- [5523cc4](https://github.com/kohsuke/args4j/commit/5523cc468f27db71b86272231ade022a6ada00db) Gump does not like non-ASCII characters ...
- [3f66768](https://github.com/kohsuke/args4j/commit/3f667682bd1923aaace954b5bb341a8615a75dad) Gump seems not liking non-ASCII characters...
- [583f657](https://github.com/kohsuke/args4j/commit/583f6579aee63f349432bc8eb1f9003c89cddca2) generified OptionHandler by following malachi's suggestion.
- [61dd2a5](https://github.com/kohsuke/args4j/commit/61dd2a5e8604996c3990c0cc2fc3feef296d923a) Make it easier to start a class using the Starter. Documented in a short tutorial.
- [15ffe6d](https://github.com/kohsuke/args4j/commit/15ffe6d7e42e2eb5cc42df7fa60821ac8da99991) Format + XHTML
- [a3126b0](https://github.com/kohsuke/args4j/commit/a3126b02715cbaac3eb3ca38f3441397800cde49) Resources about Args4J
- [7c380f7](https://github.com/kohsuke/args4j/commit/7c380f7998cdd2b8ef875be9e4f32aa62d82bc85) working around a bug in the java.net cvs updater
- [0c66e42](https://github.com/kohsuke/args4j/commit/0c66e4254cbb7a1b61b1b118311a31ef542e46fb) This commit was generated by cvs2svn to compensate for changes in r134,
- [e31cbee](https://github.com/kohsuke/args4j/commit/e31cbee78cc6fcb12641f2dc0b8aa9e264c2120b) - more XHTML checking
- [fb75525](https://github.com/kohsuke/args4j/commit/fb75525ca1dfa5a8a16fce42ec57001ab4e53c6a) - Point to the maven repository at dev.java.net which hosts args4j.
- [6f3addf](https://github.com/kohsuke/args4j/commit/6f3addfd4b6cd4a1bf9445b5f339c7b957c613b6) Add a maven changes.xml document for documenting changes between versions. Add the needed report to the project. Because we dont distinguish between args4 and args4j-tools while releasing I add only one document.
- [77a3ec8](https://github.com/kohsuke/args4j/commit/77a3ec879b7928ef55c7f0c8eedfbdb72c257ef4) working around a bug in the java.net cvs updater
- [df6a989](https://github.com/kohsuke/args4j/commit/df6a9898030f04bf79911233ef4715e461e6ce36) This commit was generated by cvs2svn to compensate for changes in r126,
- [6a97c71](https://github.com/kohsuke/args4j/commit/6a97c71c180d25da99a1898bd302949330b8c3ca) working around a bug in the java.net cvs updater

### 2.0.5 (2006/02/15)
- [fa1c0a6](https://github.com/kohsuke/args4j/commit/fa1c0a620764bd15f5f69faf56e4907235490000) - implemented the stop option "--".
- [8710a6a](https://github.com/kohsuke/args4j/commit/8710a6afe71b5d468e4e065f16579202e731f7ea) Annotations are not designed for use in interfaces.
- [e1d44d3](https://github.com/kohsuke/args4j/commit/e1d44d3169553f4be8d2e88384c0ffa84f063d93) Test the inheritence of @Option's. Annotations made in interfaces are NOT handled. Bug or Feature? ;-)
- [2044608](https://github.com/kohsuke/args4j/commit/204460892a70b3ee387166ad30aa8806956789e1) whitespaces
- [2234fd4](https://github.com/kohsuke/args4j/commit/2234fd48074446c808a2ab06c7828ef1ab0bc0e2) How to handle properties? Here some test cases for discussion from the users point of view.
- [e55cce0](https://github.com/kohsuke/args4j/commit/e55cce0b5604bdb5668305d25fe7908c80f90a57) Check the usage messages.
- [ac104fd](https://github.com/kohsuke/args4j/commit/ac104fddf96235c6a24ff5639a894af680885c6b) Dont do property tests here any more. Will be new tests :-)
- [aca4afa](https://github.com/kohsuke/args4j/commit/aca4afaeb193c3eff1d5f010ab915744de18f4a7) typo
- [c7f0ed7](https://github.com/kohsuke/args4j/commit/c7f0ed76d74b808dcf5a6c2afb36987ed2613a0e) Ignore the "field is never read" warnings.
- [91371ed](https://github.com/kohsuke/args4j/commit/91371ed1c3b1869f36cdc7eb3e166ea11c1e04e4) Ignore the "field is never read" warnings.
- [9acc728](https://github.com/kohsuke/args4j/commit/9acc728dc887bc073d0af5e4c03b087d18a65b12) Organize import: remove ExampleMode
- [5a1f2ae](https://github.com/kohsuke/args4j/commit/5a1f2aeca8a6b69a868fc53e1e1ce57dff2ee732) Usage info for the -custom option and use of that data.
- [f392dfe](https://github.com/kohsuke/args4j/commit/f392dfe15c70eb6fd20fc83486e7bcaba48deb75) Let Maven compile the examples. Maybe more Ant- than Maven-like, but I know more of Ant than of Maven ;-)
- [e9f72f7](https://github.com/kohsuke/args4j/commit/e9f72f786170546effc97c55b9bd7b8c32e46e30) working around a bug in the java.net cvs updater
- [a714f14](https://github.com/kohsuke/args4j/commit/a714f1417069fe14f61df564f88e0601a76fce9e) This commit was generated by cvs2svn to compensate for changes in r105,
- [480788d](https://github.com/kohsuke/args4j/commit/480788d05a83faca4de8038df42e58946b0f94a6) working around a bug in the java.net cvs updater

### 2.0.4 (2006/01/29)
- [8cf251b](https://github.com/kohsuke/args4j/commit/8cf251ba4acf2580397610d111ad4537005b0b4d) added a comment to explain Option.handler()
- [e4942e3](https://github.com/kohsuke/args4j/commit/e4942e33fe197a606111a56d3c8803fcfcee8645) expanded @Option to allow a Handler to be explicitly specified.
- [930de79](https://github.com/kohsuke/args4j/commit/930de792f4cd83c137491d826a5677d808f36486) Ignore Eclipse configuration
- [a129834](https://github.com/kohsuke/args4j/commit/a129834b6ab21fe14274a8e07b74433b6946c0be) Tried to rebuild the Apache Ant command line interface.
- [775ad92](https://github.com/kohsuke/args4j/commit/775ad92e78fd764e4035f2efa9c5247643c095d1) Extract the properties test and javadoc.
- [e905ee3](https://github.com/kohsuke/args4j/commit/e905ee3c243e8c9ed47c1bdab8b6b430c4179b77) Some new tests for the basics.
- [6a269a5](https://github.com/kohsuke/args4j/commit/6a269a5481710109aec2d112cf73bab85cd98a9d) New developer.
- [3a69cb6](https://github.com/kohsuke/args4j/commit/3a69cb66f202286198b15f588fba4c2941f7b7d5) working around a bug in the java.net cvs updater
- [d496a7a](https://github.com/kohsuke/args4j/commit/d496a7a3aca17c0496330fae927aaaf6b9bcd3e7) This commit was generated by cvs2svn to compensate for changes in r90, which
- [79c557b](https://github.com/kohsuke/args4j/commit/79c557b4ad057cb00e711b9fef6f5d59e046db0f) working around a bug in the java.net cvs updater

### 2.0.3 (2006/01/13)
- [acc4891](https://github.com/kohsuke/args4j/commit/acc4891ac6145d6e2cc86015b804c05160220335) added goal
- [eaffce6](https://github.com/kohsuke/args4j/commit/eaffce69704d11eabbba40bbc43adc7485f6d51f) got POM inheritance right
- [5e97de9](https://github.com/kohsuke/args4j/commit/5e97de9e5ea29515ed92608c946ee36516c13f75) 2.0.3 with DoubleOptionHandler
- [8584254](https://github.com/kohsuke/args4j/commit/85842547ffe5e82247e5c61a020854acdc8d281c) added contributed DoubleOptionHandler
- [fa53bf2](https://github.com/kohsuke/args4j/commit/fa53bf2714c084e315453da7b02deebbbfcf9b27) working around a bug in the java.net cvs updater
- [44f1ae0](https://github.com/kohsuke/args4j/commit/44f1ae01dca8005d6246302099247727a4e3c344) This commit was generated by cvs2svn to compensate for changes in r79, which
- [26d25be](https://github.com/kohsuke/args4j/commit/26d25bed6726a42dac12a4b6ab187fddffe711ef) working around a bug in the java.net cvs updater
- [bfc438e](https://github.com/kohsuke/args4j/commit/bfc438ebf6496cddbd4319a1e43e3cbb819aa6d3) This commit was generated by cvs2svn to compensate for changes in r75, which
- [58aa43d](https://github.com/kohsuke/args4j/commit/58aa43dbb02922985f2c970d152ad50864d81017) working around a bug in the java.net cvs updater
- [5ff59cd](https://github.com/kohsuke/args4j/commit/5ff59cd9cd00e6ad177caddac7cf0090eb5fa865) updated
- [5ab54f5](https://github.com/kohsuke/args4j/commit/5ab54f5096a7e8ff7bd2c8fc988754c72c22979d) added description
- [83a92cd](https://github.com/kohsuke/args4j/commit/83a92cdb7f460fbd2055dd74d04351a6505afc98) fixed a bug in the unit test pattern
- [83a4706](https://github.com/kohsuke/args4j/commit/83a4706542570934b628fa9838584dc6d7bc7124) added printExample.
- [8418212](https://github.com/kohsuke/args4j/commit/841821273e4c03bbbdeec6eec8ac8aa60006898e) implemented the printExample method.
- [d70b9c8](https://github.com/kohsuke/args4j/commit/d70b9c8409c1e175eb4928f7fefa5f5f03f6f972) project file update to include JUnit test cases.
- [6ae50b4](https://github.com/kohsuke/args4j/commit/6ae50b4e8ff9165f555b3355eee3874acff92aa5) added an unit test.
- [12e20a9](https://github.com/kohsuke/args4j/commit/12e20a92a6a2bab6214dcc4530e713192fd7a4c5) implemented issue 1: required option.
- [d79ac4b](https://github.com/kohsuke/args4j/commit/d79ac4bcaa9805a4368db3ad596ac0d0746df4ef) added libraries for IDEs.
- [4dfde58](https://github.com/kohsuke/args4j/commit/4dfde5804a29259aa5b71bba4f7c125383a6f310) working around a bug in the java.net cvs updater
- [10ac2c8](https://github.com/kohsuke/args4j/commit/10ac2c86bec734c33a19c4f1092920c6c61b1da7) This commit was generated by cvs2svn to compensate for changes in r61, which
- [0391e13](https://github.com/kohsuke/args4j/commit/0391e13842e21368bf97ac4376c210bb5ff4263e) working around a bug in the java.net cvs updater
- [1306ecb](https://github.com/kohsuke/args4j/commit/1306ecb8ece33eef9e71c3ba430ab828c10faea7) This commit was generated by cvs2svn to compensate for changes in r57, which
- [7ab2aa5](https://github.com/kohsuke/args4j/commit/7ab2aa57c91bb25ced315a2583da3755e7f8664f) working around a bug in the java.net cvs updater
- [5c3f968](https://github.com/kohsuke/args4j/commit/5c3f968f417a2277684e56a11c7c6ee722a60796) Repository deployment
- [5e16778](https://github.com/kohsuke/args4j/commit/5e16778bfffee92f4706b7206389af45abd9eb37) added a feature
- [aceb12b](https://github.com/kohsuke/args4j/commit/aceb12b6d77a348678662235f638dbca7906d161) bug fixes
- [2856b85](https://github.com/kohsuke/args4j/commit/2856b85cbb75261f7c5386fb36bc58d2bc78ad2b) working around a bug in the java.net cvs updater
- [87003e0](https://github.com/kohsuke/args4j/commit/87003e00944f680c1dd8a739f11ca00a743757d0) This commit was generated by cvs2svn to compensate for changes in r48, which
- [8f87d48](https://github.com/kohsuke/args4j/commit/8f87d4888e7bef2d787ebef2b797b6afc739e196) working around a bug in the java.net cvs updater
- [81db78f](https://github.com/kohsuke/args4j/commit/81db78f6a84ca4ce3e1b062e6c8e4a53974b6d36) This commit was generated by cvs2svn to compensate for changes in r44, which
- [6f6f84a](https://github.com/kohsuke/args4j/commit/6f6f84a3c3e09220842043cbf939976358a53208) working around a bug in the java.net cvs updater
- [04f69ba](https://github.com/kohsuke/args4j/commit/04f69ba195c78980e77d18bf5be546207767b82b) This commit was generated by cvs2svn to compensate for changes in r40, which
- [7299bd4](https://github.com/kohsuke/args4j/commit/7299bd432f27e963f099cb3aebc3760a1b2a5817) updated version number toward 2.0.1
- [a5f06b3](https://github.com/kohsuke/args4j/commit/a5f06b369490d93f37c354f03f0982d42bf39718) implemented metavariable support.
- [4c7e88a](https://github.com/kohsuke/args4j/commit/4c7e88a8b25b34bd114dff33e375f6c0a82aae2a) working around a bug in the java.net cvs updater
- [b78d86e](https://github.com/kohsuke/args4j/commit/b78d86e8a0432e8c27ff399feda56f8cd56cbfe5) This commit was generated by cvs2svn to compensate for changes in r34, which
- [cafa72d](https://github.com/kohsuke/args4j/commit/cafa72db4990f7ee555e8681963bb46f32d01a10) this script is for releasing a version to java.net
- [bf050b5](https://github.com/kohsuke/args4j/commit/bf050b5165c696117e18f5c05d08b24f5926bcd9) fixed a typo.
- [b3c511a](https://github.com/kohsuke/args4j/commit/b3c511aec22d2c327c0effd1a8d644582f7000a6) removed unused fields.
- [2a9fc09](https://github.com/kohsuke/args4j/commit/2a9fc0911f863a7e0b782d32a06a34370b6593e7) improved javadoc
- [eb9246f](https://github.com/kohsuke/args4j/commit/eb9246f11c7d43dac7c838a9d0d7afa84992825f) added another HTML for explaining how to write your own OptionHandler.
- [0a3df7a](https://github.com/kohsuke/args4j/commit/0a3df7a3dcf3e16212887f585dae8e3b8faaae80) bundling args4j runtime to tools for the convenience.
- [f1b471c](https://github.com/kohsuke/args4j/commit/f1b471c2c289be20f72b9beb4089081c05ce0706) better to have separate dist dir for runtime and tools
- [80aa7a1](https://github.com/kohsuke/args4j/commit/80aa7a1686e045e8b2b3684b5e14b4a2477e2407) print help screen if no option is given
- [6a96132](https://github.com/kohsuke/args4j/commit/6a96132557491c09a2b6caae6c07dc799b19205f) added documentation about args4j-tools
- [ae37947](https://github.com/kohsuke/args4j/commit/ae3794774997778a104cf04c2cfe96de69c3c5ba) added a few more links
- [14df463](https://github.com/kohsuke/args4j/commit/14df463abc3ca6b48169da2f27f26dc13661076e) removed redundant header
- [6699fc2](https://github.com/kohsuke/args4j/commit/6699fc2c35c441172068ee27fd2d679c6fee47fc) fixed image layout
- [db832a8](https://github.com/kohsuke/args4j/commit/db832a89a9faac813be0610e5b6620ecd5f376fe) h2 -> h3
- [ef8fac1](https://github.com/kohsuke/args4j/commit/ef8fac1c74409a3027f616e1a412483093fb17f1) javadoc improvement
- [8ec48d2](https://github.com/kohsuke/args4j/commit/8ec48d2c8c2004a3ebb9199ceecd14bc84f12a69) working around a bug in the java.net cvs updater
- [bf70a34](https://github.com/kohsuke/args4j/commit/bf70a342f03cccb711c11d8c7db2e40d2342c7a6) This commit was generated by cvs2svn to compensate for changes in r16, which
- [a215ed2](https://github.com/kohsuke/args4j/commit/a215ed2b92e19fae971f6e746d44502df6442b0f) ignore maven artifacts
- [a6fbd6b](https://github.com/kohsuke/args4j/commit/a6fbd6bf71607af30f62eb7890ad50659f73eb0c) modified to use POM inheritance
- [1ba9732](https://github.com/kohsuke/args4j/commit/1ba97321be9ad5ee7c3a056ab3fe83a456fa547e) Contents moved to www folder
- [3c3eec3](https://github.com/kohsuke/args4j/commit/3c3eec32cb786e4563d5a19ae07d69fad0975ff2) added logo
- [38d712d](https://github.com/kohsuke/args4j/commit/38d712d08657dcacd359aea75caa7dbea9cd1d4f) updated. fixed links, etc.
- [df60653](https://github.com/kohsuke/args4j/commit/df60653e270ae4cf60ef7c2cb9a7dcaffc5e1dfa) added a document.
- [97b4935](https://github.com/kohsuke/args4j/commit/97b493517cd368b66abcdf4fb9da077fd062de34) implemented resource support
- [2adf569](https://github.com/kohsuke/args4j/commit/2adf569bc900be47e1706ecc87590a256f7f2780) split out args4j-tools
- [8b702cb](https://github.com/kohsuke/args4j/commit/8b702cb4b922da08987b67d3db70035970aa8e33) implemented a tool that generates the usage XML/HTML.
- [a9068b2](https://github.com/kohsuke/args4j/commit/a9068b2d4fc2384d1bc086c4ffbd4d561fc9b686) implemented a case-insensitive search.
- [b5339c5](https://github.com/kohsuke/args4j/commit/b5339c5a775f89567de53683bbbde3fb187ad5c5) implemented args4j 2.0.
- [12e62c6](https://github.com/kohsuke/args4j/commit/12e62c6f0a0603e2052d4501de0625885dcb3584) check point.
- [882b99d](https://github.com/kohsuke/args4j/commit/882b99d467ac91d3a5efcb09d1c80545fe0c4fb1) moving the repository from SourceForge.
- [48534e4](https://github.com/kohsuke/args4j/commit/48534e40ec6fd2f399e995e5e10d3f70d80e41db) Commit by Helm::Ccvs
- [38b873c](https://github.com/kohsuke/args4j/commit/38b873c7bc59253e76df90d23e1ab99243db82b2) Standard project directories initialized by cvs2svn.
