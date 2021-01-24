import com.lingmeng.LingmengManagerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author skin
 * @createTime 2021年01月18日
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class ThreadLocal {

    private static final java.lang.ThreadLocal<String> currentUser = new java.lang.ThreadLocal<>();
    @Test
    public void ThreadLocalTest()  {

    }
}
