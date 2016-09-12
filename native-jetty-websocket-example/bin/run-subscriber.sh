#!/usr/bin/env bash

if  [ $# -ne 2 ]
then
    echo $0 servers_file_path plain_or_secure;
    exit -1;
fi

SERVERS_FILE_PATH=$1
PLAIN_OR_SECURE=$2

BIN_DIR=`dirname $0`
LOG_DIR=${BIN_DIR}/../logs
LIB_DIR=${BIN_DIR}/../lib

if [ ! -d ${LOG_DIR} ]
then
    mkdir -p ${LOG_DIR};
fi

java -cp ${LIB_DIR}/native-jetty-websocket-example-1.0-SNAPSHOT-jar-with-dependencies.jar org.eclipse.jetty.demo.pubsub.SubscriberClient ${SERVERS_FILE_PATH} ${PLAIN_OR_SECURE} &> ${LOG_DIR}/subscriber.log &
