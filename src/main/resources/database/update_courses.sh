#!/bin/bash

export PGPASSWORD=`echo $DB_PASSWORD`
source ${PWD}/functions.sh

for file in courses/* ; do
  update_course $file
done
