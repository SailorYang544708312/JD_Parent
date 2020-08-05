package com.jd.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于商家登录时的校验
 * @author 毛球
 */
@RestController
@RequestMapping("login")
public class LoginController {

   @RequestMapping("name")
   public Map<String,String> name(){
      //从springSecurity中取出当前登录的用户名
      String name = SecurityContextHolder.getContext().getAuthentication().getName();
      Map<String,String> map = new HashMap<String,String>();
      map.put("loginName",name);
      return map;
   }
}
