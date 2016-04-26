package com.davispw.kafka.connect.jms.sink;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.common.config.ConfigDef.Importance.HIGH;
import static org.apache.kafka.common.config.ConfigDef.Importance.MEDIUM;
import static org.apache.kafka.common.config.ConfigDef.Type.CLASS;
import static org.apache.kafka.common.config.ConfigDef.Type.STRING;

/**
 * Created by davispw on 2016/04/25.
 */
public class JmsSinkConfig extends AbstractConfig {
    public static final String JMS_TOPIC_NAME_CONFIG = "jms.topic.name";
    public static final String JMS_CONNECTION_FACTORY_PROVIDER_CLASS_CONFIG = "jms.connection.factory.provider.class";
    public static final String JMS_MESSAGE_CONVERTER_CLASS_CONFIG = "jms.message.converter.class";

    private static ConfigDef config;

    static {
        config = new ConfigDef()
                .define(JMS_TOPIC_NAME_CONFIG, STRING, HIGH, "TODO")
                .define(JMS_CONNECTION_FACTORY_PROVIDER_CLASS_CONFIG, CLASS, HIGH, "TODO")
                .define(JMS_MESSAGE_CONVERTER_CLASS_CONFIG, CLASS, DefaultJmsMessageConverter.class, MEDIUM, "TODO");
    }

    public JmsSinkConfig() {
        this(new HashMap<String, String>());
    }

    public JmsSinkConfig(Map<String, String> props) {
        super(config, props);
    }
}
