package com.jd.shop.controller;

import com.jd.common.pojo.JdResult;
import com.jd.common.utils.FastDFSClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传类
 * @author 毛球
 */
@RestController
public class UploadController {

   @RequestMapping("upload")
   public JdResult upload(MultipartFile file){
      //读取上传文件的原始名
      String originalFilename = file.getOriginalFilename();
      //读取到文件的扩展名（格式） 只要后缀名 不要点 所以下标加1
      String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
      try{
         //创建封装的工具类
         FastDFSClient client = new FastDFSClient("classpath:conf/fdfs_client.conf");
         //执行上传(文件(以字节模式上传)，扩展名)
         String path = client.uploadFile(file.getBytes(), extName);
         //上面的path只是fastdfs里面的 组+地址 例:group1/M00/00/00/wKgyml8qEKmAB7tTAAYCfT7Ixqk211.png
         //需要拼接上ip
         String url = "http://192.168.50.154:8888/"+path;
         return new JdResult(true,"OK",url);
      } catch (Exception e) {
         e.printStackTrace();
         return new JdResult(false,"文件上传失败",null);
      }

   }
}
