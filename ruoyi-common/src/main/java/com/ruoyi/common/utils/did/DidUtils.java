package com.ruoyi.common.utils.did;


import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.baidu.json.JSONObject;
import okhttp3.*;

import okhttp3.internal.http2.Header;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;

public class DidUtils {

    private static final String CLIPS_URL = "https://api.d-id.com/clips";

    private static final String TALKS_URL = "https://api.d-id.com/talks";


    private static final String API_KEY = "YkdsMWVIVmhiakU1TURCQU1UWXpMbU52YlE6b2NfczhnZEp1SWprWlVwSXFRekEw";


    public static String genClips(String text) throws IOException, InterruptedException {
        String clips = createClips(text);
        System.out.println(clips);
        JSONObject jsonObject = new JSONObject(clips);
        String status = jsonObject.getString("status");
        String id = jsonObject.getString("id");
        System.out.println(status);
        System.out.println(id);
        return getRequest(CLIPS_URL, id);
    }

    public static String genTalks(String text) throws IOException, InterruptedException {
        String talks = createTalks(text);
        System.out.println(talks);
        JSONObject jsonObject = new JSONObject(talks);
        String status = jsonObject.getString("status");
        String id = jsonObject.getString("id");
        System.out.println(status);
        System.out.println(id);
        return getRequest(TALKS_URL, id);
    }

    public static String createTalks(String input) {
        JSONObject data = new JSONObject();
        data.put("source_url", "https://create-images-results.d-id.com/api_docs/assets/noelle.jpeg");
        data.put("config", new JSONObject()
                .put("fluent", true)
                .put("pad_audio", 0.5)
                .put("sharpen", true));
        data.put("script", new JSONObject()
                .put("type", "text")
                .put("input", input)
                .put("provider", new JSONObject()
                        .put("type", "microsoft")
                        .put("voice_id", "zh-CN-XiaochenNeural")));

        return createRequest(TALKS_URL, data.toString());
    }

    public static String createClips(String input) {

        JSONObject data = new JSONObject();
        data.put("presenter_id", "amy-jcwCkr1grs");
        data.put("driver_id", "uM00QMwJ9x");
        data.put("background", new JSONObject().put("color", "#c9daf8"));
        JSONObject script = new JSONObject()
                .put("type", "text")
                .put("input", input)
                .put("provider", new JSONObject()
                        .put("type", "microsoft")
                        .put("voice_id", "zh-CN-XiaochenNeural"));
        data.put("script", script);

        return createRequest(CLIPS_URL, data.toString());
    }

    private static String createRequest(String url, String data) {
        //ssl认证重写
        OkHttpClient okHttpClient = new OkHttpClient.Builder().hostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }
        ).build();

        // 创建一个RequestBody对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Basic " + API_KEY)
                .build();

        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            String re = response.body().string();
            System.out.println(re);
            return re;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getRequest(String url, String id) throws InterruptedException {
        //ssl认证重写
        OkHttpClient client = new OkHttpClient.Builder().hostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }
        ).build();
        Request request = new Request.Builder()
                .url(url + "/" + id)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Basic " + API_KEY)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            String re = response.body().string();
            System.out.println(re);
            JSONObject jsonObject = new JSONObject(re);
            String status = jsonObject.getString("status");

            if (status.equals("done")) {
                String resultUrl = jsonObject.getString("result_url");
                System.out.println(resultUrl);
                return resultUrl;
            } else if (status.equals("started")||status.equals("created")) {
                Thread.sleep(1000);
                System.out.println("进入started");
                return getRequest(url, id);
            } else {
                new BaseException("Did工具获取失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("进入null");
        return null;

    }

}
