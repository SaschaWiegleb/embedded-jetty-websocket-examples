#!/usr/bin/env bash

if  [ $# -lt 5 ]
then
    echo $0 ip_address start_port num_instances num_runs plain_or_secure [keystore_dir];
    exit -1;
fi

IP_ADDRESS=$1
START_PORT=$2
NUM_INSTANCES=$3
NUM_RUNS=$4
PLAIN_OR_SECURE=$5

if [ "${PLAIN_OR_SECURE}" == "secure" ]
then
    if [ $# -ne 6 ]
    then
        echo $0 ip_address start_port num_instances num_runs secure keystore_dir;
        exit -1;
    fi
fi

KEYSTORE_DIR=$6

END_PORT=$((START_PORT + NUM_INSTANCES - 1))
BIN_DIR=`dirname $0`
LOG_DIR=${BIN_DIR}/../logs
LIB_DIR=${BIN_DIR}/../lib

if [ ! -d ${LOG_DIR} ]
then
    mkdir -p ${LOG_DIR};
fi

for i in `seq ${START_PORT} ${END_PORT}`
do
    java -cp $6:${LIB_DIR}/native-jetty-websocket-example-1.0-SNAPSHOT-jar-with-dependencies.jar org.eclipse.jetty.demo.pubsub.PublisherServer ${IP_ADDRESS}:${i} ${NUM_RUNS} ${PLAIN_OR_SECURE} &> ${LOG_DIR}/publisher_${IP_ADDRESS}_${i}.log &
done
