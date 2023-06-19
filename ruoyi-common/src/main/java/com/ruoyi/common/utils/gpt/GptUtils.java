package com.ruoyi.common.utils.gpt;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.baidu.json.JSONArray;
import com.ruoyi.common.utils.baidu.json.JSONObject;
import okhttp3.*;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.boot.json.JsonParser;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GptUtils {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPEN_API_KEY = "Bearer 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";

    private static String getKaoYanPrompt(String input, String gptcontext, String context) {
        String prompt = "聊天背景：请你扮演一位考研面试官，你是教育学专家，你正在我进行面试，你可以向我提问，问题涉猎教育学，并对我做一个评价\n" +
                "\n" +
                "下面是我们的对话历史，Q是你的问题，A是我的回答\n" +
                "Q: " + gptcontext + " \n" +
                "A: " + context + " \n" +
                "\n" +
                "下面是我本次的回答内容\n" +
                "A: " + input + " \n" +
                "\n" +
                "请你对我的回答进行评价，并做下一步的提问,详细了解我\n" +
                "你回答的要求如下：\n" +
                "1.不能超过50个字\n" +
                "2.用口语化语言回复" +
                "3.不用回复A或者Q";
        return prompt;
    }

    private static String getPeiXunPrompt(String input, String gptcontext, String context) {
        String prompt = "我是一名老师，请你扮演一个学生，我们模仿一次职前教师培训，你可以向我提出问题，并配合我的的回答\n" +
                "\n" +
                "下面是我们的谈话历史，Q是你，A是我\n" +
                "Q：" + gptcontext + " \n" +
                "A：" + context + " \n" +
                "\n" +
                "下面是我本次的回答：" + input + "\n" +
                "\n" +
                "你的任务：\n" +
                "1.请你继续扮演角色，对我的回答做出反应，并对我所教的内容提问疑问\n" +
                "2.回答要自然，要像小学生的回答，在回答前面加上老师\n" +
                "3.回复不用带\"Q\"或者\"A\"，或者\"我\"，直接回答就可以了";
        return prompt;
    }

    private static String getYiYuePrompt(String input, String gptcontext, String context) {
                String prompt = "我们谈论的背景：请你扮演成一个抑郁症的小孩，我是特殊教育的老师，我们来模拟一段对话，你可以向我提出一些问题和求助，我来开导你，你可以直接饰演角色，不能回复“好的”。你不需要回复我是否明白。\n" +
                "\n" +
                "下面是我们之前谈论过的问题，Q是你，A是我\n" +
                "\n" +
                "Q：" + gptcontext + " \n" +
                "A：" + context + " \n" +
                "\n" +
                "现在我的回答是：\n" +
                "\" " + input + " \"\n" +
                "请你继续我们的聊天，并继续扮演角色向我提出问题。你可以直接回答我的问题，不用任何格式";
        return prompt;
    }

    public static String getGptResp(String input, String gptcontext, String context) {

//        String prompt = getKaoYanPrompt(input, gptcontext, context);
        String prompt = getPeiXunPrompt(input, gptcontext, context);

        //ssl认证重写
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String s, SSLSession sslSession) {
                                return true;
                            }
                        }
                ).build();

//        String data = "{\n" +
//                "                    \"model\": \"gpt-3.5-turbo-0301\",\n" +
//                "                    \"messages\": [{ \"role\": \"user\", \"content\": \"" + input + "\" }],\n" +
//                "                    \"temperature\": 0.7\n" +
//                "                }";

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        JSONArray messages = new JSONArray();
        messages.put(0, message);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo-0301");
        jsonObject.put("messages", messages);
        jsonObject.put("temperature", 0.7);

        String s = jsonObject.toString();
        // 创建一个RequestBody对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), s);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
//                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", OPEN_API_KEY)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            String re = response.body().string();
            System.out.println(re);
            JSONObject respJson = new JSONObject(re);
            JSONObject choices = (JSONObject) respJson.getJSONArray("choices").get(0);
            String content = choices.getJSONObject("message").getString("content");

            System.out.println(content);

            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getGptSteamResp(String input) {

        //ssl认证重写
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String s, SSLSession sslSession) {
                                return true;
                            }
                        }
                ).build();

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", input);
        JSONArray messages = new JSONArray();
        messages.put(0, message);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo-0301");
        jsonObject.put("messages", messages);
        jsonObject.put("temperature", 0.7);
        jsonObject.put("stream", true);

        String s = jsonObject.toString();
        // 创建一个RequestBody对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), s);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
//                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", OPEN_API_KEY)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            String re = response.body().string();
            System.out.println(re);
            JSONObject respJson = new JSONObject(re);
            JSONObject choices = (JSONObject) respJson.getJSONArray("choices").get(0);
            String content = choices.getJSONObject("delta").getString("content");
            String finishReason = choices.getJSONObject("finish_reason").toString();
            System.out.println(content);
            System.out.println(finishReason);

            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String get(String input) throws IOException {
        String url = API_URL;

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", input);
        JSONArray messages = new JSONArray();
        messages.put(0, message);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo-0301");
        jsonObject.put("messages", messages);
        jsonObject.put("temperature", 0.7);
        jsonObject.put("stream", true);

        String s = jsonObject.toString();

        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", OPEN_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("stream", "true");
        conn.setDoOutput(true);
        //    写入请求参数
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Charset.forName("UTF-8")));
        writer.write(s);
        writer.close();
        os.close();

        //

        InputStream inputStream = conn.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
//        System.out.println("开始回答");
        StringBuffer result = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("line=>" + line);
            line = line.replace("data:", "");
            JSONObject data = new JSONObject(line);
            System.out.println("data=>" + data);
            if (data != null) {

            }
            JSONArray choices = data.getJSONArray("choices");
//            JSONObject choices = (JSONObject) choices.get(0);
            if (choices.length() > 0) {
                JSONObject choice = (JSONObject) choices.get(0);

                JSONObject delta = choice.getJSONObject("delta");
                Object finishReason = choice.get("finish_reason");
                if (delta != null) {
                    continue;
                }
                if (finishReason != null) {

                    String content = delta.getString("content");
                    if (content != null) {
                        System.out.print("content:" + content);
                        result.append(content);
                    }
                }

            }
        }
        return result.toString();
    }


//        String s1 = stringRedisTemplate.opsForValue().get("web:" + userid);
//        List<ChatModel> json = (List<ChatModel>) gson.fromJson(s1, new TypeToken<List<ChatModel>>() {
//        }.getType());
//        ChatModel chatModel = new ChatModel("assistant",answoer.toString());
//        json.add(chatModel);
//        stringRedisTemplate.opsForValue().set("web:" + userid,gson.toJson(json),1, TimeUnit.DAYS);

}




