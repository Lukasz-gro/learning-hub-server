#!/bin/bash

export PGPASSWORD=`echo $DB_PASSWORD`
source ${PWD}/functions.sh

for path in problems/* ; do
  update_problem $path
done
