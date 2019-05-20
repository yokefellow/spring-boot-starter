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

package cn.yokefellow.http.client.autoconfigure;

import cn.yokefellow.http.client.autoconfigure.property.HttpClientProperties;
import cn.yokefellow.http.client.autoconfigure.template.HttpClientTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Yokefellow
 * @since 2019-05-18-14:22
 * TODO SSLContext,X509TrustManager,AsyncHttpClientTemplate
 */

@Slf4j
@Configuration
@ConditionalOnClass(value = {HttpClient.class})
@EnableConfigurationProperties(value = HttpClientProperties.class)
public class HttpClientAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(name = "httpClientTemplate")
    public HttpClientTemplate httpClientTemplate(HttpClientProperties httpClientProperties){
        log.info("Init HttpClientTemplate");
        return new HttpClientTemplate(httpClientProperties);
    }

    @SuppressWarnings("unused")
    private SSLContext sslContext(){
        return null;
    }

    @SuppressWarnings("unused")
    private X509TrustManager x509TrustManager(){
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

}
