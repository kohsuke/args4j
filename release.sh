#!/bin/sh -xe
cd args4j
maven javanet:dist javanet:site
cd ../args4j-tools
maven javanet:dist
cd ..
