#!/bin/bash

if [ $# -ne 1 ]; then
	echo "Correct format: $0 CONTAINER_NAME"
	exit
fi

docker run -it --cpus=".2" -d --rm --name "$1" learning-hub-judge:latest
