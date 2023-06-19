import io.github.asleepyfish.util.OpenAiUtils;
import org.junit.jupiter.api.Test;

public class baidu {

    @Test
    public void testTts() {
        OpenAiUtils.createStreamChatCompletion("你好");
    }
}
