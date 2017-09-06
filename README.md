A doclet to output javadoc as Markdown
=================================

This library provides a doclet to output the javadoc comments from Java source code to markdown.

_This is probably the worst code I have ever written and has only one purpose: Get this [javadoc](https://github.com/neuhalje/bouncy-gpg) into a [CMS](https://neuhalje.github.io/bouncy-gpg/).  The code is untested, ugly, does only halve of what the 'html javadoc' does - but it works good enough._

If you want to use this code as well, I _might_ help adding some decency to it ;-)

The base was shamelessly stolen from the [xml-doclet](https://github.com/MarkusBernhardt/xml-doclet) library by Markus Bernhardt.

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
    						<id>md-doclet</id>
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
    								<version>0.0.1</version>
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

TODO
======

[x] Varargs
[ ] Freemarker from a custom folder
[ ] configure root url
[ ] configure root package
[ ] Java 8
[ ] Annotations for everything
[ ] Fields
[ ] Inherited  everything
