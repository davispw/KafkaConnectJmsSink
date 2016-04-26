package com.davispw.kafka.connect.jms.sink;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import javax.jms.*;
import java.util.Collection;
import java.util.Map;

/**
 * Created by davispw on 2016/04/25.
 */
public class JmsSinkTask extends SinkTask {

    private JmsMessageConverter converter;
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
        converter = config.getConfiguredInstance(JmsSinkConfig.JMS_MESSAGE_CONVERTER_CLASS_CONFIG, JmsMessageConverter.class);

        try {
            ConnectionFactory connectionFactory = provider.createConnectionFactory();
            connection = connectionFactory.createConnection();
            boolean transacted = false;
            session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(null);
            connection.start();
        } catch (JMSException e) {
            throw new ConnectException("Couldn't start JMS Connection", e);
        }
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        for (SinkRecord record : records) {
            try {
                Destination destination = converter.getDestination(session, record);
                Message message = converter.toMessage(session, record);
                producer.send(destination, message);
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

