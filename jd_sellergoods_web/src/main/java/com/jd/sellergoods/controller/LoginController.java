package com.jd.sellergoods.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 毛球
 * 用于支持spring security的用户登陆
 */
@RestController
@RequestMapping("login")
public class LoginController {

   @RequestMapping("name")
   public Map<String,Object> name(){
      //从Spring-security中取出当前登陆的用户名
      Map<String,Object> map = new HashMap<>();
      String uName = SecurityContextHolder.getContext().getAuthentication().getName();
      map.put("loginName",uName);
      return map;
   }
}
