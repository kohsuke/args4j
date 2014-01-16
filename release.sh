#!/bin/sh -xe
# run this after manually updating project.xml and xdocs/changes.xml 
# to point to the next release version
mvn -B release:prepare release:perform
