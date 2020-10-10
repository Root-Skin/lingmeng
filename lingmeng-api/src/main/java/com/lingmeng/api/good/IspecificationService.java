package com.lingmeng.api.good;

import com.lingmeng.model.goods.vo.req.SpecificationReq;
import com.lingmeng.model.goods.vo.res.AccurateSpecificationRes;

import java.util.List;

public interface IspecificationService {

     /**
      * @Author skin
      * @Date  2020/10/9
      * @Description 新增分组
      **/
    Boolean  addSpecificationGroup(SpecificationReq req);

     /**
      * @Author skin
      * @Date  2020/10/9
      * @Description 查询某一个分组下的具体规格
      **/
     List<AccurateSpecificationRes> getSpecification(String  cateId, String groupName);
}
