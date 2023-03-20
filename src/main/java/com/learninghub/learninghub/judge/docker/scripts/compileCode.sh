#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 CONTAINER_NAME PATH_TO_FILE"
	exit
fi

docker exec $1 gcc /files/$2 -o files/main