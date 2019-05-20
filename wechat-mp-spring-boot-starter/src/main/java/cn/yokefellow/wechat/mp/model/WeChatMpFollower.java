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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yokefellow
 * @since 2019-05-19-16:45
 */
@Getter
@Setter
@ToString
public class WeChatMpFollower implements Serializable {

    private static final long serialVersionUID = -8541047956972218169L;

    /**
     * 关注该公众账号的总用户数
     */
    private Integer total;

    /**
     * 拉取的OPENID个数，最大值为10000
     */
    private Integer count;

    /**
     * 列表数据，OPENID的列表
     */
    private OpenidData data;

    /**
     * 拉取列表的最后一个用户的OPENID
     */
    private String nextOpenid;


    @Getter
    @Setter
    @ToString
    @SuppressWarnings("WeakerAccess")
    public static class OpenidData {

        /**
         * 关注者的OPENID
         */
        private List<String> openid = new ArrayList<>();

    }
}
