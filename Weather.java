/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.flankstack;

import com.google.common.collect.ImmutableMap;
import com.hortonworks.registries.schemaregistry.client.SchemaRegistryClient;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.formats.avro.registry.cloudera.ClouderaRegistryCatalog;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
//import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.types.Row;
import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.flink.table.*;
//import org.apache.flink.api.common.serialization.SimpleStringEncoder;
//import org.apache.flink.api.java.utils.ParameterTool;
//import org.apache.flink.core.fs.Path;
//import org.apache.flink.formats.avro.registry.cloudera.ClouderaRegistryKafkaDeserializationSchema;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
//import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// weather
public class Weather {

//	private static Logger LOG = LoggerFactory.getLogger(Weather.class);
	public static void main(String[] args) throws Exception {
		ParameterTool params = Utils.parseArgs(args);

		SchemaRegistryClient client = new SchemaRegistryClient(
				ImmutableMap.of(
						SchemaRegistryClient.Configuration.SCHEMA_REGISTRY_URL.name(),
						"http://edge2ai-1.dim.local:7788/api/v1"
				)
		);
		//readSchemaRegistryProperties

		Properties kafkaProperties = Utils.readKafkaProperties( params );
//
//		KafkaDeserializationSchema<Message> schema = ClouderaRegistryKafkaDeserializationSchema
//				.builder(Message.class)
//				.setConfig(Utils.readSchemaRegistryProperties(params))
//				.build();
//
//		FlinkKafkaConsumer<Message> consumer = new FlinkKafkaConsumer<Message>(params.getRequired(K_KAFKA_TOPIC),
//				schema, Utils.readKafkaProperties(params));

//		Map<String, String> connectorProps = new Kafka()
//				.property( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//						"edge2ai-1.dim.local:9092")
//				.startFromEarliest();

		StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        streamEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		EnvironmentSettings tableSettings = EnvironmentSettings
				.newInstance()
				.useBlinkPlanner()
				.build();

		//              .inStreamingMode()
		StreamTableEnvironment tableEnv = StreamTableEnvironment
				.create(streamEnv, tableSettings);

		Map<String, String> connectorProps = new HashMap<>();
		connectorProps.put( "kafka.bootstrap.servers","edge2ai-1.dim.local:9092" );
		connectorProps.put( "bootstrap.servers", "edge2ai-1.dim.local:9092" );
		connectorProps.put( "group.id", "flinkKafkaGroup" );
		connectorProps.put( "kafka.topic", "weather");
		connectorProps.put( "kafka.security.protocol", "PLAINTEXT");

//		Map<String, String> connectorProps = new Kafka()
//				.property( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//						"edge2ai-1.dim.local:9092")
//				.property( "group.id", "flinkKafkaGroup" )
//				.property( "kafka.security.protocol", "PLAINTEXT")
//				.startFromEarliest()
//				.toProperties();

		tableEnv.registerCatalog(
				"registry", new ClouderaRegistryCatalog("registry", client, connectorProps)
		);
		tableEnv.useCatalog("registry");
		tableEnv.useDatabase( "default_database" );


		System.out.println(tableEnv.listTables());

		Table table = tableEnv.sqlQuery("SELECT * FROM weather");
		DataStream<Row> stream = tableEnv.toAppendStream(table, Row.class);
		stream.print();

		tableEnv.execute("Print");

//		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//		KafkaDeserializationSchema<weather> schema = ClouderaRegistryKafkaDeserializationSchema
//				.builder(weather.class)
//				.setConfig(Utils.readSchemaRegistryProperties(params))
//				.build();
//		FlinkKafkaConsumer<weather> consumer = new FlinkKafkaConsumer<weather>(params.getRequired(K_KAFKA_TOPIC), schema, Utils.readKafkaProperties(params));
//
//		// bug fix https://github.com/georgevetticaden/cdf-ref-app/blob/master/csp-trucking-flink/src/main/java/cloudera/cdf/csp/flink/refapp/trucking/TruckingStreamingAnalticsFlinkRefAppWithSchemaRegistry.java
//
//		// speedStream = speedStream.map(i -> i).keyBy(t -> t.getDriverId());
//// record -> record.getLatitude() + "," + record.getLongitude() + "," + record.getTemperatureString()
//
//		DataStream<String> source = env.addSource(consumer)
//				.name("Cloudera Kafka Source")
//				.uid("ClouderaKafkaSource")
//				.map(i -> i.getWeather())
//				.name("ToOutputString");
//
//		StreamingFileSink<String> sink = StreamingFileSink
//				.forRowFormat(new Path(params.getRequired(K_HDFS_OUTPUT)), new SimpleStringEncoder<String>("UTF-8"))
//				.build();
//		source.addSink(sink)
//				.name("FS Sink")
//				.uid("FS Sink");
//		source.print();
//
//		env.execute("Weather");
	}
}
