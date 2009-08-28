#!/bin/sh -xe
# run this after manually updating project.xml and xdocs/changes.xml 
# to point to the next release version
mvn -B release:prepare release:perform
cd args4j
maven clean:clean javanet:dist javanet:site jar:install
cd ../args4j-tools
maven clean:clean javanet:dist jar:install
cd ..
maven javanet:site
