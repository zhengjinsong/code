package com.example.ceshi_qinghua.controller;

import com.example.ceshi_qinghua.unit.IpUserMangerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    @GetMapping("/getToken")
    public String token(){
        return IpUserMangerUtil.getToken();
    }

    @GetMapping("/banIp")
    public Map<String,Object> ipban(){
        Map<String, Object> stringObjectMap = IpUserMangerUtil.callIpBanApi("101.6.28.224", "gf16", "封");
        return stringObjectMap;
    }

    @GetMapping("/banUser")
    public Map<String,Object> userban(){
        Map<String, Object> stringObjectMap = IpUserMangerUtil.callUserBanApi("gf16", "封");
        return stringObjectMap;
    }

    @GetMapping("/banIpUser")
    public Map<String,Object> banIpUser(){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> stringObjectMap = IpUserMangerUtil.callIpBanApi("101.6.28.224", "gf16", "封");
        Map<String, Object> stringObjectMapus = IpUserMangerUtil.callUserBanApi("gf16", "封");
        result.putAll(stringObjectMap);
        result.putAll(stringObjectMapus);
        return result;
    }
}
