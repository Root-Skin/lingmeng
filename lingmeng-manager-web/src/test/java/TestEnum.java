import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.cms.model.Cms;
import com.lingmeng.cms.model.MsgMethodEnum;
import com.lingmeng.dao.cms.CmsPageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class TestEnum {

    @Autowired
    private CmsPageMapper cmsPageMapper;

    @Test
    public void createIndex() {
        Cms cms =new Cms();
        cms.setMsgMethod(1);
        cmsPageMapper.insert(cms);
    }
    @Test
    public void getIndex() {
        Cms cms = cmsPageMapper.selectById("d5cb1277ed78466421c2bbfa7e6eb64e");
        cms.setMsgMethodStatus(MsgMethodEnum.valueOf(cms.getMsgMethod()));
        System.out.println(cms);
    }
    @Test
    public void getEnum() {
        ArrayList<Map<String, Object>> list = MsgMethodEnum.getList();
        System.out.println(list);
    }
}
