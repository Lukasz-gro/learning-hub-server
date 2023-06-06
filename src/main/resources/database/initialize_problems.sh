#!/bin/bash

export PGPASSWORD=`echo $DB_PASSWORD`
source ${PWD}/functions.sh

for path in problems/* ; do
  add_problem $path
done
