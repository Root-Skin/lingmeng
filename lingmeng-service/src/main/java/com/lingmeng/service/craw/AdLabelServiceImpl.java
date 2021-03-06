package com.lingmeng.service.craw;

import com.lingmeng.api.craw.AdLabelService;
import com.lingmeng.common.utils.craw.HMStringUtils;
import com.lingmeng.craw.AdChannelLabel;
import com.lingmeng.craw.AdLabel;
import com.lingmeng.dao.craw.AdChannelLabelMapper;
import com.lingmeng.dao.craw.AdLabelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdLabelServiceImpl  implements AdLabelService {

    @Autowired
    private AdLabelMapper adLabelMapper;

    @Autowired
    private AdChannelLabelMapper adChannelLabelMapper;

    @Override
    public String getLableIds(String labels) {

        long currentTime = System.currentTimeMillis();
        log.info("获取channel信息，标签：labels：{}", labels);
        List<AdLabel> adLabelList = new ArrayList<AdLabel>();
        if (StringUtils.isNotEmpty(labels)) {
            //转换成小写
            labels = labels.toLowerCase();
            List<String> labelList = Arrays.asList(labels.split(","));
            log.info("查询label数组：{}", labelList);
            List<AdLabel> tmpLabels = adLabelMapper.queryAdLabelByLabels(labelList);
            if (null != tmpLabels && !tmpLabels.isEmpty()) {
                adLabelList = addLabelList(tmpLabels, labelList);
            } else {
                adLabelList = addLabelList(labelList);
            }
        }

        List<String> labelIdList = adLabelList.stream().map(label -> HMStringUtils.toString(label.getId())).collect(Collectors.toList());
        String labelIds = HMStringUtils.listToStr(labelIdList, ",");
        log.info("获取channel信息完成，标签：labels：{},labelIds:{},耗时：{}", labels, labelIds, System.currentTimeMillis() - currentTime);
        return labelIds;
    }

    @Override
    public Integer getAdChannelByLabelIds(String labelIds) {
        Integer channelId = 0;
        try {
            channelId = getSecurityAdChannelByLabelIds(labelIds);
        } catch (Exception e) {
            log.error("获取channel信息失败，errorMsg:{}", e.getMessage());
        }
        return channelId;
    }


    private Integer getSecurityAdChannelByLabelIds(String labelIds) {
        long currentTime = System.currentTimeMillis();
        log.info("获取channel信息，标签IDS：labelIds：{}", labelIds);
        Integer channelId = 0;
        if (StringUtils.isNotEmpty(labelIds)) {
            //转换成小写
            List<String> labelList = Arrays.asList(labelIds.split(","));
            log.info("查询label数组：{}", labelList);
            List<AdLabel> adLabelList = adLabelMapper.queryAdLabelByLabelIds(labelList);
            if (null != adLabelList && !adLabelList.isEmpty()) {
                channelId = geAdChannelIdByLabelId(adLabelList.get(0).getId());
            }
        }
        channelId = channelId == null ? 0 : channelId;

        log.info("获取channel信息完成，标签：labelIds：{},channelId:{},耗时：{}", labelIds, channelId, System.currentTimeMillis() - currentTime);
        return channelId;
    }


    public Integer geAdChannelIdByLabelId(String labelId) {
        Integer channelId = 0;
        AdChannelLabel adChannelLabel = adChannelLabelMapper.selectByLabelId(labelId);
        if (null != adChannelLabel) {
            channelId = adChannelLabel.getChannelId();
        }
        return channelId;
    }

    public List<AdLabel> addLabelList(List<AdLabel> adLabelList, List<String> orginLabelList) {
        List<String> unAddLabelList = adLabelList.stream().map(x -> x.getName()).filter(x -> !orginLabelList.contains(x)).collect(Collectors.toList());
        return addLabelList(unAddLabelList);
    }

    public List<AdLabel> addLabelList(List<String> labelList) {
        List<AdLabel> adLabelList = new ArrayList<AdLabel>();
        if (null != labelList && !labelList.isEmpty()) {
            for (String label : labelList) {
                adLabelList.add(addLabel(label));
            }
        }
        return adLabelList;
    }

    /**
     * 添加label
     *
     * @param label
     */
    public AdLabel addLabel(String label) {
        AdLabel adLabel = new AdLabel();
        adLabel.setName(label);
        adLabel.setType(true);
        adLabel.setCreatedTime(new Date());
        adLabelMapper.insertSelective(adLabel);
        return adLabel;
    }
}
