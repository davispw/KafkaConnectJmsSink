package com.davispw.kafka.connect.jms.sink;

import org.apache.kafka.common.Configurable;
import org.apache.kafka.connect.sink.SinkRecord;

import javax.jms.*;
import java.util.Objects;

public class DefaultJmsMessageConverter implements JmsMessageConverter {

    @Override
    public Destination getDestination(Session session, SinkRecord record) throws JMSException {
        // Just an example.  You can imagine a JmsMessageConverter that would
        // send to a configurable topic, or a dynamic one based on the contents of the record for example.
        return session.createTopic(record.topic());
    }

    @Override
    public Message toMessage(Session session, SinkRecord record) throws JMSException {
        Message message;
        Object value = record.value();
        if (value instanceof byte[]) {
            message = session.createBytesMessage();
            ((BytesMessage) message).writeBytes((byte[]) record.value());
        } else {
            message = session.createTextMessage(Objects.toString(record.value()));
        }
        return message;
    }
}