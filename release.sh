#!/bin/sh -xe
cd args4j
maven javanet:dist javanet:site javanet:deploy-jar jar:install
cd ../args4j-tools
maven javanet:dist javanet:deploy-jar jar:install
cd ..
