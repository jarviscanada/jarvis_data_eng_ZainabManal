#! /bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
	echo "Illegal number of parameters"
	exit 1
fi

vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

cpu_number=$(lscpu | grep "CPU(s):" | awk '{print $2}' | awk -F '[^0-9]*' '$0=$1')
cpu_architecture=$(lscpu | awk '/Architecture/ {print $NF}')
cpu_model=$(lscpu | grep "Model name:" | awk '{print $3, $4, $5}')
cpu_mhz=$(lscpu | grep "CPU MHz:" | awk '{print $3}')
l2_cache=$(lscpu | grep "L2 cache" | awk '{print $3}' | awk '{gsub(/[^0-9]/,"")}1')
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')
timestamp=$(vmstat -t | tail -n1 | awk '{print $18, $19}')

insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp) VALUES('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp');"  

export PGPASSWORD=$psql_password 

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
  
