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

package cn.yokefellow.wechat.mp.test;

import cn.yokefellow.http.client.autoconfigure.template.HttpClientTemplate;
import cn.yokefellow.wechat.mp.autoconfigure.property.WeChatMpProperties;
import cn.yokefellow.wechat.mp.demo.WeChatMpApplication;
import cn.yokefellow.wechat.mp.manager.WeChatMpBasicSupport;
import cn.yokefellow.wechat.mp.model.WeChatMpAccessToken;
import cn.yokefellow.wechat.mp.model.WeChatMpFollower;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yokefellow
 * @since 2019-05-19-16:52
 */
@Profile("author")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeChatMpApplication.class)
public class WeChatMpApplicationTests {

    @Autowired
    private WeChatMpProperties weChatMpProperties;

    @Autowired
    private HttpClientTemplate httpClientTemplate;

    @Autowired
    private WeChatMpBasicSupport weChatMpBasicSupport;

    /**
     * 获取
     */
    @Test
    public void beanTest(){
        System.out.println(weChatMpProperties.toString());
        long startTime = System.currentTimeMillis();
        String url = "https://api.github.com/users";
        String forString = httpClientTemplate.getForString(url);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-startTime)+"ms");
        WeChatMpAccessToken accessToken = weChatMpBasicSupport.getAccessToken();
        System.out.println(accessToken);
        WeChatMpFollower followers = weChatMpBasicSupport.getFollowers();
        System.out.println(followers);
    }
}
