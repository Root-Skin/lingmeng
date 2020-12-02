package com.lingmeng.service.goods;

import com.lingmeng.api.good.IspuService;
import com.lingmeng.api.good.es.GoodSHTMLService;
import com.lingmeng.common.utils.thread.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

@Service
public class GoodSHTMLServiceImpl implements GoodSHTMLService {

    @Autowired
    private IspuService spuService;

    @Autowired
    private TemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodSHTMLServiceImpl.class);

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    public void createHtml(String spuId) {

        PrintWriter writer = null;
        try {
            // 获取页面数据
            Map<String, Object> result =  this.spuService.getGoodDetail(spuId);

            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(result);

            // 创建输出流

            File file = new File("D:\\develope\\develope-soft\\nginx\\nginx-1.12.2\\nginx-1.12.2\\html\\goods\\" + spuId + ".html");
            writer = new PrintWriter(file);

            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            LOGGER.error("页面静态化出错：{}，"+ e, spuId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void deleteHtml(String id) {
        File file = new File("D:\\develope\\develope-soft\\nginx\\nginx-1.12.2\\nginx-1.12.2\\html\\goods\\" + id + ".html");

        file.deleteOnExit();
    }

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    public void asyncExcute(String spuId) {
        ThreadUtils.execute(()->createHtml(spuId));
        /*ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                createHtml(spuId);
            }
        });*/
    }
}
