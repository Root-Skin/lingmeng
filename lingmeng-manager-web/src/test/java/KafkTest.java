import com.lingmeng.LingmengManagerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author skin
 * @createTime 2021年03月20日
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class KafkTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void sendTest01() {
        for(int i = 0; i < 5; i++) {
            kafkaTemplate.send("test", "test msg!");
            System.out.println("发送成功");
        }
    }
}
