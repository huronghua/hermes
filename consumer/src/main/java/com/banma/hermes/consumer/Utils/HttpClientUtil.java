package com.banma.hermes.consumer.Utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by banma on 2017/12/12.
 */
public class HttpClientUtil {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public  String sendPost(String url, String data) {
        String response = null;
        logger.info("url: " + url);
        logger.info("request: " + data);
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data,
                        ContentType.create("application/json", "UTF-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                if (httpresponse.getStatusLine().getStatusCode() != 200){
                    return "";
                }
                response = EntityUtils
                        .toString(httpresponse.getEntity());
                logger.info("response: " + response);
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
