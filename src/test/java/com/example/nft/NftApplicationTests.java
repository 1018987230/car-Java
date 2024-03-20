//package com.example.nft;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//
//@SpringBootTest
//class NftApplicationTests {
//
//    private CloseableHttpClient httpClient;
//    @Test
//    void contextLoads() {
//    }
//    @Test
//    public void CreateOrder() throws Exception{
////请求URL
//        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/h5");
//
//// 请求body参数
//        String reqdata = "{"
//                + "\"amount\": {"
//                + "\"total\": 100,"
//                + "\"currency\": \"CNY\""
//                + "},"
//                + "\"scene_info\": {"
//                + "\"payer_client_ip\":\"14.23.150.211\","
//                + "\"h5_info\": {"
//                + "\"type\": \"IOS\"" + "}},"
//                + "\"mchid\": \"1900006891\","
//                + "\"description\": \"Image形象店-深圳腾大-QQ公仔\","
//                + "\"notify_url\": \"https://www.weixin.qq.com/wxpay/pay.php\","
//                + "\"out_trade_no\": \"1217752501201407033233388881\","
//                + "\"goods_tag\": \"WXG\","
//                + "\"appid\": \"wxdace645e0bc2c424\"" + "}";
//        StringEntity entity = new StringEntity(reqdata,"utf-8");
//        entity.setContentType("application/json");
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//
////完成签名并执行请求
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//        try {
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == 200) {
//                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
//            } else if (statusCode == 204) {
//                System.out.println("success");
//            } else {
//                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
//                throw new IOException("request failed");
//            }
//        } finally {
//            response.close();
//        }
//    }
//
//
//
//}
