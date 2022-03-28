# Wait to be sure that SQL Server came up
echo "------- begin run-initialization -------"
sleep 30s

# Run the setup script to create the DB and the schema in the DB
# Note: make sure that your password matches what is in the Dockerfile
for file in /user/src/app/*.sql
  do /opt/mssql-tools/bin/sqlcmd -U sa -P 14256958Sa -l 30 -e -i $file
done
#/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P 14256958Sa -d master -i script.sql
echo "------- end run-initialization -------"