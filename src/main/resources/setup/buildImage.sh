#!/bin/bash

echo $DOCKER_DIR_PATH
docker build -t learning-hub-judge ${DOCKER_DIR_PATH}
