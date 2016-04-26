package com.davispw.kafka.connect.jms.sink;

import javax.jms.ConnectionFactory;

/**
 * Created by davispw on 2016/04/25.
 */
public interface JmsConnectionFactoryProvider {

    ConnectionFactory createConnectionFactory();
}
