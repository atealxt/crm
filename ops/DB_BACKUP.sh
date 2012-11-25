#!/bin/sh

HOST="127.0.0.1"
DBNAME="ZHYFOUNDRY_CRM"
USER=""
PASSWORD=""

TIMESTAMP=`date +%Y-%m-%d_%H%M`
FILE_NAME="MYSQL_$DBNAME.sql"

echo 
echo Backup start

cd $1

echo Dump start
mysqldump -h$HOST -u$USER -p$PASSWORD --hex-blob $DBNAME > ./$FILE_NAME
echo Dump end

echo Zip start
tar czf ./$FILE_NAME.$TIMESTAMP.tar.gz ./$FILE_NAME
rm -f ./$FILE_NAME
echo Zip end

echo Backup end

echo 
echo "File path: " 
pwd
echo "File name: "
echo $FILE_NAME.$TIMESTAMP.tar.gz
echo 

