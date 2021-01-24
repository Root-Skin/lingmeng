import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.api.auth.AuthService;
import com.lingmeng.dao.user.UserMapper;
import com.lingmeng.user.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Date;

/**
 * @author skin
 * @createTime 2021年01月17日
 * @Description jmeter 压力测试秒杀
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private AuthService authService;

    @Test
    public void addUser() {
        User user = new User();
        for (int i = 1; i < 5000; i++) {
            user.setId(null);
            user.setCreatedTime(new Date());
            user.setPhone("1883482" + String.format("%04d", i));
            user.setUserName("username" + i);
            user.setPassword("abcdefg" + i);
            this.userMapper.insert(user);
        }
    }

    @Test
    public void getTokenCSV() {
        try {
            File csv = new File("E://Token.csv");//CSV文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            for (int i = 1; i < 5000; i++) {
                //新增一行数据
                String token = this.authService.authenticate("username" + i, "abcdefg" + i);
                bw.write("username" + i + "," + token);
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            //捕获File对象生成时的异常
            e.printStackTrace();
        } catch (IOException e) {
            //捕获BufferedWriter对象关闭时的异常
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @author skin
     * @Date 2021/1/17 18:46
     * @description 秒杀
     **/
    @Test
    public void miaosha() {
        User user = new User();
        for (int i = 1; i < 5000; i++) {
            user.setId(null);
            user.setCreatedTime(new Date());
            user.setPhone("1883482" + String.format("%04d", i));
            user.setUserName("username" + i);
            user.setPassword("abcdefg" + i);
            this.userMapper.insert(user);
        }
    }
}
