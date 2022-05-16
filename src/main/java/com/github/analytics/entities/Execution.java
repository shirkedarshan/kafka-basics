package entities;

import context.SessionContext;
import context.TestExecutionContext;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public class Execution {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BACKGROUND_PURPLE = "\u001b[45;1m";
    private static final String ANSI_BACKGROUND_BLUE = "\u001b[44;1m";
    private static final String ANSI_BACKGROUND_CYAN = "\u001b[46;1m";
    private static final String EARLIEST = "earliest";
    private static final String LATEST = "latest";
    private final TestExecutionContext context;

    public Execution() {
        long threadId = Thread.currentThread().getId();
        this.context = SessionContext.getTestExecutionContext(threadId);
    }

    public Properties setTheStreamsProperties() {
        String appId = context.getTestStateAsString(TEST_CONTEXT.APP_ID);
        String bootstrapServers = context.getTestStateAsString(TEST_CONTEXT.BOOTSTRAP_SERVERS);

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, appId);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, LATEST);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return config;
    }

    public void writeOrPeekKtable(KTable<Object, Object> kTable, String className) {
        boolean isWritingToTopic = Boolean.parseBoolean(context.getTestStateAsString(TEST_CONTEXT.IS_WRITING_TO_TOPIC));
        String outTopic = context.getTestStateAsString(TEST_CONTEXT.OUT_TOPIC);
        if (isWritingToTopic) {
            kTable
                    .toStream()
                    .to(outTopic);
        } else {
            kTable
                    .toStream()
                    .peek((key, value) -> this.printRecordAs(className, key, value));
        }
    }

    public void writeOrPeekKStream(KStream<Object, Object> kStream, String className) {
        boolean isWritingToTopic = Boolean.parseBoolean(context.getTestStateAsString(TEST_CONTEXT.IS_WRITING_TO_TOPIC));
        String outTopic = context.getTestStateAsString(TEST_CONTEXT.OUT_TOPIC);
        if (isWritingToTopic) {
            kStream
                    .to(outTopic);
        } else {
            kStream
                    .peek((key, value) -> this.printRecordAs(className, key, value));
        }
    }

    private void printRecordAs(String className, Object key, Object value) {
        System.out.println(ANSI_BACKGROUND_BLUE + " " + className + ": " + ANSI_BACKGROUND_PURPLE + " Key: " + key + " " + ANSI_BACKGROUND_CYAN + " Value: " + value + ANSI_RESET);
    }
}
