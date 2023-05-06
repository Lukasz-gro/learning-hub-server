#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 file.x file.in"
	exit
fi

./$1 < $2
