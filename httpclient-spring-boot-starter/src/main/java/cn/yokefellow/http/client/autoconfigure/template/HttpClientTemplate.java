/*
 * Copyright 2019 Yokefellow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.yokefellow.http.client.autoconfigure.template;

import cn.yokefellow.http.client.autoconfigure.property.HttpClientProperties;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yokefellow
 * @since 2019-05-18-15:44
 * TODO https
 */
@Slf4j
@SuppressWarnings({"unused", "WeakerAccess", "Duplicates"})
public class HttpClientTemplate {

    public static final String HTTPS_PREFIX = "https";

    public static final String CONNECTION_OPERATOR = "?";

    public static final String ASSIGNMENT_OPERATOR = "=";

    public static final String PARAMETER_SEPARATOR= "&";

    public static CloseableHttpClient closeableHttpClient;

    public HttpClientTemplate(HttpClientProperties httpClientProperties) {
        HttpClientProperties.Builder builder = httpClientProperties.getBuilder();
        HttpClientProperties.RequestConfig requestConfig = httpClientProperties.getRequestConfig();
        closeableHttpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectionRequestTimeout(requestConfig.getConnectionRequestTimeout())
                                .setConnectionRequestTimeout(requestConfig.getConnectionRequestTimeout())
                                .setSocketTimeout(requestConfig.getSocketTimeout())
                                .setMaxRedirects(requestConfig.getMaxRedirects())
                                .build())
                .setMaxConnTotal(builder.getMaxConnTotal())
                .setMaxConnPerRoute(builder.getMaxConnPerRoute())
                .build();
    }

    public String getForString(String url){
        return getForString(url,null,null);
    }

    public String getForString(String url, Map<String,Object> params){
        return getForString(url,params,null);
    }

    public String getForString(String url, Map<String,Object> params,Map<String,Object> headers){
        if(url == null){
            return null;
        }
        if(params != null && params.size() > 0) {
            url = formatUrl(url, params);
        }
        HttpGet httpGet = new HttpGet(url);
        if(headers != null && headers.size() > 0){
            formatHeader(headers,httpGet);
        }
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            if(httpEntity != null){
                InputStream inputStream = httpEntity.getContent();
                byte[] buffer = new byte[1024];
                return convert(inputStream, buffer);
            }
        } catch (IOException e) {
            log.error("Execute HttpGet Error", e);
        }finally {
            try {
                if(httpResponse != null){
                    httpResponse.close();
                }
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                log.error("Close HttpResponse or HttpEntity Error", e);
            }
        }
        return null;
    }

    public <T> T getForObject(String url,Class<T> clazz){
        String result = getForString(url,null,null);
        return JSONObject.parseObject(result,clazz);
    }
    public <T> T getForObject(String url, Map<String,Object> params,Class<T> clazz){
        String result = getForString(url,params,null);
        return JSONObject.parseObject(result,clazz);
    }

    public <T> T getForObject(String url, Map<String,Object> params,Map<String,Object> headers,Class<T> clazz){
        String result = getForString(url,params,headers);
        return JSONObject.parseObject(result,clazz);
    }

    public String postFormUrlencodedForString(String url, Map<String,Object> params){
        return postFormUrlencodedForString(url,params,null);
    }

    public String postFormUrlencodedForString(String url, Map<String,Object> params,Map<String,Object> headers){
        if(url == null){
            return null;
        }
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        HttpPost httpPost = new HttpPost(url);
        if(params != null && params.size() > 0){
            List<NameValuePair> paramsList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramsList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(paramsList));
            } catch (UnsupportedEncodingException e) {
                log.error("Init UrlEncodedFormEntity Error", e);
            }
        }
        if(headers != null && headers.size() > 0){
            formatHeader(headers,httpPost);
        }
        try {
            httpResponse = closeableHttpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            if(httpEntity != null){
                byte[] buffer = new byte[1024];
                InputStream inputStream = httpEntity.getContent();
                return convert(inputStream, buffer);
            }
        } catch (IOException e) {
            log.error("Execute HttpPost Form Error", e);
        }finally {
            try {
                if(httpResponse != null){
                    httpResponse.close();
                }
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                log.error("Close HttpResponse or HttpEntity Error", e);
            }
        }
        return null;
    }

    public <T> T postFormUrlencodedForObject(String url, Map<String,Object> params,Class<T> clazz){
        String result = postFormUrlencodedForString(url,params,null);
        return JSONObject.parseObject(result,clazz);
    }

    public String postJsonForString(String url,String json){
        if(url == null){
            return null;
        }
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        if(json != null){
            try {
                StringEntity stringEntity = new StringEntity(json);
                httpPost.setEntity(stringEntity);
            } catch (UnsupportedEncodingException e) {
                log.error("Init StringEntity Error", e);
            }
        }
        try {
            httpResponse = closeableHttpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            if(httpEntity != null){
                byte[] buffer = new byte[1024];
                InputStream inputStream = httpEntity.getContent();
                return convert(inputStream, buffer);
            }
        } catch (IOException e) {
            log.error("Execute HttpPost JSON Error", e);
        }finally {
            try {
                if(httpResponse != null){
                    httpResponse.close();
                }
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                log.error("Close HttpResponse or HttpEntity Error", e);
            }
        }
        return null;
    }

    public <T> T postJsonForObject(String url,String json,Class<T> clazz){
        if(url == null){
            return null;
        }
        String result = postJsonForString(url,json);
        return JSONObject.parseObject(result,clazz);
    }

    @SuppressWarnings("WeakerAccess")
    public String formatUrl(String baseUrl, Map<String,Object> params){
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        boolean isFirstParam = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (isFirstParam) {
                stringBuilder.append(CONNECTION_OPERATOR)
                        .append(entry.getKey())
                        .append(ASSIGNMENT_OPERATOR)
                        .append(entry.getValue());
                isFirstParam = false;
            } else {
                stringBuilder.append(PARAMETER_SEPARATOR)
                        .append(entry.getKey())
                        .append(ASSIGNMENT_OPERATOR)
                        .append(entry.getValue());
            }
        }
        return stringBuilder.toString();
    }

    public void formatHeader(Map<String,Object> headers, HttpRequestBase httpRequestBase){
        for (Map.Entry<String, Object> entry : headers.entrySet()){
            httpRequestBase.setHeader(entry.getKey(),String.valueOf(entry.getValue()));
        }
    }

    public String convert(InputStream inputStream, byte[] buffer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length;
        while ((length= inputStream.read(buffer))!= -1){
            byteArrayOutputStream.write(buffer,0,length);
        }
        return byteArrayOutputStream.toString();
    }
}
