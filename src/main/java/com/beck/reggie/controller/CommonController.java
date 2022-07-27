package com.beck.reggie.controller;

import com.beck.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String BasePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file)  {
        log.info(file.toString());
        //file是一个临时文件,需要转存到指定的位置,否则本次完成临时文件会删除
        //文件后缀
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        使用UUID生成文件名
        String s = UUID.randomUUID().toString();

        //创建一个目录对象
        File f = new File(BasePath);
        if(!f.exists()){
            //目录不存在
            f.mkdirs();
        }
        try {
//            将file转存到指定的位置
            file.transferTo(new File(BasePath+s+substring));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(s + substring);
    }

    /**
     *文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
//        输入流读取文件内容
        try {
            FileInputStream inputStream = new FileInputStream(new File(BasePath + name));
            //        输出流,输出浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }
//            关闭资源
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
