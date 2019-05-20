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

package cn.yokefellow.wechat.mp.constant;

import lombok.Getter;

/**
 * @author Yokefellow
 * @since 2019-05-18-20:50
 */
@Getter
public enum WeChatMpErrorType {

    /**
     * INVALID_APP_ID
     */
    INVALID_APP_ID(40013,"无效AppID错误");

    private int errorCode;

    private String errorMsg;

    WeChatMpErrorType(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static String getErrorMsg(int errCode) {
        for (WeChatMpErrorType weChatMpErrorType : WeChatMpErrorType.values()) {
            if (weChatMpErrorType.getErrorCode() == errCode) {
                return weChatMpErrorType.getErrorMsg();
            }
        }
        return null;
    }
}
