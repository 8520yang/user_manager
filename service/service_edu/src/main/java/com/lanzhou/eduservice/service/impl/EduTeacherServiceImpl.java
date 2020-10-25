package com.lanzhou.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.eduservice.entity.EduTeacher;
import com.lanzhou.eduservice.mapper.EduTeacherMapper;
import com.lanzhou.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //粉装到pageteacher
        baseMapper.selectPage(teacherPage,wrapper);
        //获取数据
        Map<String, Object> map = new HashMap<>();
        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long total = teacherPage.getTotal();
        boolean next = teacherPage.hasNext();
        boolean previous = teacherPage.hasPrevious();
        long pages = teacherPage.getPages();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", next);
        map.put("hasPrevious", previous);
        //map返回

        return map;
    }
}
