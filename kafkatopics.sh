kafka-topics --create --partitions 16 --replication-factor 1 --zookeeper $(hostname -f):2181/kafka --topic flink-weather-alerts
kafka-topics --create --partitions 16 --replication-factor 1 --zookeeper $(hostname -f):2181/kafka --topic metrics-topic.log


# schema.registry.url=http://edge2ai-1.dim.local:7788/api/v1
# 7788/swagger
