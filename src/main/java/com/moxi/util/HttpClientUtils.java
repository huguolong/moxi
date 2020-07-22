package com.moxi.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	
	private static final CloseableHttpClient httpclient;  
    public static final String CHARSET = "UTF-8";  
    
    static{  
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();  
        httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();  
    }
    
    /** 
     * HTTP Get 获取内容
     * @return 页面内容 
     */  
    public static String sendHttpGet(String url, Map<String, Object> params) throws ParseException, UnsupportedEncodingException, IOException{  
          
        if(params !=null && !params.isEmpty()){  
              
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());  
              
            for (String key :params.keySet()){  
                pairs.add(new BasicNameValuePair(key, params.get(key).toString()));  
            }  
            url +="?"+EntityUtils.toString(new UrlEncodedFormEntity(pairs), CHARSET);  
        }  
        
//        System.out.println("请求URL:" + url);
        
        HttpGet httpGet = new HttpGet(url);  
        CloseableHttpResponse response = httpclient.execute(httpGet);  
        int statusCode = response.getStatusLine().getStatusCode();  
        if(statusCode !=200){  
            httpGet.abort();  
            throw new RuntimeException("HttpClient,error status code :" + statusCode);  
        }  
        HttpEntity entity = response.getEntity();  
        String result = null;  
        if (entity != null) {  
            result = EntityUtils.toString(entity, "utf-8");  
            EntityUtils.consume(entity);  
            response.close();  
            return result;  
        }else{  
            return null;  
        }  
    }  
    
    /** 
     * HTTPS Get 获取内容 
     * @param url 请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return 页面内容 
     */ 
    public static String sendHttpsGet(final String url, final Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("");

        if (null != params && !params.isEmpty()) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(params.get(key));
                i++;
            }
        }

        CloseableHttpClient httpClient = createSSLClientDefault();

        CloseableHttpResponse response = null;
        
        
//        System.out.println("请求URL:" + url + sb.toString());
        
        HttpGet get = new HttpGet(url + sb.toString());
        String result = "";

        try {
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpClientUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    Logger.getLogger(HttpClientUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }
    
    @SuppressWarnings("deprecation")
	private static CloseableHttpClient createSSLClientDefault() {

        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] xcs, String string){
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyStoreException ex) {
            Logger.getLogger(HttpClientUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HttpClientUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(HttpClientUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return HttpClients.createDefault();
    }
    
}