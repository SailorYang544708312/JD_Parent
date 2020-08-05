package com.js.shop.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于登录校验
 * AuthenticationFailureHandler 要实现的接口
 * @author 毛球
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

   @Override
   public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
      Map<String,Object> map = new HashMap<>(2);
      map.put("success",false);
      //Bad credentials密码错误
      if ("Bad credentials".equals(e.getMessage())){
         map.put("message","密码错误");
      }else {
         map.put("message",e.getMessage());
      }
      /*String result = JSON.json(map);
      httpServletResponse.setContentType("text/json;charset=utf-8");
      httpServletResponse.getWriter().write(result);*/
   }
}
