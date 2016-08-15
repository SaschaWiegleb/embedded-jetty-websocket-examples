#!/usr/bin/env bash
START_PORT=$1
NUM_INSTANCES=$2
END_PORT=$((START_PORT + NUM_INSTANCES - 1))

for i in `seq ${START_PORT} ${END_PORT}`
do
    mvn exec:java -Dexec.mainClass=org.eclipse.jetty.demo.pubsub.PublisherServer -Dexec.args="localhost:${i}" &> logs/localhost_${i}.log &
done