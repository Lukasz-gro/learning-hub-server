#!/bin/bash

if [ $# -ne 1 ]; then
	echo "Correct format: $0 file.java"
	exit
fi

javac $1 2>error

if [ -s "error" ]; then
  cat error
  exit 1
else
  cat out
fi
