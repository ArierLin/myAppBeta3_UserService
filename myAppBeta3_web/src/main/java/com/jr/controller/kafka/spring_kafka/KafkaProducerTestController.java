package com.jr.controller.kafka.spring_kafka;

import com.jr.kafka.producer.KafkaProducerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author jql
 * @create 2018-02-28 10:10
 * @desc kafka生产者测试（消费者使用spring启动监听，自动执行onMessage方法）
 **/
@SuppressWarnings(value="all")
@Controller
@RequestMapping(value="/kafka")
public class KafkaProducerTestController {


    @Autowired
    private KafkaProducerServer kafkaProducerServer;

    @ResponseBody
    @RequestMapping(value="testKafka_Spring")
    public String testKafka_Spring(){
        String topic = "orderTopic";
        String value = "testSpring_kafka";
        //是否使用分区 0是\1不是
        String ifPartition = "0";
        Integer partitionNum = 3;
        String role = "test";//用来生成key
        //获取消息发送结果
        Map<String, Object> res = kafkaProducerServer.sendMesForTemplate
                (topic, value, ifPartition, partitionNum, role);
        //先打印生产者的onMessage中的日志
        System.out.println("测试结果如下：===============");
        String message = (String) res.get("message");
        String code = (String) res.get("code");

        System.out.println("code:" + code);
        System.out.println("message:" + message);
        return message;
    }

//    public static void main(String[] args) {
//
//        KafkaProducerServer kafkaProducer = new KafkaProducerServer();
//        String topic = "orderTopic";
//        String value = "testSpring_kafka";
//        //是否使用分区 0是\1不是
//        String ifPartition = "0";
//        Integer partitionNum = 3;
//        String role = "test";//用来生成key
//        //获取消息发送结果
//        Map<String, Object> res = kafkaProducer.sendMesForTemplate
//                (topic, value, ifPartition, partitionNum, role);
//
//        System.out.println("测试结果如下：===============");
//        String message = (String) res.get("message");
//        String code = (String) res.get("code");
//
//        System.out.println("code:" + code);
//        System.out.println("message:" + message);
//    }
}
