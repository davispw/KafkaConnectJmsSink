package com.davispw.kafka.connect.jms.sink;

import org.apache.kafka.connect.sink.SinkRecord;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Objects;

public class DefaultJmsMessageConverter implements JmsMessageConverter {

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