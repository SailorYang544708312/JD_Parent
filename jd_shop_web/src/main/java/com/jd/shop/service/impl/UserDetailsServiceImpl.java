package com.jd.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jd.pojo.TbSeller;
import com.jd.sellergoods.service.SellerService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实现UserDetailsService接口，用来做连接数据库的验证
 * @author 毛球
 */
public class UserDetailsServiceImpl implements UserDetailsService {

   private SellerService sellerService;

   public SellerService getSellerService() {
      return sellerService;
   }

   public void setSellerService(SellerService sellerService) {
      this.sellerService = sellerService;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      //角色的集合
      List<GrantedAuthority> authorities = new ArrayList<>();
      //authorities.add(new SimpleGrantedAuthority("ROLE_USER")); 可以有多个角色
      authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

      //通过sellerId获取商家对象
      TbSeller seller = sellerService.findOne(username);
      if (seller != null){
         //用户名存在的
         if (seller.getStatus().equals("1")){
            //表示审核通过
            //参数一：要验证的用户名， 参数二：正确的密码（数据库里面的密码）参数三:授予角色
            //如果认证成功，那么就授予该角色
            return new User(username,seller.getPassword(),authorities);
         }else {
            //表示没有审核通过
            throw new BadCredentialsException("您的账号还在审核中");
         }
      }else {
         //表示用户名不存在
         throw new BadCredentialsException("用户名不存在");
      }


   }
}
