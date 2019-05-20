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

package cn.yokefellow.wechat.mp.autoconfigure.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author Yokefellow
 * @since 2019-05-18-20:59
 */

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "wechat.mp")
public class WeChatMpProperties {

    @NotEmpty(message = "公众号开发者ID(AppID)不能为空")
    private String appId;

    @NotEmpty(message = "公众号开发者密码(AppSecret)不能为空")
    private String appSecret;

    @Valid
    private final Server server = new Server();

    @Getter
    @Setter
    @SuppressWarnings("WeakerAccess")
    public static class Server  {

        @NotEmpty(message = "服务器配置令牌(Token)不能为空")
        private String token;
    }
}
