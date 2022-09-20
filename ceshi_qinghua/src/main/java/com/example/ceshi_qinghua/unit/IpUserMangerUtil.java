package com.example.ceshi_qinghua.unit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.ceshi_qinghua.unit.IpOperationConstant.*;

public class IpUserMangerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUserMangerUtil.class);
    public static String token;
    public static Long token_validity;
    public static Long token_Time;


    private static JSONObject directory;

//    private final static String app_ip=;
//
//    private final static String app_secret=;

    private final static String token_url="http://localhost:8080/get";

    private final static String ip_ban_url="http://localhost:8081/api/tsinghua/user/status";


    //封禁解封ip的方法
    public static Map<String, Object> callIpBanApi(String ip, String user, String act) {
        Map<String, String> requestPara = new LinkedHashMap<>();
        requestPara.put(IP, ip);
        requestPara.put(ACT, act);
        requestPara.put(USER_NAME, user);
        Map<String, Object> result = null;
        try {
            result = callUserOrUserMeth(requestPara);
            if (((Long) result.get("code")).equals(200L)) {
                if ("封".equals(act)) {
                    result.put(IP_BAN, TRUE);
                    result.put(IP_BAN_TEXT, result.get("code"));
                } else if ("解封".equals(act)) {
                    result.put(IP_UNSEAL, TRUE);
                    result.put(IP_UNSEAL_TEXT, result.get("code"));
                }
            }
        } catch (Exception e) {
            if ("封".equals(act)) {
                result.put(IP_BAN, FALSE);
                result.put(IP_BAN_TEXT, e);
            } else if ("解封".equals(act)) {
                result.put(IP_UNSEAL, FALSE);
                result.put(IP_UNSEAL_TEXT, e);
            }
            LOGGER.error("调用三方封禁/解封ip错误：{}", e);
        }
        return result;
    }

    //封禁解封账号的方法
    public static Map<String, Object> callUserBanApi(String user, String act) {
        Map<String, String> requestPara = new LinkedHashMap<>();
        requestPara.put(ACT, act);
        requestPara.put(USER_NAME, user);
        Map<String, Object> result = null;
        try {
            result = callUserOrUserMeth(requestPara);
            if (((Long) result.get("code")).equals(200L)) {
                if ("封".equals(act)) {
                    result.put(USER_NAME_BAN, TRUE);
                    result.put(USER_NAME_BAN_TEXT, result.get("code"));
                } else if ("解封".equals(act)) {
                    result.put(USER_NAME_UNSEAL, TRUE);
                    result.put(USER_NAME_UNSEAL_TEXT, result.get("code"));
                }
            }
        } catch (Exception e) {
            if ("封".equals(act)) {
                result.put(USER_NAME_BAN, FALSE);
                result.put(USER_NAME_BAN_TEXT, e);
            } else if ("解封".equals(act)) {
                result.put(USER_NAME_UNSEAL, FALSE);
                result.put(USER_NAME_UNSEAL_TEXT, e);
            }
            LOGGER.error("调用三方封禁/解封账户错误：{}", e);
        }
        return result;
    }

    public static Map<String, Object> callUserOrUserMeth(Map<String, String> requestPara) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        requestPara.put("access_token", getToken());
        String res = GeneralHttpClientUtil.doPost(requestPara, requestHeaders, ip_ban_url);
        JSONObject jsonObject = JSON.parseObject(res);
        Long code = jsonObject.getLong("code");
        String message = jsonObject.getString("message");
        result.put("code", code);
        result.put("message", message);
        return result;
    }

    public static String getToken() {
        LOGGER.debug("执行获取token！");
        //获取当前时间戳取10位
        Long curnTime = System.currentTimeMillis();
        //避免在请求处理过程中token失效加5分钟
        Long youXiaoTime = (curnTime + 5 * 60 * 1000) / 1000;
        //token为空或者token的有效期为空或者过期都从新获取token
        if (token == null || token.isEmpty() || token_validity == null || token_validity < youXiaoTime) {
            //构建请求参数
            Map<String, String> para = new LinkedHashMap<>();
            //构建请求头
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//            para.put("appId", app_id);
//            para.put("appSecret", app_secret);
            String res = null;
            try {
                res = GeneralHttpClientUtil.doPost(para, headers, token_url);
            } catch (IOException e) {
                LOGGER.info("获取token失败：{}", e);
            }
            JSONObject jsonDat = JSON.parseObject(res).getJSONObject("data");
            IpUserMangerUtil.token = jsonDat.getString("access_token");
            IpUserMangerUtil.token_Time = jsonDat.getLong("lifetime");
            IpUserMangerUtil.token_validity = jsonDat.getLong("expired_at");
        }
        return IpUserMangerUtil.token;
    }
}
