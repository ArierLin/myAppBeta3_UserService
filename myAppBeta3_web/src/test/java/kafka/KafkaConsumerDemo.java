package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author jql
 * @create 2018-02-26 9:38
 * @desc kafka消费者demo
 **/
public class KafkaConsumerDemo {

    private final KafkaConsumer<String, String> consumer;

    private KafkaConsumerDemo() {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.44.131:9092");
        props.put("bootstrap.servers", "localhost:9092");
        //消费者的组id
        props.put("group.id", "test");
        //从poll(拉)的回话处理时长
        props.put("session.timeout.ms", "30000");
        //poll的数量限制
        //props.put("max.poll.records", "100");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }

    void consume() {
        consumer.subscribe(Arrays.asList(KafkaProducerDemo.TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                System.out.println("--------------消费者消费------------------");
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerDemo().consume();
    }
}
