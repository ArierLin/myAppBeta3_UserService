package com.jr.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jql
 * @create 2017-08-30 11:35
 * @desc HttpClient网络请求工具类
 **/
public class MyHttpClientUtil {

    /**
     * 发送post请求
     * @param url
     * @param map
     * @param encoding
     * @return
     */
    public static String sendPostRequest(String url, Map<String,String> map, String encoding){

        String responseBody = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        try {
            if(map!=null){
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            System.out.println("请求地址："+url);
            System.out.println("请求参数："+nvps.toString());
            //设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //获取结果实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    responseBody = EntityUtils.toString(entity, encoding);
                }
                //释放资源
                EntityUtils.consume(entity);
            }
            //释放链接
            response.close();
        }catch (Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            httpPost.releaseConnection();
        }
        return responseBody;
    }

    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("goodsId","1000014752");
        String encoding = "utf-8";
        String url = "https://www.baidu.com/";

        String result = sendPostRequest(url,map,encoding);
        System.out.println(result);

    }

}
