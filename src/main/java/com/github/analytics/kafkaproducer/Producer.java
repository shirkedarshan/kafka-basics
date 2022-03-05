package com.github.analytics.kafkaproducer;

import com.github.analytics.entities.TEST_CONTEXT;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, TEST_CONTEXT.BOOSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Creating Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // asynchronous
        producer.send(createRecord(TEST_CONTEXT.TOPIC_NAME, "Hello_World"));

        producer.flush();
        producer.close();
    }

    //Creating producer record
    private static ProducerRecord<String, String> createRecord(String topicName, String recordValue) {
        return new ProducerRecord<>(topicName, recordValue);
    }
}