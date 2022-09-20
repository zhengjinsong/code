package com.example.web_http_post.controllerl;

import com.example.web_http_post.pojo.Request;
import com.example.web_http_post.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @PostMapping("/api/tsinghua/user/status")
    public Result getStudent(@RequestParam( required = false,value = "access_token") String access_token,
                             @RequestParam(required = false,value = "username") String username,
                             @RequestParam(required = false,value = "act") String act,
                             @RequestParam(required = false,value = "ip") String ip ) {
        Result result = new Result();
        System.out.println("请求参数为：" +"token:"+ access_token+
                "  用户名："+username+"   act:"+act+"  ip:"+ip);
        if (StringUtils.isEmpty(access_token)) {
            result.setCode(400);
            result.setMessage("token不能为空");
        } else {
            result.setCode(200);
            result.setMessage("ok");
        }
        return result;
    }
}
