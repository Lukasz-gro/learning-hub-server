#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 file.cpp file.x"
	exit
fi

g++ $1 -o $2
