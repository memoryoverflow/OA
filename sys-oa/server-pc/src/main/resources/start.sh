#!/bin/sh
export JAVA_HOME=/usr/local/software/java/jdk
cd /opt/project/jar/newOA
nohup>/opt/project/jar/newOA/log java -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5006   -jar oa.jar   2>& 1 &
