#!/bin/bash

export PGPASSWORD=`echo $DB_PASSWORD`
source ${PWD}/functions.sh

for file in courses/* ; do
  add_course $file
done
