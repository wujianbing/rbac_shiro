package com.jack.rbac_shiro.controller;

import com.jack.rbac_shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    @RequestMapping("/video/order_list")
    public JsonData findMyVideo(){
        Map<String,Object> map = new HashMap<>();
        map.put("shiro实战1","88");
        map.put("shiro实战2","99");
        map.put("shiro实战3","100");
        map.put("shiro实战4","100");
        return JsonData.buildSuccess(map);
    }
}
