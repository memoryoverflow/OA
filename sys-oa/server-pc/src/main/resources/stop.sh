#!/bin/sh
pid=$(ps -ef | grep oa.jar | grep -v grep | awk '{print $2}')

kill -9 $pid

echo $pid
