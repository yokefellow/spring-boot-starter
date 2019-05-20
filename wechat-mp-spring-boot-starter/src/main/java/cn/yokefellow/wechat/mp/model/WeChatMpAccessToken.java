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

package cn.yokefellow.wechat.mp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Yokefellow
 * @since 2019-05-18-20:45
 */

@Getter
@Setter
@ToString
public class WeChatMpAccessToken implements Serializable {

    private static final long serialVersionUID = 9201177176983206143L;

    /**
     * 获取到的凭证
     */
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒
     */
    private Integer expiresIn;


}
