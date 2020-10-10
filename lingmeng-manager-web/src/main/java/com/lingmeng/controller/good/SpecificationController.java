package com.lingmeng.controller.good;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.api.good.IspecificationService;
import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.goods.SpecificationMapper;
import com.lingmeng.exception.RestException;
import com.lingmeng.model.goods.model.Specification;
import com.lingmeng.model.goods.vo.req.*;
import com.lingmeng.model.goods.vo.res.AccurateSpecificationRes;
import com.lingmeng.model.goods.vo.res.SpecificationRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private IspecificationService specificationService;

    /**
     * @Author skin
     * @Date 2020/9/30
     * @Description 该接口用于查询分类下的规格()
     * <p>
     * //todo 这里需要使用弹窗进行显示
     **/
    @GetMapping("/getByCateId")
    public RestReturn getSpecificationByCateId(@RequestParam String cateId) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", cateId));
        if (StringUtils.isEmpty(specification)) {
            throw new RestException("该分类下对应的规格不存在");
        }
        SpecificationRes res = new SpecificationRes();
        res.setCategoryId(specification.getCategoryId());
        res.setGroupName(JSONObject.parseArray(specification.getSpecifications()));
        return RestReturn.ok(res);
    }

    /**
     * @Author skin
     * @Date 2020/9/28
     * @Description 分类的基础上新增分组
     **/
    @PostMapping("/addGroup")
    public RestReturn addSpecificationGroup(@RequestBody SpecificationReq req) {
        Boolean result = specificationService.addSpecificationGroup(req);
        if (result) {
            return RestReturn.ok("新增分组成功");
        }
        return RestReturn.error("新增分组失败");
    }

    /**
     * @Author skin
     * @Date 2020/9/30
     * @Description 编辑分组名称
     **/
    @PostMapping("/editGroup")
    public RestReturn editSpecificationGroup(@RequestBody SpecificationEditReq req) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", req.getId()));
        if (StringUtils.isEmpty(specification)) {
            throw new RestException("该分类下对应的规格不存在");
        }
        List<String> result = JSONObject.parseArray(specification.getSpecifications(), String.class);

        //这里是重点,不是继续使用原来解析出来的json 而是重新构建新的对象插入数据库
        List list = new ArrayList();
        for (String s : result) {
            Map map = JSONObject.parseObject(s, Map.class);
            if (req.getName().equals(map.get("group"))) {
                map.put("group", req.getGroupName());
            }
            list.add(map);
        }
        specification.setSpecifications(JSON.toJSONString(list));
        specification.setCreatedBy("1");
        specification.setCreatedTime(new Date());
        specification.setUpdatedBy("1");
        specification.setUpdatedTime(new Date());
        specification.setDelFlag(false);
        specificationMapper.update(specification, new QueryWrapper<Specification>().eq("category_id", req.getId()));
        return RestReturn.ok("编辑成功");
    }

    /**
     * @Author skin
     * @Date 2020/9/30
     * @Description 查询某一个分类下 的分组项
     **/
    @GetMapping("/cateGroupList")
    public RestReturn SpecificationGroupList(@RequestParam String cateId) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", cateId));
        if (StringUtils.isEmpty(specification)) {
            throw new RestException("该分类下不存在分组");
        }
        List list = new ArrayList();
        //解析为List  来得到第一个元素0
        List<String> objects = JSONObject.parseArray(specification.getSpecifications(), String.class);
        for (String s : objects) {
            Map result = new HashMap();
            Map map = JSONObject.parseObject(s, Map.class);
            Object group = map.get("group");
            result.put("id", specification.getCategoryId());
            result.put("name", group);
            list.add(result);
        }
        return RestReturn.ok(list);
    }


    /**
     * @Author skin
     * @Date 2020/10/9
     * @Description 得到某一个分组下具体的规格参数
     **/

    @GetMapping("/getSpecification")
    public RestReturn getSpecification(@RequestParam String cateId, @RequestParam String groupName) {
        List<AccurateSpecificationRes> specification = specificationService.getSpecification(cateId, groupName);
        return RestReturn.ok(specification);
    }

    /**
     * @Author skin
     * @Date 2020/10/10
     * @Description 新增参数
     **/
    @PostMapping("/addSpecification")
    public RestReturn addSpecification(@RequestParam String groupId, @RequestParam String groupName, @RequestBody SpecificationaddReq req) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", groupId));
        //判断是否在该分组下存在 规格
        List<AccurateSpecificationRes> specifications = specificationService.getSpecification(groupId, groupName);
        if (StringUtils.isEmpty(specifications) && StringUtils.isEmpty(specification)) {
            Specification newSpecification = new Specification();
            Map map = new HashMap();
            List list = new ArrayList<>();
            list.add(req);
            map.put("group", groupName);
            map.put("param", list);
            newSpecification.setCategoryId(groupId);
            newSpecification.setSpecifications(JSONObject.toJSONString(map));
            specificationMapper.insert(newSpecification);
        } else {
            List<String> strings = JSONObject.parseArray(specification.getSpecifications(), String.class);
            for (String s : strings) {
                List list = new ArrayList();
                Map map = JSONObject.parseObject(s, Map.class);
                if (map.get("group").equals(groupName)) {
                    List<SpecificationaddReq> param = new ArrayList<>();
                    if (!StringUtils.isEmpty(map.get("param"))) {
                        param = JSONArray.parseArray(JSONObject.toJSONString(map.get("param")), SpecificationaddReq.class);
                    }
                    param.add(req);
                    map.put("param", param);
                    list.add(map);
                }
                //设置最新的param
                specification.setSpecifications(JSONObject.toJSONString(list));
            }
            specificationMapper.update(specification, new QueryWrapper<Specification>().eq("category_id", groupId));
        }
        return RestReturn.ok("新增成功");
    }

    /**
     * @Author skin
     * @Date 2020/10/10
     * @Description 编辑参数
     **/
    @PostMapping("/editSpecification")
    public RestReturn editSpecification(@RequestBody GroupSpecificationEditReq req) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", req.getId()));
        if (StringUtils.isEmpty(specification)) {
            throw new RestException("找不到该规格,请重新选择");
        }
        List<String> strings = JSONObject.parseArray(specification.getSpecifications(), String.class);
        List list = new ArrayList();
        List paramList = new ArrayList();
        for (String s : strings) {
            Map map = JSONObject.parseObject(s, Map.class);
            if (map.get("group").equals(req.getGroupName())) {
                List<SpecificationaddReq> params = JSONArray.parseArray(JSONObject.toJSONString(map.get("param")), SpecificationaddReq.class);
                map.put("group", map.get("group"));
                for (SpecificationaddReq param : params) {
                    //使用规格名称判断修改的是哪一个规格
                    if (param.getName().equals(req.getOldName())) {
                        //设置新名称
                        param.setName(req.getName());
                        param.setIsGeneral(req.getIsGeneral());
                        param.setIsNum(req.getIsNum());
                        param.setIsSearch(req.getIsSearch());
                        param.setUnit(req.getUnit());
                        param.setSearchMax(req.getSearchMax());
                        param.setSearchMin(req.getSearchMin());
                    }
                    paramList.add(param);
                    //原有数据被覆盖
                }
                map.put("param", paramList);
            }
            list.add(map);
            specification.setSpecifications(JSONObject.toJSONString(list));
        }
        specificationMapper.update(specification, new QueryWrapper<Specification>().eq("category_id", req.getId()));
        return RestReturn.ok("编辑成功");
    }

    /**
     * @Author skin
     * @Date 2020/10/10
     * @Description
     **/
    @PostMapping("/delSpecification")
    public RestReturn editSpecification(@RequestBody GroupSpecificationDelReq req) {
        Specification specification = specificationMapper.selectOne(new QueryWrapper<Specification>().eq("category_id", req.getId()));
        if (StringUtils.isEmpty(specification)) {
            throw new RestException("找不到该规格,请重新选择");
        }
        List<String> strings = JSONObject.parseArray(specification.getSpecifications(), String.class);
        List list = new ArrayList();
        List paramList = new ArrayList();
        for (String s : strings) {
            Map map = JSONObject.parseObject(s, Map.class);
            if (map.get("group").equals(req.getGroupName())) {
                List<SpecificationaddReq> params = JSONArray.parseArray(JSONObject.toJSONString(map.get("param")), SpecificationaddReq.class);
                for (SpecificationaddReq param : params) {
                    if (param.getName().equals(req.getName())) {
                        //设置新名称
                    } else {
                        paramList.add(param);
                    }
                }
                map.put("param", paramList);
            }
            list.add(map);
            specification.setSpecifications(JSONObject.toJSONString(list));
        }
        specificationMapper.update(specification, new QueryWrapper<Specification>().eq("category_id", req.getId()));
        return RestReturn.ok("删除成功");
    }
}
