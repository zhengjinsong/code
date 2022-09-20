package com.example.qinghua_cehsi2.unit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.qinghua_cehsi2.unit.IpOperationConstant.*;


public class IpUserMangerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUserMangerUtil.class);
    private static final String ITS_IP_BAN_CONFIG = "its_ip_ban_config.json";

    public static String token;
    private static JSONObject directory;

    private static String token_url="https://101.6.4.97:8001/api/v1/auth/get-access-token";

    private static String ip_ban_url="https://101.6.4.97:8001/api/tsinghua/user/status";



    //封禁解封ip的方法
    public static Map<String, Object> callIpBanApi(String ip, String user, String act) {
        LOGGER.debug("调用封禁ip的方法");
        Map<String, String> requestPara = new LinkedHashMap<>();
        requestPara.put(ACT, act);
        requestPara.put(IP, ip);
        requestPara.put(USER_NAME, user);
        Map<String, Object> result = new HashMap<>();
        try {
            result = callUserOrUserMeth(requestPara);
            Long code = (Long) result.get("code");
            if ("封".equals(act)) {
                if (code.equals(0L)) {
                    result.put(IP_BAN, CHENGGONG);
                    result.put(IP_BAN_TEXT, "封禁ip成功！");
                } else {
                    result.put(IP_BAN, SHIBAI);
                    result.put(IP_BAN_TEXT, "封禁ip失败！");
                }
            } else if ("解封".equals(act)) {
                if (code.equals(0L)) {
                    result.put(IP_UNSEAL, CHENGGONG);
                    result.put(IP_UNSEAL_TEXT, "解封ip成功！");
                } else {
                    result.put(IP_UNSEAL, SHIBAI);
                    result.put(IP_UNSEAL_TEXT, "解封ip失败！");
                }
            }
        } catch (Exception e) {
            if ("封".equals(act)) {
                result.put(IP_BAN, SHIBAI);
                result.put(IP_BAN_TEXT, "调用封禁ip接口异常！");
            } else if ("解封".equals(act)) {
                result.put(IP_UNSEAL, SHIBAI);
                result.put(IP_UNSEAL_TEXT, "调用解封ip接口异常");
            }
            LOGGER.error("ip解封/封禁接口调用异常：{}", e.toString());
        }
        return result;
    }

    //封禁解封账号的方法
    public static Map<String, Object> callUserBanApi(String user, String act) {
        LOGGER.debug("调用封禁账号的方法");
        Map<String, String> requestPara = new LinkedHashMap<>();
        requestPara.put(ACT, act);
        requestPara.put(USER_NAME, user);
        Map<String, Object> result = new HashMap<>();
        try {
            result = callUserOrUserMeth(requestPara);
            Long code = (Long) result.get("code");
            if ("封".equals(act)) {
                if (code.equals(0L)) {
                    result.put(USER_NAME_BAN, CHENGGONG);
                    result.put(USER_NAME_BAN_TEXT, "封禁账号成功！");
                } else {
                    result.put(USER_NAME_BAN, SHIBAI);
                    result.put(USER_NAME_BAN_TEXT, "封禁账号失败！");
                }
            } else if ("解封".equals(act)) {
                if (code.equals(0L)) {
                    result.put(USER_NAME_UNSEAL, CHENGGONG);
                    result.put(USER_NAME_UNSEAL_TEXT, "解封账号成功！");
                } else {
                    result.put(USER_NAME_UNSEAL, SHIBAI);
                    result.put(USER_NAME_UNSEAL_TEXT, "解封账号失败！");
                }
            }
        } catch (Exception e) {
            if ("封".equals(act)) {
                result.put(USER_NAME_BAN, SHIBAI);
                result.put(USER_NAME_BAN_TEXT, "调用封禁账号接口异常！");
            } else if ("解封".equals(act)) {
                result.put(USER_NAME_UNSEAL, SHIBAI);
                result.put(USER_NAME_UNSEAL_TEXT, "调用解封账号接口异常！");
            }
            LOGGER.error("账号解封/封禁接口调用异常：{}", e.toString());
        }
        return result;
    }

    public static Map<String, Object> callUserOrUserMeth(Map<String, String> requestPara) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        requestPara.put("access_token", getToken());
        String res = RequestClientUtil.postToWSO(ip_ban_url, requestPara, requestHeaders);
        JSONObject jsonObject = JSON.parseObject(res);
        Long code = jsonObject.getLong("code");
        String message = jsonObject.getString("message");
        result.put("code", code);
        result.put("message", message);
        return result;
    }

    public static String getToken() throws Exception {
        LOGGER.debug("执行获取token！");
        //构建请求头
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        String res = null;
        res = RequestClientUtil.get(token_url);
        LOGGER.debug("结果：{}", res);
        JSONObject jsonDat = JSON.parseObject(res).getJSONObject("data");
        IpUserMangerUtil.token = jsonDat.getString("access_token");
        return token;
    }
}
