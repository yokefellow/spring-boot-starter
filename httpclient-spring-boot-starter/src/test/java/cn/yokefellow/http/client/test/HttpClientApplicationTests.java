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

package cn.yokefellow.http.client.test;

import cn.yokefellow.http.client.autoconfigure.template.HttpClientTemplate;
import cn.yokefellow.http.client.demo.HttpClientApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yokefellow
 * @since 2019-05-18-19:51
 */
@Profile("author")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpClientApplication.class)
public class HttpClientApplicationTests {

    @Autowired
    private HttpClientTemplate httpClientTemplate;

    @Test
    public void requestTest(){
        long startTime = System.currentTimeMillis();
        String url = "https://api.github.com/users";
        String forString = httpClientTemplate.getForString(url);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-startTime)+"ms");
    }
}
