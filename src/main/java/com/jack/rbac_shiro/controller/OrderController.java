package com.jack.rbac_shiro.controller;

import com.jack.rbac_shiro.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("authc")
public class OrderController {

    @RequestMapping("/video/play_record")
    public JsonData findMyVideo(){
        Map<String,Object> map = new HashMap<>();
        map.put("shiro实战1","第一季");
        map.put("shiro实战2","第二季");
        map.put("shiro实战3","第三季");
        map.put("shiro实战4","第四季");
        return JsonData.buildSuccess(map);
    }
}
