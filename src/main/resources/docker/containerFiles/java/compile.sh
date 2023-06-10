#!/bin/bash

if [ $# -ne 1 ]; then
	echo "Correct format: $0 file.java"
	exit
fi

myClass=($(grep 'public class' $1))
myClass=${myClass[2]}
name=$(ls *.java)
name=${name%.*}
mv ${name}.java ${myClass}.java

javac ${myClass}.java 2>error

if [ -s "error" ]; then
  cat error
  exit 1
fi
