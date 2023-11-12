nux Cluster Monitoring Agent 

The Linux Cluster Monitoring Agent is a tool aimed at preserving and monitoring real-time data  from nodes running on CentOS. This application collects data in the form of host hardware usage and specification and uses a Data Definition Language (DDL) SQL script to organise and store the specified data into tables within a relational database. This project is designed for users with clusters of machines in their projects, providing real-time insights into resource usage, which can be later used to generate reports and aid in future resource planning decisions.

The technologies used are:
- Bash
- Postgres
- Docker
- Git 

# Quick Start
- Start a psql instance using psql_docker.sh
```bash 
./scripts/psql_docker.sh start
```

- Create tables using ddl.sql
```bash
./sql/ddl.sql
```

- Insert hardware specs data into the DB using host_info.sh
```bash
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```

- Insert hardware usage data into the DB using host_usage.sh
```bash
./scripts/host_usage.sh  psql_host psql_port db_name psql_user psql_password
```

- Crontab setup
```bash
crontab -e
```

# Implementation

The project comprised three main phases. Initially, a Docker container was created and used to execute a Postgres instance. Within this Postgres instance, a database named host_agent was created, and would be used to store all hardware specification and usage data.
Following this, a DDL (Data Definition Language) file was generated using Postgres. The DDL file outlined the structure of two tables: host_info, for storing data related to hardware specifications, and host_usage, for storing data related to hardware usage.
Finally, two scripts were developed using bash and Postgres. The first script, host_info.sh, acquired and inserted hardware usage data into the host_info table within the host_agent database. The second script, host_usage.sh, obtained usage data and inserted it into the database. Crontab was then used to execute the host_usage.sh script every minute, ensuring regular data updates in the database.

## Architecture


## Scripts
- psql_docker.sh
This script creates a Postgres instance inside a docker container. The Postgres instance has a username and password. The instance can be started, stopped

```bash
./scripts/psql_docker.sh start|stop|create db_username db_password
```

- host_info.sh
This script runs only once during installation. It assumes the hardware data is static and allows users to collect hardware specifications and inserts that data into the database table, host_info.

```bash
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```

- host_usage.sh
This script collects hardware usage data and is executed once every minute. Once executed, it stores the gathered data into the database table, host_usage.

```bash
.scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```

- crontab
This script automates both monitoring usage data and the insertion of that data into a database by running host_usage.sh once every minute.

## Database Modelling
- host_info

- host_usage

# Test
The bash scripts were manually tested. The Postgres instance alongside the two shell scripts, host_info.sh and host_info.sh, were tested manually on a remote desktop on the Google Cloud Platform.

# Deployment
The project was deployed using several different technologies. A GitHub repository was used to maintain and manage the code, as well as to provide documentation. Crontab was used to schedule and automate the execution of the host_usage shell script, executing it once every minute.  Docker was used to provision a PostgreSQL instance and a PostgresQL script was used to create the tables, host_info and host_usage, in the database.  The hardware specifications and usage details were then added to the database for future use. 

# Improvements

- Track the network bandwidth usage alongside other usage metrics, in order to help users assess the need for hardware upgrades or workload scaling.
- Implement a feature to detect hardware changes and update the database if any changes occur
- Create a user-friendly interface to display important metrics over time.

