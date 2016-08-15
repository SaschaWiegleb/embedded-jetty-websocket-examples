#!/usr/bin/env bash

if  [ $# -ne 3 ]
then
    echo $0 start_port num_instances num_runs;
    exit -1;
fi


START_PORT=$1
NUM_INSTANCES=$2
NUM_RUNS=$3
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
    java -cp ${BIN_DIR}/../lib/native-jetty-websocket-example-1.0-SNAPSHOT-jar-with-dependencies.jar org.eclipse.jetty.demo.pubsub.PublisherServer localhost:${i} ${NUM_RUNS} &> ${BIN_DIR}/../logs/localhost_${i}.log &
done