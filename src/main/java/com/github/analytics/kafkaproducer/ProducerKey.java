package com.github.analytics.kafkaproducer;

import com.github.analytics.entities.TEST_CONTEXT;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Properties;

public class ProducerKey {
    private static final Logger logger = LoggerFactory.getLogger(ProducerKey.class);

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, TEST_CONTEXT.BOOSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Creating Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 5; i++) {
            String recordValue = "Hello_World: " + Calendar.getInstance().getTime() + "__" + i;
            String recordKey = "id_" + i;
            ProducerRecord<String, String> record = ProducerKey.createRecord(TEST_CONTEXT.TOPIC_NAME, recordKey, recordValue);

            // asynchronous
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null == e) {
                        logger.info(
                                "Received new metadata: \n" +
                                        "Topic: " + recordMetadata.topic() + "\n" +
                                        "Partition: " + recordMetadata.partition() + "\n" +
                                        "Offset: " + recordMetadata.offset() + "\n" +
                                        "Timestamp: " + recordMetadata.timestamp() + "\n");
                    } else {
                        logger.error("Error while producing: " + e);
                    }
                }
            });
        }

        producer.flush();
        producer.close();
    }

    //Creating producer record
    private static ProducerRecord<String, String> createRecord(String topicName, String recordValue) {
        return new ProducerRecord<>(topicName, recordValue);
    }

    private static ProducerRecord<String, String> createRecord(String topicName, String recordKey, String recordValue) {
        return new ProducerRecord<>(topicName, recordKey, recordValue);
    }
}