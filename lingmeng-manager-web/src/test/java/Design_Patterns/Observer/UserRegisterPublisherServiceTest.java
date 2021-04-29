package Design_Patterns.Observer;

import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.controller.Design_Patterns.Listener.example.UserRegisterPublisherService;
import com.lingmeng.user.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description spring监听器测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class UserRegisterPublisherServiceTest {

    @Resource
    private UserRegisterPublisherService userRegisterPublisherService;


    @Test
    public void test1() {
        User user = new User();
        user.setUserName("hhh");
        userRegisterPublisherService.insert(user);
    }

}
