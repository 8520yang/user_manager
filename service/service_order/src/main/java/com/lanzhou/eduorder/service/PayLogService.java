package com.lanzhou.eduorder.service;

import com.lanzhou.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String id);

    Map<String, String> queryPayStatus(String orderId);

    void updateOrdrStatus(Map<String, String> map);
}
