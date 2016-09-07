#!/usr/bin/env bash

if  [ $# -ne 1 ]
then
    echo $0 server_ip:port;
    exit -1;
fi

SERVER_IP_PORT=$1
BIN_DIR=`dirname $0`
LOG_DIR=${BIN_DIR}/../logs
LIB_DIR=${BIN_DIR}/../lib

if [ ! -d ${LOG_DIR} ]
then
    mkdir -p ${LOG_DIR};
fi

java -cp ${LIB_DIR}/native-jetty-websocket-example-1.0-SNAPSHOT-jar-with-dependencies.jar org.eclipse.jetty.demo.pubsub.AmbariSubscriberClient ${SERVER_IP_PORT} &> ${LOG_DIR}/subscriber.log &