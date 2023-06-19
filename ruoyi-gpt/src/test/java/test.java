import com.ruoyi.common.utils.baidu.json.JSONArray;
import com.ruoyi.common.utils.baidu.json.JSONObject;
import com.ruoyi.common.utils.did.DidUtils;
import com.ruoyi.common.utils.gpt.GptUtils;
import io.github.asleepyfish.util.OpenAiUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


public class test {



    @Test
    public void testGetCilps() throws IOException, InterruptedException {
//        String clips = DidUtils.createClips("Could not match the union against any of the items.");
//        System.out.println(clips);
//        DidUtils.genVideo("{\"id\":\"clp_dugP7QE6meX7C2zZCUZah\",\"created_at\":\"2023-06-15T16:40:35.767Z\",\"object\":\"clip\",\"status\":\"created\"}");
//        String rul = DidUtils.getClips("clp_dugP7QE6meX7C2zZCUZah");
//        System.out.println(rul);
//        String s = DidUtils.genVideo("hello world,Could not match the union against any of the items.");
//        System.out.println(s);
        String resp = GptUtils.getGptResp("可以简单讲一下心理学领域里面的学生最近发展区是什么吗？请在100字之内回答",null,null);
        System.out.println(resp);
//        String s = DidUtils.genVideo(resp);
//        System.out.println(s);
//        String clips = DidUtils.getClips("google-oauth2|101372294584883016402");
//        System.out.println(clips);

//        String clips = DidUtils.createClips("你好，有什么我可以帮助你的吗？");
//        System.out.println(clips);

        //{"id":"clp_dugP7QE6meX7C2zZCUZah","created_at":"2023-06-15T16:40:35.767Z","object":"clip","status":"created"}
    }

    @Test
    public void getClips() throws IOException, InterruptedException {
//        clp_aRAGYqKuEk9buU-NrQJ7D
//        String clips = DidUtils.getClips("clp_aRAGYqKuEk9buU-NrQJ7D");
//        System.out.println(clips);

        String data = "{\n" +
                "                    \"model\": \"gpt-3.5-turbo-0301\",\n" +
                "                    \"messages\": [{ \"role\": \"user\", \"content\": \"" + "input" + "\" }],\n" +
                "                    \"temperature\": 0.7\n" +
                "                }";
        JSONObject message = new JSONObject();
        message.put("role","user");
        message.put("content","asdfas");
        JSONArray messages = new JSONArray();
        messages.put(0,message);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model","gpt-3.5-turbo-0301");
        jsonObject.put("messages", messages);
        jsonObject.put("temperature", 0.7);

        String s = jsonObject.toString();
        System.out.println(s);

    }
    
    @Test
    public void getGpt() throws IOException {
//        String gptSteamResp = GptUtils.get("可以简单概括一下心理学吗");
//        System.out.println(gptSteamResp);

    }

    @Test
    public void testGenerateImg() throws IOException, InterruptedException {
//        String s = DidUtils.genTalks("老师您好，我已经准备好听课啦！今天我们讲点什么呢？");
//        System.out.println(s);
        String aaa = DidUtils.getRequest("https://api.d-id.com/talks", "tlk_usNHrKN8wOhHh_sWJ6ZAP");
        System.out.println(aaa);

    }
}
