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

package cn.yokefellow.wechat.mp.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yokefellow
 * @since 2019-04-28-10:03
 * 微信公众平台服务器验证校验工具
 */
@Slf4j
public class ConvertUtils {


    /**
     * @param bytes 字节数组
     * @return 转换为十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(byteToHexString(aByte));
        }
        return sb.toString();
    }
    /**
     *
     * @param aByte 字节
     * @return 转换为十六进制字符串
     */
    public static String byteToHexString(byte aByte){
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] temp = new char[2];
        temp[0] = digit[(aByte >>> 4) & 0X0F];
        temp[1] = digit[aByte & 0X0F];
        return new String(temp);
    }
}

