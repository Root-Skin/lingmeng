package com.lingmeng.controller.miaosha;

import com.lingmeng.api.miaosha.ImiaoshaService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.miaosha.vo.MiaoshaRedisDataVo;
import com.lingmeng.task.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private ImiaoshaService imiaoshaService;

     /**
      * @Author skin
      * @Date  2020/12/9
      * @Description 查询秒杀时间段
      **/
     @GetMapping("/timeList")
    public RestReturn addBrand(){
         List<Date> dateMenus = DateUtil.getDateMenus();
         List<String> result = dateMenus.stream().map(e -> DateUtil.miaoshaTime(e)).collect(Collectors.toList());
         return RestReturn.ok(result);
     }
     /**
      * @Author skin
      * @Date  2020/12/9
      * @Description 得到具体时段的秒杀产品
      **/
    @GetMapping("/getMiaoshaGoodsByTime")
    public RestReturn getMiaoshaGoodsByTime(String time){
        //2020-12-09 16:00:00
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = "";
        try {
            Date date = simpleDateFormat.parse(time);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMddHH");
            formatTime = simpleDateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<MiaoshaRedisDataVo> miaoshaGoodsByTime = imiaoshaService.getMiaoshaGoodsByTime(formatTime);
        return RestReturn.ok(miaoshaGoodsByTime);
    }

     /**
      * @Author skin
      * @Date  2020/12/10
      * @Description 点击抢购 进入详情信息
      **/
    @GetMapping("/getMiaoshaDetail")
    public RestReturn getMiaoshaDetail(String time,String id){
        MiaoshaRedisDataVo miaoshaDetail = imiaoshaService.getMiaoshaDetail(time, id);
        return RestReturn.ok(miaoshaDetail);
    }


}
