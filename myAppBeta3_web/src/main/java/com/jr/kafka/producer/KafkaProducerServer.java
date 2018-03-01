package com.jr.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.alibaba.fastjson.JSON;
import com.jr.utils.Constants.*;

/**
 * * @author jql
 *
 * @create 2018-02-28 10:05
 * @desc 生产者
 * kafkaProducer模板
 * 使用此模板发送消息
 **/
@Component
public class KafkaProducerServer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * kafka发送消息模板
     *
     * @param topic        主题
     * @param value        messageValue
     * @param ifPartition  是否使用分区 0是\1不是
     * @param partitionNum 分区数 如果是否使用分区为0,分区数必须大于0
     * @param role         角色:bbc app erp...
     */
    public Map<String, Object> sendMesForTemplate(String topic, Object value, String ifPartition,
                                                  Integer partitionNum, String role) {
        String key = role + "-" + value.hashCode();
        String valueString = JSON.toJSONString(value);
        if (ifPartition.equals("0")) {
            //获取使用的分区索引
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
            Map<String, Object> res = checkProductSentRecord(result);
            return res;
        } else {
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, valueString);
            Map<String, Object> res = checkProductSentRecord(result);
            return res;
        }
    }

    /**
     * 根据key值获取分区索引
     *
     * @param key
     * 例：String key = role + "-" + value.hashCode();
     * @param partitionNum
     * 例：3
     * @return
     * 例：0,1,2
     */
    private int getPartitionIndex(String key, int partitionNum) {
        if (key == null) {
            Random random = new Random();
            return random.nextInt(partitionNum);
        } else {
            int result = Math.abs(key.hashCode()) % partitionNum;
            return result;
        }
    }

    /**
     * 检查发送返回结果record
     *
     * @param listenableFuture
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> checkProductSentRecord(ListenableFuture<SendResult<String, String>> listenableFuture) {
        Map<String, Object> m = new HashMap<String, Object>();
        if (listenableFuture != null) {
            try {
                //producer.send发送方式为异步发送，添加消息到缓冲区等待发送，并立即返回。生产者将单个的消息批量在一起发送来提高效率。
                // 由于send调用是异步的，它将为分配消息的此消息的RecordMetadata返回一个Future。
                // 如果future调用get()，则将阻塞，直到相关请求完成并返回该消息的metadata，或抛出发送异常
                SendResult sendResult = listenableFuture.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = sendResult.getRecordMetadata().offset();
                if (offsetIndex != null && offsetIndex >= 0) {
                    m.put("code", KafkaMesConstant.SUCCESS_CODE);
                    m.put("message", KafkaMesConstant.SUCCESS_MES);
                    return m;
                } else {
                    m.put("code", KafkaMesConstant.KAFKA_NO_OFFSET_CODE);
                    m.put("message", KafkaMesConstant.KAFKA_NO_OFFSET_MES);
                    return m;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
                return m;
            } catch (ExecutionException e) {
                e.printStackTrace();
                m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
                return m;
            }
        } else {
            m.put("code", KafkaMesConstant.KAFKA_NO_RESULT_CODE);
            m.put("message", KafkaMesConstant.KAFKA_NO_RESULT_MES);
            return m;
        }
    }


}
