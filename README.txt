This is the beta test of the JDO 2 projects:

- api20 to build the jdo.jar which defines the JDO API version 2.0
- core20 the JDO2 core including utility and metadata model classes
- enhancer20 the JDO2 byte code enhancer 
- tck20 the JDO 2.0 TCK

-------------
Dependencies:
-------------

The JDO maven project define their dependencies in the project.xml file:
JDO2: api20, core20, enhancer20, tck20

-------------
Prerequisites
-------------

- Maven
You need Maven version 1.0.1 or 1.0.2. You can download maven from 
http://maven.apache.org/start/download.html

- JNDI implementation (fscontext.jar and providerutil.jar)
The JNDI test cases in tck20 need a JNDI implementation.
To configure this please check the property jndi in project.properties of ri11,
fostore20 and tck20. It lists all jars of your JNDI implementation in a 
path-like syntax. Furthermore, the three subprojects have a properties file 
test/conf/jndi.properties defining all the necessary properties of the JNDI 
implemenation. The default setting in project.properties and jndi.properties 
use Sun's File System Service Provider implementation (fscontext.jar and 
providerutil.jar) and assume to find both jars in the directory trunk/lib/ext. 
For download please go to http://java.sun.com/products/jndi/downloads/index.html,
click the Download button at 'Download JNDI 1.2.1 & More', accept a license 
agreement, download 'File System Service Provider, 1.2 Beta 3' and then unpack
the downloaded zip. It includes the jars fscontext.jar and providerutil.jar.

- JPOX
The Reference Implementation for JDO 2.0 is JPOX. The tck20 subproject 
automatically downloads the beta-6 JPOX.

- derby
The default datastore for tck20 is derby. The tck20 subproject 
automatically downloads version 10.1.1.0 of derby and derbytools.
NOTE!! Mac OSX users must uncomment derby.storage.fileSyncTransactionLog=true 
in tck20/test/conf/derby.properties.

-------
Remarks
-------

Please note, maven uses the user.home system property for the location
of the maven local repostitory: ${user.home}/.maven/repository.
Under Windows this system property is C:\Documents and Settings\<user> 
no matter what the HOME variable is set to. As a workaround, set the 
system property by adding -Duser.home=%HOME% to the environment variable 
MAVEN_OPTS.

Remarks about tck20:
This version of the TCK is now in beta test.  Feedback with other 
implementation will be very useful.  

- See Prerequisites concerning JPOX and Derby.

- Run "maven build" to build the tck.  This will compile, enhance, install 
the schemas, and run all the tests on all supported databases and identity 
types.

You may use the following custom goals and command line options
with tck20/maven:

Custom Goals:
    * runtck.jdori - runs the TCK on the JDO Reference Implementation
    * runtck.iut - runs the TCK on the implementation under test
    * installSchema - installs the database schema
    * enhance.jdori - enhances the class files using the JDO RI enhancer
    * enhance.iut - enhances the class files using the
                    implementation under test's enhancer

Command Line Options:
    -Djdo.tck.cfglist=<configuration file list>
          Overrides the definition of jdo.tck.cfglist found in
          tck20/test/conf/configuration.list by supplying
          one or more space-separated test configuration files.
          Test configuration files typically have the .conf extension.

      -Djdo.tck.dblist=<database list>
          Overrides the property value in project.properties by supplying
          one or more space-separated database names

      -Djdo.tck.identitytypes=<identity type list>
            Overrides the property value in project.properties by supplying
            one or more space-separated identity types (applicationidentity
            or datastoreidentity) to use for this run.


Maven looks for the following configuration files in test/conf:
    * configurations.list
          A list of files. Each file listed is a test configuration file.
    * test configuration files
          Each of these files sets values for
                jdo.tck.testdescription - an optional string describing
                    the purpose of these tests
                jdo.tck.classes - a list of one or more test classes.
                jdo.tck.testdata - fully qualified file name
                    (not required by all tests)
                jdo.tck.standarddata - fully qualified file name
                    (not required by all tests)
                jdo.tck.mapping - file designator that maven.xml uses
                    to build a javax.jdo.option.Mapping value and
                    corresponding schema name
    * exclude.list
          A list of test classes NOT to execute during a TCK test run
          [Not yet fully implemented]

For example, run "maven runtck.jdori" to run all tests or
"maven -Djdo.tck.cfg=<configuration file name> runtck.jdori"
to run one configuration.

Logging
Apache JDO uses the apache commons logging package for logging.
Sub-projects ri11 and tck11 use several properties files to configure logging.
- common-logging.properties: specifies the logging implementation to use.
  It is tested with apache SimpleLog and JDK 1.4 logging.
- logging.properties: logger configuration when using JDK 1.4 logging.
- simplelog.properties: logger configuration when using apache SimpleLog.

The file jdo_check.xml includes the checkstyle configuration. It is borrowed
from the sun_checks.xml, but does not use all of the sun rules and customizes 
some other rules. The checkstyle configuration is not yet finished.

Mevenide is a nice maven plugin for IDEs (see http://mevenide.codehaus.org).
You find download instructions in http://mevenide.codehaus.org/download.html.
For Netbeans, once you install the plugin, you should be able to open an 
existing maven project by File -> Open Project -> Open Project Folder.
Navigate to a directory including a maven project and choose this 
directory. Netbeans will use the project folder. If you right-click the Maven 
project you can examine the contents of the project.xml (see Properties) or 
execute goals.
