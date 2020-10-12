package com.lingmeng.service.goods;


import com.lingmeng.api.good.IspuService;
import com.lingmeng.dao.goods.SpuMapper;
import com.lingmeng.model.goods.vo.req.AddGoodReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SpuServiceImpl implements IspuService {


    @Autowired
    private SpuMapper goodMapper;

    @Override
    public Boolean addNewGood(AddGoodReq req) {

        return null;
    }
}
