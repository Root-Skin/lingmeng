package com.lingmeng.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.order.model.Order;


public interface OrderMapper extends BaseMapper<Order> {

    Integer updateOrderStatus(String orderId);

}
