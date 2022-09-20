package com.example.qinghua_cehsi2.controller;

import com.example.qinghua_cehsi2.unit.IpUserMangerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    @GetMapping("/getToken")
    public String token() throws Exception {
        return IpUserMangerUtil.getToken();
    }

    @GetMapping("/banIp")
    public Map<String,Object> ipban(@RequestParam(value = "act")String act){
        Map<String, Object> stringObjectMap = IpUserMangerUtil.callIpBanApi("101.6.28.224", "gf16", act);
        return stringObjectMap;
    }

    @GetMapping("/banUser")
    public Map<String,Object> userban(@RequestParam(value = "act")String act){
        Map<String, Object> stringObjectMap = IpUserMangerUtil.callUserBanApi("gf16", act);
        return stringObjectMap;
    }

    @GetMapping("/banIpUser")
    public Map<String,Object> banIpUser(@RequestParam(value = "act")String act){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> stringObjectMap = IpUserMangerUtil.callIpBanApi("101.6.28.224", "gf16", act);
        Map<String, Object> stringObjectMapus = IpUserMangerUtil.callUserBanApi("gf16", act);
        System.out.println("封ip"+stringObjectMap);
        System.out.println("封user"+stringObjectMapus);
        result.putAll(stringObjectMap);
        result.putAll(stringObjectMapus);
        return result;
    }
}
