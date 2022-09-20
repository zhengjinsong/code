package com.example.web_demo.controllerl;

import com.example.web_demo.pojo.Dataa;
import com.example.web_demo.pojo.Student;
import com.example.web_demo.pojo.XiangYing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class TestController {

    @PostMapping("/api/v2/auth/get-access-token")
    public XiangYing getToken(@RequestParam(required = false,value = "appId") String appId, @RequestParam(required = false,value = "appSecret") String appSecret
            , HttpServletRequest request) {
        System.out.println("参数："+appId+" "+appSecret);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String nextElement = headerNames.nextElement();//获取headerNames集合中的请求头
            String header2 = request.getHeader(nextElement);//通过请求头得到请求内容
            System.err.println("请求头==========key  " + nextElement + "----------VALUE:" + header2);
        }
        XiangYing xin = new XiangYing();
        Dataa data = new Dataa();
        data.setAccess_token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTcnVuIFJFU1RGdWwgQVBJIFNlcnZpY2VzIiwiZXhwIjoxNjQzNzgwOTUwLCJzdWIiOiJUZXN0IiwiYXVkIjoiVGVzdCIsIm5iZiI6MTY0Mzc3Mzc1MCwiaWF0IjoxNjQzNzczNzUwLCJqdGkiOiIzY2YxOGFkZTZjZTJmMzYzZTEwNGU1MjBhZGFiMjRkOCIsImFwcElkIjoiNzEyMzMwMDQ0MDMwNDBzeSJ9.ZmQwMDM1YmI5ZDZjZDRjNjYzZjkzNjIxOGY1YWY0NTIyZDI2MzE2NGNmYWUwZDNlMTVhZDNkODQ3ZDI3YmI0Mg");
        Long time2 = System.currentTimeMillis();
        //避免在请求处理过程中token失效加5分钟
        Long time3 = (time2+10*60*1000) / 1000;
        data.setLifetime(time3);
        data.setExpired_at(time3);
        xin.setCode(0l);
        xin.setMessage("ok");
        xin.setVersion("v2.0.0");
        xin.setData(data);
        return xin;
    }
}
