#!/bin/sh

# call mysql client from shell script without
# passing credentials on command line

# This demonstrates small single queries using
# the -e parameter.   Credentials and connection
# info are sent through standard input.


mysql_user=elastest
mysql_password=elastest
mysql_host=127.0.0.1
mysql_port=3306
mysql_database=elastest

mysql_exec_from_file() {
  local query_file="$1"
  local opts="$2"
  local tmpcnf="$(mktemp)"
  local tmpcnf="mysql.conf"
  printf "%s\n" \
      "[client]" \
      "user=${mysql_user}" \
      "password=${mysql_password}" \
      "host=${mysql_host}" \
      "port=${mysql_port}" \
      "database=${mysql_database}" \
      > "${tmpcnf}"
  chmod 600 "${tmpcnf}"

  mysql_exec_from_file_result=$(
      mysql --defaults-file="${tmpcnf}" "$opts" < "${query_file}"
  )
  rm "${tmpcnf}"
}

#DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

SCRIPT=`realpath $0`
DIR=`dirname $SCRIPT`

SQLFILE="${DIR}/mysql-test-data.sql"
echo "${SQLFILE}"

mysql_exec_from_file "${SQLFILE}"
echo "${mysql_exec_from_file_result}"
