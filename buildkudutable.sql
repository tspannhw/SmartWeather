CREATE TABLE webrequests
(
  uuid STRING,
  msgdate TIMESTAMP,
  controllerid STRING, 
  endpointid STRING, 
  remoteaddress STRING,
  sessionid STRING,
  message STRING,
  localaddress STRING,
PRIMARY KEY (uuid,msgdate) ) 
PARTITION BY HASH PARTITIONS 4 
STORED AS KUDU TBLPROPERTIES ('kudu.num_tablet_replicas' = '1');
