package com.lingmeng.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.goods.model.Specification;
import org.apache.ibatis.annotations.Param;


public interface SpecificationMapper extends BaseMapper<Specification> {


    Specification  getSpecificationByCateId(@Param("cateId") String cateId);
}
