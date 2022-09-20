import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Testa {

    @Test
    public void httpClient() throws IOException {
//        String url = "http://localhost:8080/get";
//        Map<String, String> para = new LinkedHashMap<>();
//        Map<String, String> headers = new LinkedHashMap<>();
//        para.put("name", "song");
//        para.put("name2", "song");
//        headers.put("headTest", "test");
//        headers.put("Content-Type", "application/json");
//        String res = HttpClientUtil.doGet(para, headers, url);
//        //res=null;
//        System.out.println(res);
//        JSONObject jsonDat= JSON.parseObject(res).getJSONObject("data");
//        String token=jsonDat.getString("access_token");
//        Long lifetime=jsonDat.getLong("lifetime");
//        Long expired_at=jsonDat.getLong("expired_at");
//        System.out.println("token:"+token);
//        System.out.println("lifetime:"+lifetime);
//        System.out.println("expired_at:"+expired_at);


        String url = "http://localhost:8081/api/tsinghua/user/status";
        Map<String, String> para = new LinkedHashMap<>();
        Map<String, String> headers = new LinkedHashMap<>();
        para.put("access_token", "张三");
        para.put("username", "123");
        para.put("act", "123");
        para.put("ip", "12121");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        String res = GeneralHttpClientUtil.doPost(para, headers, url);
        JSONObject jsonObject = JSON.parseObject(res);
        System.out.println( jsonObject.get("code"));
        System.out.println( jsonObject.get("message"));
    }
}
