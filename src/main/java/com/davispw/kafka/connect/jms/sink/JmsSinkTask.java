package com.davispw.kafka.connect.jms.sink;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import javax.jms.*;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Created by davispw on 2016/04/25.
 */
public class JmsSinkTask extends SinkTask {

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    @Override
    public String version() {
        return AppInfoParser.getVersion();
    }

    @Override
    public void start(Map<String, String> props) {
        JmsSinkConfig config = new JmsSinkConfig(props);
        JmsConnectionFactoryProvider provider = config.getConfiguredInstance(JmsSinkConfig.JMS_CONNECTION_FACTORY_PROVIDER_CLASS_CONFIG,
                JmsConnectionFactoryProvider.class);
        String topicName = config.getString(JmsSinkConfig.JMS_TOPIC_NAME_CONFIG);

        ConnectionFactory connectionFactory =
                provider.createConnectionFactory();
        try {
            connection = connectionFactory.createConnection();

            boolean transacted = false;
            session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(topicName);

            producer = session.createProducer(destination);

            connection.start();
        } catch (JMSException e) {
            throw new ConnectException("Couldn't start JMS Connection", e);
        }
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        for (SinkRecord record : records) {
            try {
                Message message;
                Object value = record.value();
                if (value instanceof byte[]) {
                    message = session.createBytesMessage();
                    ((BytesMessage) message).writeBytes((byte[]) record.value());
                } else {
                    message = session.createTextMessage(Objects.toString(record.value()));
                }
                producer.send(message);
            } catch (JMSException e) {
                throw new ConnectException("Couldn't send JMS Message", e);
            }
        }
    }

    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
    }

    @Override
    public void stop() {
        try {
            connection.close();
        } catch (JMSException e) {
            throw new ConnectException("Couldn't close JMS Connection", e);
        }
    }
}

