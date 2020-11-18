HADOOP_USER_NAME=hdfs hdfs dfs -mkdir -p /tmp/weatherapp
HADOOP_USER_NAME=hdfs hdfs dfs -chmod -R 777  /tmp/weatherapp
HADOOP_USER_NAME=hdfs hdfs dfs -mkdir -p /tmp/weatherappnj
HADOOP_USER_NAME=hdfs hdfs dfs -chmod -R 777  /tmp/weatherapp
HADOOP_USER_NAME=hdfs hdfs dfs -mkdir -p /tmp/weatherappny
HADOOP_USER_NAME=hdfs hdfs dfs -chmod -R 777  /tmp/weatherapp

/opt/cloudera/parcels/CDH/bin/kafka-topics --create --bootstrap-server edge2ai-1.dim.local:9092 --replication-factor 1 --partitions 1 --topic weathernj
/opt/cloudera/parcels/CDH/bin/kafka-topics --create --bootstrap-server edge2ai-1.dim.local:9092 --replication-factor 1 --partitions 3 --topic weatherny



