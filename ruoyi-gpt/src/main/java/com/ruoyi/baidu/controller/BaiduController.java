package com.ruoyi.baidu.controller;

import com.ruoyi.baidu.domain.IatRequestBody;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.baidu.BaiduUtils;
import com.ruoyi.common.utils.baidu.DemoException;
import com.ruoyi.common.utils.baidu.IatUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/baidu")
public class BaiduController extends BaseController {

    @GetMapping("/getTtsAudio")
    @ResponseBody
    public ResponseEntity<byte[]> getAudio(String text) throws IOException, DemoException {
        //调用tts工具
        if (text.isEmpty()) {
            new BaseException("text不能为空");
        }
        String audioPath = BaiduUtils.tts(text);

        // 读取音频文件的二进制数据
        byte[] audioBytes = Files.readAllBytes(Paths.get(audioPath));

        // 设置响应头中的 MIME 类型
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/wav"));

        // 返回 ResponseEntity 对象
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/iat")
    @ResponseBody
    public AjaxResult getIat(@RequestParam("file") MultipartFile file) throws IOException {
        String iat = IatUtils.iat(file);
        return success(iat);
    }



}
