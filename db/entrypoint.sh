# Run Microsoft SQl Server and initialization script (at the same time)
echo "------- begin entrypoint -------"
/opt/mssql/bin/sqlservr #& /usr/src/app/run-initialization.sh
echo "------- end entrypoint -------"