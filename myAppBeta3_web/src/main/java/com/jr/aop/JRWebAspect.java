package com.jr.aop;

import com.jr.ResponseCode;
import com.jr.dto.responseDTO.ResponseDto;
import com.jr.exception.JRException;
import com.jr.kafka.producer.KafkaProducerServer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * web 层切面
 * 使用环绕通知拦截所有controller的所有方法
 */
@Component
@Aspect
public class JRWebAspect {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static  final String topic = "orderTopic";
//    public static  final String value = "testSpring_kafka";
    //是否使用分区 0是\1不是
    public static  final String ifPartition = "0";
    public static  final Integer partitionNum = 3;
    public static  final String role = "test";//用来生成key

    @Autowired
    private KafkaProducerServer kafkaProducerServer;

    /**
     * 环绕通知
     */
    @SuppressWarnings("unused")
    @Around(value = "execution(* com.jr.controller.*Controller.*(..))")
    public Object controllerAround(ProceedingJoinPoint joinPoint) {
        Date startDate = new Date();
        Object[] args = joinPoint.getArgs();
        String simpleClassName = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        MDC.put("intf",simpleClassName + "." + method);
        LOG.info("call " + simpleClassName + "." + method + ", PARAMETER: " + getLog(args));
        //发送日志信息到kafka，ELK从kafka收集日志信息
        String value = "call " + simpleClassName + "." + method + ", PARAMETER: " + getLog(args);
        //获取消息发送结果
        Map<String, Object> res = kafkaProducerServer.sendMesForTemplate
                (topic, value, ifPartition, partitionNum, role);
        //执行切入点的方法
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            if(ex instanceof JRException){
                JRException nhException = (JRException)ex;
                result = new ResponseDto(nhException.getCode(),nhException.getMes());
            }else {
                LOG.error("unknow error , ",ex);
                result = new ResponseDto(ResponseCode.CODE_999.getCode(),ResponseCode.CODE_999.getDes());
            }
        } catch (Throwable throwable) {
            LOG.error("call " + simpleClassName + "." + method + " , error: ", throwable);
        }finally {
            Date endDate = new Date();
            Long time = endDate.getTime() - startDate.getTime();
            LOG.info("call " + simpleClassName + "." + method + ", [" + time + "]ms, RESULT: " + result);
        }
        return result;
    }

    /*@SuppressWarnings("unused")
    @Around(value = "execution(* com.bjucloud.*.service.*Service.*(..))")
    public Object serviceAround(ProceedingJoinPoint joinPoint) {
        Date startDate = new Date();
        Object[] args = joinPoint.getArgs();
        String simpleClassName = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        LOG.info("call " + simpleClassName + "." + method + ", PARAMETER: " + getLog(args));
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            LOG.error("call " + simpleClassName + "." + method + " , error: ", e);
        } catch (Throwable throwable) {
            LOG.error("call " + simpleClassName + "." + method + " , error: ", throwable);
        }finally {
            Date endDate = new Date();
            Long time = endDate.getTime() - startDate.getTime();
            LOG.info("call " + simpleClassName + "." + method + ", [" + time + "]ms, PARAMETER: " + result);
        }
        return result;
    }
*/
    /**
     * 获取入参日志
     */
    private String getLog(Object[] args) {
        StringBuilder logSb = new StringBuilder("[");
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            logSb.append(arg == null ? null : arg.toString());
            if (i != args.length - 1) {
                logSb.append(",");
            }
        }
        logSb.append("]");
        return logSb.toString();
    }

}
