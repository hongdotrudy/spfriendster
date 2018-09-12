#!/usr/bin/env bash

mvn clean install
docker build -t local/sp-friendster .
docker run --name spfriendster --mount type=volume,source=mongodata,target=/data/db -p 9090:9090 -d local/sp-friendster
