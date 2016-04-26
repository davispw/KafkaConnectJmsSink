# KafkaConnectJmsSink

## Quick Start

```bash
# From Confluent Package dir
./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties

./bin/kafka-server-start ./etc/kafka/server.properties

# From ActiveMQ dir
./bin/activemq console

#From Confluent Package dir
PROJECT_DIR=<path to Maven project dir>
ACTIVEMQ_DIR=<path to apache-activemq-5.xx.xx>
CLASSPATH="$PROJECT_DIR"/target/kafka-connect-jms-sink-1.0-SNAPSHOT.jar:"$ACTIVEMQ_DIR"/lib/\* ./bin/connect-standalone ./etc/kafka/connect-standalone-string.properties ./etc/kafka-connect-jms-sink/quickstart-jms.properties
echo test | ./bin/kafka-console-producer --topic test_jms --broker-list localhost:9092

# From ActiveMQ dir
./bin/activemq consumer --destination topic://TEST
```
