package com.lingmeng.task.jobs;


import com.lingmeng.dao.goods.SkuMapper;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import com.lingmeng.task.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class TaskTest {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Scheduled(cron = "* * * * * ? ")
    public void test(){
        System.out.println("哇哈哈");
    }


//    @Scheduled(cron = "* * * * * ? ")
    @Scheduled(cron = "0 0/1 * * * ? ")
//    @Scheduled(cron = " 0 0 1 * * ? ")
    public void loadMiaoshaIntoRedis(){


        log.info("每秒钟开始获取该时段需要秒杀的商品-----------------START");
        //得到秒杀的时间段
        List<Date> dateMenus = DateUtil.getDateMenus();
        for(Date startTime :dateMenus){
            //时间转成yyyyMMddHH
            String FormatedStartTime = DateUtil.date2Str(startTime);
            //计算结束时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.add(Calendar.HOUR, 2);
            Date endtime = calendar.getTime();
            //得到当前时段开始->redis中的缓存(如果redis中存在,我们要进行排除)
            Set keys = redisTemplate.boundHashOps("miaoshaGood_" + FormatedStartTime).keys();

            List<MiaoshaRedisDataVo> maps = skuMapper.selectByMiaoshaTime(startTime, endtime, keys);
            for (MiaoshaRedisDataVo miaosha : maps) {
                //查找出来的数据放入缓存
                //Before Save into redis the pojo must implements Serializable
                redisTemplate.boundHashOps("miaoshaGood_"+FormatedStartTime).put(miaosha.getId(),miaosha);

                //In order to prevent High concurrency oversold
                String[] ids = pushIds(miaosha.getStock(), miaosha.getId());
                //商品数据存储队列
                redisTemplate.boundListOps("miaoshaGoodCountList_"+miaosha.getId()).leftPushAll(ids);

                //自增计数器
                redisTemplate.boundHashOps("miaoshaGoodCount").increment(miaosha.getId(),miaosha.getStock());

            }
        }
        log.info("每秒钟开始获取该时段需要秒杀的商品-----------------END");

    }

    public String[] pushIds(int len,String id){
        String[] ids = new String[len];
        for (int i = 0; i <len ; i++) {
            ids[i]=id;
        }
        return ids;
    }

}
