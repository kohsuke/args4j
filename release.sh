#!/bin/sh -xe
cd args4j
(java -fullversion | grep 1.5) || exit 1

maven clean:clean javanet:dist javanet:site javanet:deploy-jar jar:install
cd ../args4j-tools
maven javanet:dist javanet:deploy-jar jar:install
cd ..
maven javanet:site
