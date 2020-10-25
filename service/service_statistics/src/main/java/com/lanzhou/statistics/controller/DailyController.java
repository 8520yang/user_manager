package com.lanzhou.statistics.controller;


import com.lanzhou.commouils.R;
import com.lanzhou.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {
    @Autowired
    private DailyService dailyService;
    //统计某一天注册人数
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.OK();
    }

    //图表显示，返回日期和json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showDate(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map= dailyService.getShowData(type,begin,end);
        return R.OK().data(map);
    }
}

