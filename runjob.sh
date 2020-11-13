flink run --jobmanager yarn-cluster --detached --parallelism 2 --yarnname WeatherApp \
-yt application.properties \
weather-1.0.jar \
--kafka.bootstrap.servers edge2ai-1.dim.local:9092 \
--kafkaTopic weather \
--hdfsOutput hdfs:///tmp/weatherapp \
--properties.file application.properties
