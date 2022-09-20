import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static String token;
    public static Long token_validity;
    public static Long token_Time;

    private static final String APP_ID = "appid";

    private static final String APP_SECRET = "appSecret";

    private static final String TOKEN_URL = "http://localhost:8080/api/v2/auth/get-access-token";

    private static final String IP_BAN="http://localhost:8081/api/tsinghua/user/status";

    public static void main(String[] args) {
        //获取当前时间戳取10位
        Long time2 = System.currentTimeMillis();
        //避免在请求处理过程中token失效加5分钟
        Long time3 = (time2 + 5 * 60 * 1000) / 1000;
        for (int i = 0; i < 2; i++) {
            //token为空或者token的有效期为空或者过期都从新获取token
            if (token == null || token.isEmpty() || token_validity == null || token_validity < time3) {
                getToken();
                System.out.println("执行第："+i);
            }
        }
        //调用封禁ip
        String ip="172.0.0.1";
        String userName="张三";
        String act="封";
        Map<String, String> para = new LinkedHashMap<>();
        Map<String, String> headers = new LinkedHashMap<>();
        para.put("ip",ip);
        para.put("username", userName);
        para.put("access_token",token);
        para.put("act",act);
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        String res = GeneralHttpClientUtil.doPost(para, headers, IP_BAN);
        JSONObject jsonObject = JSON.parseObject(res);
        System.out.println( jsonObject.get("code"));
        System.out.println( jsonObject.get("message"));

    }

    //获取token
    public static void getToken() {
        System.out.println("执行获取token！");
        Map<String, String> para = new LinkedHashMap<>();
        Map<String, String> headers = new LinkedHashMap<>();
        para.put("appId", APP_ID);
        para.put("appSecret", APP_SECRET);
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        String res = GeneralHttpClientUtil.doPost(para, headers, TOKEN_URL);
        JSONObject jsonDat= JSON.parseObject(res).getJSONObject("data");
        Main.token=jsonDat.getString("access_token");
        Main.token_Time=jsonDat.getLong("lifetime");
        Main.token_validity=jsonDat.getLong("expired_at");
        System.out.println("token:"+token);
        System.out.println("lifetime:" + token_Time);
        System.out.println("expired_at:" +token_validity);
    }
}
