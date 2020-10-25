package com.lanzhou.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.commouils.R;
import com.lanzhou.statistics.client.UcenterClient;
import com.lanzhou.statistics.entity.Daily;
import com.lanzhou.statistics.mapper.DailyMapper;
import com.lanzhou.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    //
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        QueryWrapper<Daily> wrapper=new QueryWrapper<>();
        wrapper.eq("date_calculated",wrapper);
        baseMapper.delete(wrapper);
        //远程调用得到某一天的注册人数
        R r = ucenterClient.countRegister(day);
        Integer countRegister= (Integer) r.getData().get("countRegister");
        //得到注册人数的值
        //获取的数据添加到数据库
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister);
        daily.setDateCalculated(day);//统计日期

        daily.setVideoViewNum( RandomUtils.nextInt(100,200));
        daily.setLoginNum( RandomUtils.nextInt(100,200));
        daily.setCourseNum( RandomUtils.nextInt(100,200));
        baseMapper.insert(daily);
    }
    //返回要显示的数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询数据
        QueryWrapper<Daily> wrapper=new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<Daily> dailies = baseMapper.selectList(wrapper);
        //因为返回的由两部分数据，
        //前端是要求数组，后端需要转换
        //创建两个list集合
        List<String> date_calculatedList=new ArrayList<>();
        List<Integer> numDataList=new ArrayList<>();
        //遍历查询出来的所有数据list集合
        for (int i = 0; i < dailies.size(); i++) {
            Daily daily = dailies.get(i);
            date_calculatedList.add(daily.getDateCalculated());
            switch (type){
                case"login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case"register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case"video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case"course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //把我们封装之后的list集合放在map中
        Map<String,Object> map=new HashMap<>();
        map.put("date_calculated",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;

    }
}
