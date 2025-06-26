#!/usr/bin/env bash

export AGENT_APPLICATION=..

#export MAVE_PROFILE=enable-mcp

./support/check_env.sh

cd ..
mvn -Dmaven.test.skip=true spring-boot:run
