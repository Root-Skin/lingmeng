import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.common.utils.wxPay.PayHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class createPayUrl {

    @Autowired
    private PayHelper payHelper;
    @Test
    public void createIndex() {
        payHelper.createPayUrl("111");
    }
//    @Test
//    public PayState queryOrder() {
//        PayState payState = payHelper.queryOrder("20150806125346");
//        return payState;
//    }
}
