package com.davispw.kafka.connect.jms.sink;

import org.apache.kafka.connect.sink.SinkRecord;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by davispw on 2016/04/25.
 */
public interface JmsMessageConverter {
    Message toMessage(Session session, SinkRecord record) throws JMSException;
}
