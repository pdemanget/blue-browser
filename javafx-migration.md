Migrating from java8 to java > 11
==================================


Empty example
-------------
See docs
https://openjfx.io/openjfx-docs/#maven


m20 is the alias for maven java 20 (simple way to have multiple version of java in parallel)
alias m20='JAVA_HOME=/d/opt/jdk-20 mvn'
   
    m20 archetype:generate -DarchetypeGroupId=org.openjfx -DarchetypeArtifactId=javafx-archetype-simple -DarchetypeVersion=0.0.3 -DgroupId=pdem     -DartifactId=demo -Dpackagename=pdem.demo.jfx -Dversion=1.0.0 -Djavafx-version=20
    
    path=%JAVA_HOME%\bin;%PATH%
    rem the plugin is not using JAVA_HOME!    
    mvn clean javafx:run
    
dependencies
---------

		<dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${jfx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${jfx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-web</artifactId>
            <version>${jfx.version}</version>
        </dependency>

		<dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${jfx.version}</version>
        </dependency>

plugin
------
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.3</version>
                <configuration>
                    <mainClass>${main.class}</mainClass>
                </configuration>
            </plugin>


your configuration
-------------------
	<properties>
		<jfx.version>20.0.2</jfx.version>
		<main.class>fil.Web</main.class>
	</properties>
   
   
maven usage
===========

simple project creation
-----------------------
mvn archetype:generate -DgroupId=fr.myGroupId -DartifactId=MyApplication -Dpackagename=fr.myGroupId -DarchetypeArtifactId=maven-archetype-quickstart
