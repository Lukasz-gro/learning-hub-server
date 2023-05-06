#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 file.py file.in"
	exit
fi

python3 "$1" < "$2" > out 2>error

if [ -s "error" ]; then
  cat error
else
  cat out
fi
