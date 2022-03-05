package com.github.analytics.kafkaconsumer;

import com.github.analytics.entities.TEST_CONTEXT;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerAssignSeek {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerAssignSeek.class);
    private static int numberOfMessagesReadSoFar = 0;
    private static boolean keepOnReading = true;
    private static final long offsetToReadFrom = 15L;
    private static final int numberOfMessagesToRead = 5;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, TEST_CONTEXT.BOOSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, TEST_CONTEXT.GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, TEST_CONTEXT.EARLIEST); //         earliest/latest/none

        // creating consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // Specifying partitions
        TopicPartition partitionToReadFrom = new TopicPartition(TEST_CONTEXT.TOPIC_NAME, 0);
        consumer.assign(Arrays.asList(partitionToReadFrom));
        consumer.seek(partitionToReadFrom, offsetToReadFrom);

        while (keepOnReading) {
            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : consumerRecords) {
                numberOfMessagesReadSoFar += 1;
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                if (numberOfMessagesReadSoFar >= numberOfMessagesToRead) {
                    keepOnReading = false;
                    break;
                }
            }
        }
        logger.info("Exiting the application");
    }
}