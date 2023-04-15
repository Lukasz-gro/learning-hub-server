#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 CONTAINER_NAME PATH_TO_FILE"
	exit
fi

docker cp $2 $1:/judge