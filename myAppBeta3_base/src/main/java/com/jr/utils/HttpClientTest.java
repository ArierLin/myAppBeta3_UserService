package com.jr.utils;

import com.jr.dto.HttpResultDto;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhangQingrong
 * @Date : 2017/8/30 15:15
 */
public class HttpClientTest {

    public static void main(String[] args) {
        try {
            //        String url = "https://10.108.68.212:8443/upp_payment/pay/uppSytPay.upp";
            String url = "http://58.248.41.144/upp_payment_ps/pay/uppSytPay.upp";
            HttpPost httpPost = new HttpPost(url);
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("postType", ""));
            nameValuePairs.add(new BasicNameValuePair("account",""));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs, "GBK");
            httpPost.setEntity(formEntity);
            HttpResultDto httpResultDto = HttpClientUtils.sendHttpPostMsg(httpPost);
            if (httpResultDto.getSuccess()){
                System.out.println("request success : " + httpResultDto);
            }else {
                System.out.println("request fail : " + httpResultDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
