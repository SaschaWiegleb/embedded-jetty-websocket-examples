#!/usr/bin/env bash
ps -ef | grep PublisherServer | grep java | awk '{print $2}' | xargs kill