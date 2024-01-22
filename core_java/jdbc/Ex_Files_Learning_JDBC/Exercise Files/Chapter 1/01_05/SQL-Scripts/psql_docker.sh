#! /bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Start Docker 
# Make sure you understand the double pipe operator
sudo systemctl status docker || sudo systemctl start docker

# Check container status (try the following cmds on the terminal)
 docker container inspect stk-psql 
 container_status=$?

# User switch case to handle create|stop|start options
case $cmd in
create)

  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1
  fi

  # Check the number of CLI arguments
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  # Create container 
  docker volume create stkdata

  # Start the container
  docker run --name stk-psql -e POSTGRES_PASSWORD=$db_password -d -v stkdata:/var/lib/postgresql/data -p 5432:5432 postgres 

  # Ensure you understand what `$?` represents
  exit $?
  ;;

start|stop)
  # Check instance status; exit 1 if the container has not been created
  if [ $container_status -ne 0 ]; then
    echo 'Container not created. Cannot start or stop.'
    exit 1
  fi

  # Start or stop the container
  docker container $cmd stk-psql
  exit $?
  ;;

*)

  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac

