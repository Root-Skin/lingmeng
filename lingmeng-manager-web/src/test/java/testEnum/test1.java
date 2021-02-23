package testEnum;

import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.dao.test.TestEnumMapper;
import com.lingmeng.testEnum.testEnum;
import com.lingmeng.testEnum.ttEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class test1 {

    @Autowired
    private TestEnumMapper testEnumMapper;
    @Test
    public void Ttsc() {
        testEnum test = new testEnum();
        test.setTest2(ttEnum.ONLINE);
        testEnumMapper.insert(test);
    }

    @Test
    public void Ttsc2() {

        testEnum testEnum = testEnumMapper.selectById("1357285455075520514");
        System.out.println();
    }

}
