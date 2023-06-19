package com.ruoyi.common.utils.baidu;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;


import java.util.Base64;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class IatUtils {
    private static final String API_KEY = "HOTNM8yInQYICDTcwmPyUeWu";
    private static final String SECRET_KEY = "vs2gXbgbOShQ60qfASbHfzA5gkNj9ysq";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String iat(MultipartFile file) throws IOException {
        String fileContentAsBase64 = getFileContentAsBase64(file, false);
        MediaType mediaType = MediaType.parse("application/json");
        // speech 可以通过 getFileContentAsBase64("C:\fakepath\16k_10.pcm") 方法获取,如果Content-Type是application/x-www-form-urlencoded时,第二个参数传true

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("format", "pcm");
        jsonObject.put("rate", 16000);
        jsonObject.put("channel", 1);
        jsonObject.put("cuid", "Kv7DLJH9zl2Hi5iaPQ025LRx13l0kxdo");
        jsonObject.put("token", getAccessToken());
        jsonObject.put("speech", fileContentAsBase64);
        jsonObject.put("len", file.getSize());


        String content = jsonObject.toString();

        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://vop.baidu.com/server_api")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
//        System.out.println(response.body().string());
        String r = response.body().string();

        return r;
    }


    /**
     * 获取文件base64编码
     *
     * @param urlEncode 如果Content-Type是application/x-www-form-urlencoded时,传true
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */

    static String getFileContentAsBase64(MultipartFile file, boolean urlEncode) throws IOException {
        byte[] b = file.getBytes();
//        byte[] b = Files.readAllBytes(Paths.get(path));
        String base64 = Base64.getEncoder().encodeToString(b);
        if (urlEncode) {
            base64 = URLEncoder.encode(base64, "utf-8");
        }
        return base64;
    }


    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }

}