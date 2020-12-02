flink run --jobmanager yarn-cluster --detached --parallelism 1 --yarnname WeatherApp \
-yt /opt/demo/SmartWeather/application.properties \
weather-1.0.jar \
--kafka.bootstrap.servers edge2ai-1.dim.local:9092 \
--kafkaTopic weathernj \
--hdfsOutput hdfs:///tmp/weatherapp \
--properties.file /opt/demo/SmartWeather/application.properties
