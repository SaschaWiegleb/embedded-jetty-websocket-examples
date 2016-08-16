#!/usr/bin/env bash

if  [ $# -ne 4 ]
then
    echo $0 ip_address start_port num_instances num_runs;
    exit -1;
fi

IP_ADDRESS=$1
START_PORT=$2
NUM_INSTANCES=$3
NUM_RUNS=$4
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
    java -cp ${LIB_DIR}/native-jetty-websocket-example-1.0-SNAPSHOT-jar-with-dependencies.jar org.eclipse.jetty.demo.pubsub.PublisherServer ${IP_ADDRESS}:${i} ${NUM_RUNS} &> ${LOG_DIR}/${IP_ADDRESS}_${i}.log &
done