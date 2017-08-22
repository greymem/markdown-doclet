A doclet to output javadoc as Markdown
=================================

This library provides a doclet to output the javadoc comments from Java source code to markdown.

The base, some ideas and shamelessly stolen from the
[xml-doclet](https://github.com/MarkusBernhardt/xml-doclet) library by Markus Bernhardt.

Usage
-----

If you are using maven you can use this library by adding the following report to your pom.xml:

    <project>
    	...
    			<plugin>
    				<groupId>org.apache.maven.plugins</groupId>
    				<artifactId>maven-javadoc-plugin</artifactId>
    				<executions>
    					<execution>
    						<id>xml-doclet</id>
						<phase>prepare-package</phase>
    						<goals>
    							<goal>javadoc</goal>
    						</goals>
    						<configuration>
    							<doclet>name.neuhalfen.projects.markdowndoclet.MarkdownDoclet</doclet>
    							<additionalparam>-d ${project.build.directory}/javadoc</additionalparam>
    							<useStandardDocletOptions>false</useStandardDocletOptions>
    							<docletArtifact>
    								<groupId>name.neuhalfen.projects.markdowndoclet</groupId>
    								<artifactId>markdown-doclet</artifactId>
    								<version>1.0.6</version>
    							</docletArtifact>
    						</configuration>
						</execution>
    				</executions>
    			</plugin>
    	...
    </project>
    

Options
-------

    -d <directory>            Destination directory for output file.
                              Default: .
