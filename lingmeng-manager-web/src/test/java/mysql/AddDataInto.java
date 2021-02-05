package mysql;

import com.lingmeng.LingmengManagerApplication;
import com.lingmeng.common.utils.spring.SpringContextHolder;
import com.lingmeng.dao.mysql.StudentMapper;
import com.lingmeng.dao.mysql.StudentScoreMapper;
import com.lingmeng.mysql.Student;
import com.lingmeng.mysql.StudentScore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LingmengManagerApplication.class)
public class AddDataInto {

    @Autowired
    private SpringContextHolder springContextHolder;


    private static String firstName="赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
    private static String girl="秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    private static String boy="伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";



    /**
     * @param
     * @author skin
     * @Date 2021/2/4 11:45
     * @description 学生成绩数据库添加数据 4436
     **/
    @Test
    public void Ttsc() {
        CountDownLatch latch = new CountDownLatch(2);
        new MyThread(latch).start();
        new MyThread2(latch).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        /**
//         * test主线程运行时，启动了线程池，线程池中的任务会加载bean，但因为异步原因，
//         * 任务提交给线程池后，主线程结束了，开始销毁bean容器，
//         * 而线程池任务有需要创建出bean，所以出现上述的异常情况。
//         */
//        StudentScore studentScore = new StudentScore();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        /**
//         * 这里只是向线程池中放入了一个线程
//         */
//        executorService.execute(() -> {
//            System.out.println(Thread.currentThread().getName() + " is running...");
//            for (int i = 0; i < 4436; i++) {
//                studentScore.setId(null);
//                studentScore.setSId(String.valueOf(i));
//                for (int j = 1; j < 6; j++) {
//                    studentScore.setCId("0" + j);
//                    studentScore.setScore(new BigDecimal(10 + (int) (Math.random() * (100 - 10 + 1))));
//                    this.studentScoreMapper.insert(studentScore);
//                }
//            }
//        });
//        while (((ThreadPoolExecutor)executorService).getActiveCount() > 0){
//            try {
//                Thread.sleep(500);  //阻塞主线程
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    class MyThread extends Thread {

        private StudentScoreMapper studentScoreMapper;
        private StudentMapper studentMapper;


        private CountDownLatch latch;

        public MyThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            studentScoreMapper = springContextHolder.getApplicationContext().getBean(StudentScoreMapper.class);
            studentMapper = springContextHolder.getApplicationContext().getBean(StudentMapper.class);
            System.out.println(Thread.currentThread().getName() + " is running...");
            for (int i = 0; i < 2218; i++) {
                StudentScore studentScore = new StudentScore();
                Student student = new Student();

                studentScore.setId(null);
                student.setSId(String.valueOf(i));
                student.setSName(getChineseName());
                student.setSSex(name_sex);
                student.setSAge(randomDateBetweenMinAndMax());
                studentMapper.insert(student);

                studentScore.setSId(String.valueOf(i));
                for (int j = 1; j < 6; j++) {
                    studentScore.setCId("0" + j);
                    studentScore.setScore(new BigDecimal(10 + (int) (Math.random() * (100 - 10 + 1))));
                    this.studentScoreMapper.insert(studentScore);
                }
            }
            latch.countDown();
        }
    }

    class MyThread2 extends Thread {

        private StudentScoreMapper studentScoreMapper;
        private StudentMapper studentMapper;

        private CountDownLatch latch;

        public MyThread2(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            studentScoreMapper = springContextHolder.getApplicationContext().getBean(StudentScoreMapper.class);
            studentMapper = springContextHolder.getApplicationContext().getBean(StudentMapper.class);
            System.out.println(Thread.currentThread().getName() + " is running...");
            for (int i = 2218; i < 4436; i++) {
                StudentScore studentScore = new StudentScore();
                Student student = new Student();
                studentScore.setId(null);
                student.setSId(String.valueOf(i));
                student.setSName(getChineseName());
                student.setSSex(name_sex);
                student.setSAge(randomDateBetweenMinAndMax());
                studentMapper.insert(student);
                studentScore.setSId(String.valueOf(i));
                for (int j = 1; j < 6; j++) {
                    studentScore.setCId("0" + j);
                    studentScore.setScore(new BigDecimal(10 + (int) (Math.random() * (100 - 10 + 1))));
                    this.studentScoreMapper.insert(studentScore);
                }
            }
            latch.countDown();
        }
    }
    private static String name_sex = "";
    private static String getChineseName() {
        int index=getNum(0, firstName.length()-1);
        String first=firstName.substring(index, index+1);
        int sex=getNum(0,1);
        String str=boy;
        int length=boy.length();
        if(sex==0){
            str=girl;
            length=girl.length();
            name_sex = "女";
        }else {
            name_sex="男";
        }
        index=getNum(0,length-1);
        String second=str.substring(index, index+1);
        int hasThird=getNum(0,1);
        String third="";
        if(hasThird==1){
            index=getNum(0,length-1);
            third=str.substring(index, index+1);
        }
        return first+second+third;
    }

    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    public static Date randomDateBetweenMinAndMax(){
        Calendar calendar = Calendar.getInstance();
        //注意月份要减去1
        calendar.set(1990,11,31);
        calendar.getTime().getTime();
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        long min = calendar.getTime().getTime();;
        calendar.set(1993,11,31);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.getTime().getTime();
        long max = calendar.getTime().getTime();
        //得到大于等于min小于max的double值
        double randomDate = Math.random()*(max-min)+min;
        //将double值舍入为整数，转化成long类型
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar.getTime();
    }
}
