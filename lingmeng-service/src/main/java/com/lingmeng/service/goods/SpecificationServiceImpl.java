package com.lingmeng.service.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.api.good.IspecificationService;
import com.lingmeng.dao.goods.SpecificationMapper;
import com.lingmeng.goods.model.Specification;
import com.lingmeng.goods.vo.req.SpecificationReq;
import com.lingmeng.goods.vo.res.AccurateSpecificationRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SpecificationServiceImpl implements IspecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public Boolean addSpecificationGroup(SpecificationReq req) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", req.getCategoryId()));
        //如果是null 就插入新的数据
        if (StringUtils.isEmpty(specification)) {
            Specification newSpecification = new Specification();
            ArrayList<Map> newArrayList = new ArrayList();
            //学习点 json的新增
            JSONObject newJSONObject = new JSONObject();
            newJSONObject.put("group", req.getGroupName());
            newArrayList.add(newJSONObject);
            newSpecification.setCategoryId(req.getCategoryId());
            newSpecification.setSpecifications(newArrayList.toString());
            return specificationMapper.insert(newSpecification) > 0;
        } else {
            //在元数据上面新增
            //之前的错误,在这里取出了 数组之后 还转换了一次成为json数据 .导致添加了数据太过于复杂
            List result = JSONObject.parseArray(specification.getSpecifications());
            Map newGroup = new HashMap();
            newGroup.put("group", req.getGroupName());
            result.add(newGroup);
            specification.setSpecifications(JSON.toJSONString(result));
            specification.setCreatedBy("1");
            specification.setCreatedTime(new Date());
            specification.setUpdatedBy("1");
            specification.setUpdatedTime(new Date());
            specification.setDelFlag(false);
            return specificationMapper.update(specification, new QueryWrapper<Specification>().eq("category_id", req.getCategoryId())) > 0;
        }
    }

    @Override
    public List<AccurateSpecificationRes> getSpecification(String cateId, String groupName) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", cateId));
        if(StringUtils.isEmpty(specification)){
            return null;
        }
        List<String> strings = JSONObject.parseArray(specification.getSpecifications(), String.class);
        if(CollectionUtils.isEmpty(strings)){
            return null;
        }
        for (String s : strings) {
            Map map = JSONObject.parseObject(s, Map.class);
            if (map.get("group").equals(groupName)) {
                System.out.println(map.get("param"));
                if(StringUtils.isEmpty(map.get("param"))){
                    return null;
                }
                List<AccurateSpecificationRes> list = JSONArray.parseArray(JSONObject.toJSONString(map.get("param")), AccurateSpecificationRes.class);
                for (AccurateSpecificationRes res : list) {
                    res.setId(cateId);
                }
                return list;
            }
        }
        return null;
    }


}
