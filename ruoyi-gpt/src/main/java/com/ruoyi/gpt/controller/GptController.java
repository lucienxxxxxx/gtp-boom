package com.ruoyi.gpt.controller;

import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.gpt.GptUtils;
import io.github.asleepyfish.util.OpenAiUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import retrofit2.http.POST;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Proxy;

import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/gpt")
public class GptController extends BaseController {

    @GetMapping("/getGptResp")
    @ResponseBody
    public AjaxResult getGptResp(String input, String context, String gptcontext) {


        String gptResp = GptUtils.getGptResp(input,gptcontext,context);
        if (gptResp == null) {
            throw new BaseException("gpt生成失败");
        }
        return success(gptResp);
    }

    @PostMapping("/getGptStream")
    @ResponseBody
    public String proxy(@RequestBody String requestBody) throws Exception {
        // 创建 HttpClient 对象
        HttpClient httpClient = HttpClientBuilder.create().build();

        // 创建 HttpPost 请求对象，并设置请求 URL
        HttpPost httpPost = new HttpPost("https://chatgpt.nextweb.fun/api/openai/v1/chat/completions");

        // 设置请求头信息
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer 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");
        httpPost.setHeader("Accept", "text/event-stream");

        // 设置请求体参数
        StringEntity requestEntity = new StringEntity(requestBody, "UTF-8");
        httpPost.setEntity(requestEntity);

        // 设置代理服务器
        HttpHost proxy = new HttpHost("127.0.0.1", 8080);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        httpPost.setConfig(config);

        // 执行请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);

        // 从响应对象中获取响应实体
        HttpEntity responseEntity = response.getEntity();

        // 将响应实体转换为字符串
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        // 返回响应字符串
        return responseString;
    }

    @GetMapping("/streamChatWithWeb")
    public void streamChatWithWeb(String content, HttpServletResponse response) throws IOException {
        // 需要指定response的ContentType为流式输出，且字符编码为UTF-8
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        // 禁用缓存
        response.setHeader("Cache-Control", "no-cache");
        OpenAiUtils.createStreamChatCompletion(content, response.getOutputStream());
    }

}
