CREATE TABLE weatherkudu
(`location` STRING,`observation_time` STRING, `credit` STRING, `credit_url` STRING, `image` STRING, `suggested_pickup` STRING, `suggested_pickup_period` BIGINT,
`station_id` STRING, `latitude` DOUBLE, `longitude` DOUBLE,  `observation_time_rfc822` STRING, `weather` STRING, `temperature_string` STRING,
`temp_f` DOUBLE, `temp_c` DOUBLE, `relative_humidity` BIGINT, `wind_string` STRING, `wind_dir` STRING, `wind_degrees` BIGINT, `wind_mph` DOUBLE, `wind_gust_mph` DOUBLE, `wind_kt` BIGINT,
`wind_gust_kt` BIGINT, `pressure_string` STRING, `pressure_mb` DOUBLE, `pressure_in` DOUBLE, `dewpoint_string` STRING, `dewpoint_f` DOUBLE, `dewpoint_c` DOUBLE, `windchill_string` STRING,
`windchill_f` BIGINT, `windchill_c` BIGINT, `visibility_mi` DOUBLE, `icon_url_base` STRING, `two_day_history_url` STRING, `icon_url_name` STRING, `ob_url` STRING, `disclaimer_url` STRING,
`copyright_url` STRING, `privacy_policy_url` STRING,
PRIMARY KEY (`location`, `observation_time`)
)
PARTITION BY HASH PARTITIONS 4
STORED AS KUDU
TBLPROPERTIES ('kudu.num_tablet_replicas' = '1');
