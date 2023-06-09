#!/bin/bash

if [ $# -ne 2 ]; then
	echo "Correct format: $0 javaClass file.in"
	exit
fi

myClass=$(ls *.class)
name=${myClass%.*}

java $name < "$2" > out 2>error

if [ -s "error" ]; then
  cat error
  exit 1
else
  cat out
fi
