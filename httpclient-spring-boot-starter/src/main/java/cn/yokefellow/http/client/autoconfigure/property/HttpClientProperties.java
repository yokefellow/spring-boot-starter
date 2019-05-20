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

package cn.yokefellow.http.client.autoconfigure.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yokefellow
 * @since 2019-05-18-14:12
 */
@Getter
@Setter
@SuppressWarnings("WeakerAccess")
@ConfigurationProperties(prefix = "httpclient")
public class HttpClientProperties {

    private Builder builder = new Builder();

    private RequestConfig requestConfig = new RequestConfig();

    @Getter
    @Setter
    public static class Builder{

        private int maxConnTotal = 10;

        private int maxConnPerRoute = 10;
    }

    @Getter
    @Setter
    public static class RequestConfig{

        private int connectionRequestTimeout = 1000;

        private int connectTimeout = 5000;

        private int socketTimeout = 5000;

        private int maxRedirects = 10;
    }
}
