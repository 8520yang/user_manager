package com.lanzhou.statistics.Shedule;

import com.lanzhou.statistics.service.DailyService;
import com.lanzhou.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SheduleTask {
    //每隔五秒钟去执行一次
    @Autowired
    private DailyService dailyService;

    //在每天凌晨一点开始执行,把前一天的数据进行查询
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task1(){
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));

    }
}
