package com.lanzhou.eduorder.service;

import com.lanzhou.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberIdByJwtToken);
}
