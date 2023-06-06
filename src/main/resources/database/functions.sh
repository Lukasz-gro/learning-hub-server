add_problem_tests() {
  path=$1
  p_id=$2
  for _test in ${path}/tests/* ; do
    t_input=`jq -r .input $_test`
    t_output=`jq -r .output $_test`
    psql -d $DB_NAME -U $DB_USERNAME -c "INSERT INTO test (problem_id, input, output) VALUES ($p_id, '$t_input', '$t_output')"
  done
}

add_course_problems() {
  course=$1
  c_id=$2
  for k in `jq -c '.problems | keys | .[]' $course` ; do
    problem=`jq -r ".problems[$k]" $course`
    p_name=`jq -r '.name' <<< $problem`
    p_id=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT id FROM problem WHERE name = '$p_name'"`
    if [ -z $p_id ] ; then
      echo "There is no \"$p_name\" problem. You need to add all problems in course before adding a course."
      continue
    fi
    ordinal_number=`jq -r '.ordinal_number' <<< $problem`
    psql -d $DB_NAME -U $DB_USERNAME -c "INSERT INTO course_problem VALUES ($c_id, $p_id, $ordinal_number)"
  done
}

add_problem() {
  path=$1
  problem=`find $path -maxdepth 1 -type f`
  p_name=`jq -r .name $problem`
  p_description=`jq -r .description $problem`
  p_prompt=`jq -r .prompt $problem`
  count=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT COUNT(id) FROM problem WHERE name = '$p_name'"`
  if [ $count -eq 1 ] ; then
    echo "Problem $p_name is already in database. If you want to update it use update_problems.sh script"
    return
  fi

  psql -d $DB_NAME -U $DB_USERNAME -c "INSERT INTO problem (name, description, prompt) VALUES ('$p_name', '$p_description', '$p_prompt')"
  p_id=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT id FROM problem WHERE name = '$p_name'"`
  add_problem_tests $path $p_id
}

update_problem() {
  path=$1
  problem=`find $path -maxdepth 1 -type f`
  p_name=`jq -r .name $problem`
  p_description=`jq -r .description $problem`
  p_prompt=`jq -r .prompt $problem`
  count=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT COUNT(id) FROM problem WHERE name = '$p_name'"`
  if [ $count -ne 1 ] ; then
    echo "There is no \"$p_name\" problem in database. To add problems use initialize_problems.sh script"
    return
  fi

  p_id=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT id FROM problem WHERE name = '$p_name'"`
  psql -d $DB_NAME -U $DB_USERNAME -c "UPDATE problem SET name = '$p_name', description = '$p_description', prompt = '$p_prompt' WHERE id = $p_id"
  psql -d $DB_NAME -U $DB_USERNAME -c "DELETE FROM test WHERE problem_id = $p_id"
  add_problem_tests $path $p_id
}

add_course() {
  course=$1
  c_name=`jq -r .name $course`
  c_short_description=`jq -r .short_description $course`
  c_description=`jq -r .description $course`
  count=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT COUNT(id) FROM course WHERE name = '$c_name'"`
  if [ $count -eq 1 ] ; then
    echo "Course $c_name is already in database. If you want to update it use update_courses.sh script"
    return
  fi

  psql -d $DB_NAME -U $DB_USERNAME -c "INSERT INTO course (name, short_description, description) VALUES ('$c_name', '$c_short_description', '$c_description')"
  c_id=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT id FROM course WHERE name = '$c_name'"`
  add_course_problems $course $c_id
}

update_course() {
  course=$1
  c_name=`jq -r .name $course`
  c_short_description=`jq -r .short_description $course`
  c_description=`jq -r .description $course`
  count=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT COUNT(id) FROM course WHERE name = '$c_name'"`
  if [ $count -ne 1 ] ; then
    echo "There is no \"$c_name\" course in database. To add courses use initialize_courses.sh script"
    return
  fi

  c_id=`psql -d $DB_NAME -U $DB_USERNAME -tc "SELECT id FROM course WHERE name = '$c_name'"`
  psql -d $DB_NAME -U $DB_USERNAME -c "UPDATE course SET name = '$c_name', short_description = '$c_short_description', description = '$c_description' WHERE id = $c_id"
  psql -d $DB_NAME -U $DB_USERNAME -c "DELETE FROM course_problem WHERE course_id = $c_id"
  add_course_problems $course $c_id
}