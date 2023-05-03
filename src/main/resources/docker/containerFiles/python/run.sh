#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 file.py file.in"
	exit
fi

python3 $1 < $2
