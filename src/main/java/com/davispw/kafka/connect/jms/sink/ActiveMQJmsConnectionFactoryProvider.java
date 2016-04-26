package com.davispw.kafka.connect.jms.sink;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.kafka.common.Configurable;

import javax.jms.ConnectionFactory;
import java.util.Map;

/**
 * Created by davispw on 2016/04/25.
 */
public class ActiveMQJmsConnectionFactoryProvider implements JmsConnectionFactoryProvider, Configurable {
    private String url;

    @Override
    public void configure(Map<String, ?> configs) {
        url = (String) configs.get("activemq.broker.url");
    }

    @Override
    public ConnectionFactory createConnectionFactory() {
        return new ActiveMQConnectionFactory(url);
    }
}
