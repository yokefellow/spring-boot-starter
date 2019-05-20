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

package cn.yokefellow.wechat.mp.manager;

import cn.yokefellow.http.client.autoconfigure.template.HttpClientTemplate;
import cn.yokefellow.wechat.mp.autoconfigure.property.WeChatMpProperties;
import cn.yokefellow.wechat.mp.model.WeChatMpAccessToken;
import cn.yokefellow.wechat.mp.model.WeChatMpFollower;
import cn.yokefellow.wechat.mp.util.ConvertUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author Yokefellow
 * @since 2019-05-19-15:13
 * @see <a href="https://mp.weixin.qq.com/wiki>MP Wiki</a>
 */
@Slf4j
@SuppressWarnings("WeakerAccess")
public class WeChatMpBasicSupport {

    public static final int MAX_OPENID_NUM = 10000;

    /**
     * GET_ACCESS_TOKEN_API
     */
    public static final String GET_ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    /**
     * 获取关注者列表
     */
    public static final String GET_FOLLOWER_API = "https://api.weixin.qq.com/cgi-bin/user/get?access_token={0}&next_openid={1}";

    protected final String token;

    protected final String appId;

    protected final String appSecret;

    public final HttpClientTemplate httpClientTemplate;

    public WeChatMpBasicSupport(WeChatMpProperties weChatMpProperties, HttpClientTemplate httpClientTemplate){
        this.token = weChatMpProperties.getServer().getToken();
        this.appId = weChatMpProperties.getAppId();
        this.appSecret = weChatMpProperties.getAppSecret();
        this.httpClientTemplate  = httpClientTemplate;
    }

    /**
     * @param timestamp 时间戳
     * @param nonce 随机数字串
     * @param signature 消息体签名
     * @return 校验成功 true,校验失败 false
     */
    public boolean isValidated(String timestamp, String nonce, String signature){
        String [] dict = new String[]{token,timestamp,nonce};
        Arrays.sort(dict);
        StringBuilder sb = new StringBuilder();
        for (String s : dict) {
            sb.append(s);
        }
        String sha1Str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(sb.toString().getBytes());
            sha1Str = ConvertUtils.bytesToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException", e);
        }
        return sha1Str != null && sha1Str.equals(signature.toUpperCase());
    }

    public WeChatMpAccessToken getAccessToken(){
        String url = MessageFormat.format(GET_ACCESS_TOKEN_API,appId,appSecret);
        JSONObject jsonObject = JSONObject.parseObject(httpClientTemplate.getForString(url));
        WeChatMpAccessToken weChatMpAccessToken = null;
        if(jsonObject != null){
            weChatMpAccessToken = jsonObject.toJavaObject(WeChatMpAccessToken.class);
            if (weChatMpAccessToken.getAccessToken() == null){
                log.error("申请微信凭证失败,errCode:{},errMsg:{}",jsonObject.getInteger("errcode"),jsonObject.getString("errmsg"));
                return null;
            }
        }
        return weChatMpAccessToken;
    }

    public WeChatMpFollower getFollowers(){
        WeChatMpAccessToken accessToken = getAccessToken();
        if(accessToken == null){
            return null;
        }
        String token = accessToken.getAccessToken();
        WeChatMpFollower weChatFollower = getFollowers(token,null);
        if(weChatFollower == null){
            return null;
        }
        if(weChatFollower.getCount() < MAX_OPENID_NUM){
            return weChatFollower;
        }else{
            String nextOpenid = weChatFollower.getNextOpenid();
            while (true){
                WeChatMpFollower temp = getFollowers(token, nextOpenid);
                if(temp != null){
                    if(temp.getCount() < MAX_OPENID_NUM){
                        weChatFollower.getData().getOpenid().addAll(temp.getData().getOpenid());
                        weChatFollower.setCount(weChatFollower.getCount() + temp.getCount());
                        weChatFollower.setNextOpenid(null);
                        break;
                    }else{
                        weChatFollower.getData().getOpenid().addAll(temp.getData().getOpenid());
                        weChatFollower.setCount(weChatFollower.getCount() + temp.getCount());
                        nextOpenid = temp.getNextOpenid();
                    }
                }
            }
        }
        return weChatFollower;
    }
    private WeChatMpFollower getFollowers(String accessToken,String nextOpenid){
        if(nextOpenid == null){
            nextOpenid = "";
        }
        String url = MessageFormat.format(GET_FOLLOWER_API, accessToken, nextOpenid);
        JSONObject jsonObject = JSONObject.parseObject(httpClientTemplate.getForString(url));
        WeChatMpFollower weChatFollower = null;
        if(jsonObject != null){
            weChatFollower = jsonObject.toJavaObject(WeChatMpFollower.class);
            if (weChatFollower != null && weChatFollower.getData() == null){
                log.error("获取到的关注者失败,errCode:{},errMsg:{}",jsonObject.getInteger("errcode"),jsonObject.getString("errmsg"));
                return null;
            }
        }
        return weChatFollower;
    }
}
