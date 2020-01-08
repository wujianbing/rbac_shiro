package com.jack.rbac_shiro.controller;

import com.jack.rbac_shiro.domain.JsonData;
import com.jack.rbac_shiro.domain.UserQuery;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pub")
public class PublicController {

    @RequestMapping("need_login")
    public JsonData needLogin(){
        return JsonData.buildSuccess(-2,"需要登录");
    }

    @RequestMapping("not_permit")
    public JsonData notPermit(){
        return JsonData.buildSuccess(-3,"没有权限访问");
    }

    @RequestMapping("index")
    public JsonData index(){
        List<String> videoList = new ArrayList<>();
        videoList.add("java开发");
        videoList.add("c++开发");
        videoList.add("c语言开发");
        videoList.add("python开发");
       return JsonData.buildSuccess(videoList);
    }

    @RequestMapping("login")
    public JsonData login(@RequestBody UserQuery userQuery, HttpServletRequest request, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> info = new HashMap<>();
            try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userQuery.getName(),userQuery.getPassword());
            subject.login(usernamePasswordToken);
            info.put("msg","登录成功");
            info.put("session_id",subject.getSession().getId());
            return JsonData.buildSuccess(info);
        }catch (Exception e){
           return JsonData.buildError("用户或密码错误");
        }
    }
}
