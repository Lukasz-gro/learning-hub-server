#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 file.py file.in"
	exit
fi

touch out
python3 "$1" < "$2" > out 2>error

if [ -s "error" ]; then
  cat error
  exit 1
else
  cat out
fi
