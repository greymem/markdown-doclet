rm -rf target
mvn install
VERSION="0.0.1-SNAPSHOT"
javadoc -locale en -cp /Users/jens/Projects/java/markdown-doclet/target/markdown-doclet-${VERSION}-jar-with-dependencies.jar  -verbose  -doclet name.neuhalfen.projects.markdowndoclet.MarkdownDoclet  -docletpath /Users/jens/Projects/java/markdown-doclet/target/markdown-doclet-${VERSION}-jar-with-dependencies.jar -sourcepath src/main/java -d target/production  name.neuhalfen.projects.markdowndoclet.example

echo ----
head -n 15 target/production/name/neuhalfen/projects/markdowndoclet/example/Base.md
echo \\n\\n----\\n\\n
head -n 15 target/production/name/neuhalfen/projects/markdowndoclet/example/BaseWithTypeParams.md
echo \\n\\n----\\n\\n
head -n 15 target/production/name/neuhalfen/projects/markdowndoclet/example/BaseWithTypeParams.Nested.md
echo \\n\\n----\\n\\n
head -n 15 target/production/name/neuhalfen/projects/markdowndoclet/example/MyEnum.md
echo \\n\\n----\\n\\n
head -n 15 target/production/name/neuhalfen/projects/markdowndoclet/example/Example.md
echo \\n\\n----\\n\\n
head -n 15 target/production/name/neuhalfen/projects/markdowndoclet/example/ExampleTypeParams.md
