package kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @author jql
 * @create 2018-02-26 9:36
 * @desc kafka生产者demo
 **/
public class KafkaProducerDemo {

    private final Producer<String, String> kafkaProducer;

    public final static String TOPIC = "JAVA_TOPIC";

    private KafkaProducerDemo() {
        kafkaProducer = createKafkaProducer();
    }

    private Producer<String, String> createKafkaProducer() {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.44.131:9092");
        props.put("bootstrap.servers", "localhost:9092");
        //The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.
        //“所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
        props.put("acks", "all");
        //如果请求失败，生产者也会自动重试，即使设置成0 the producer can automatically retry.
        props.put("retries", 0);
        //尝试重试指定topic分区的失败请求之前等待的时间。这样可以避免在某些故障情况下高频次的重复发送请求。100 long
        props.put("retry.backoff.ms", 100);
        //The producer maintains buffers of unsent records for each partition.
        props.put("batch.size", 16384);
        //默认立即发送，这里这是延时毫秒数
        props.put("linger.ms", 1);
        //生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
        props.put("buffer.memory", 33554432);
        //The key.serializer and value.serializer instruct how to turn the key
        // and value objects the user provides with their ProducerRecord into bytes.
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);
        return kafkaProducer;
    }

    void produce() {
        for (int i = 1; i < 1000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String key = String.valueOf("key" + i);
            final String data = "hello kafka message:" + key;
            kafkaProducer.send(new ProducerRecord<>(TOPIC, key, data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    //do sth
//                    System.out.println("recordMetadata = " + recordMetadata);
//                    System.out.println("已发送 data = " + data);
                }
            });
//            kafkaProducer.send(new ProducerRecord<String, String>(TOPIC, 0, Integer.toString(i), Integer.toString(i)));
            System.out.println(data);
        }
        kafkaProducer.close();
        kafkaProducer.flush() ;//所有缓存记录被立刻发送
    }

    public static void main(String[] args) {
        new KafkaProducerDemo().produce();
    }
}

