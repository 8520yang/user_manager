package com.lanzhou.statistics.service;

import com.lanzhou.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
