package com.lanzhou.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.eduservice.entity.EduCourse;
import com.lanzhou.eduservice.entity.EduCourseDescription;
import com.lanzhou.eduservice.entity.EduTeacher;
import com.lanzhou.eduservice.entity.frontVo.CourseFrontVo;
import com.lanzhou.eduservice.entity.frontVo.CourseWebVo;
import com.lanzhou.eduservice.entity.vo.CourseInfoVo;
import com.lanzhou.eduservice.entity.vo.CoursePublishVo;
import com.lanzhou.eduservice.mapper.EduCourseMapper;
import com.lanzhou.eduservice.service.EduCourseDescriptionService;
import com.lanzhou.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //课程表里加
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        //成功加入了几条记录
        if (insert<=0){
            System.out.println("添加失败");

        }
        //获取添加的id
        String cid=eduCourse.getId();

        //简介表里加
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CoursePublishVo publishCourseIInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> teacherPage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());

        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");

        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");

        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }



        baseMapper.selectPage(teacherPage,wrapper);
        //获取数据
        Map<String, Object> map = new HashMap<>();
        List<EduCourse> records = teacherPage.getRecords();
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
        return map;
    }

    //查询基本信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {

        return baseMapper.getBaseCourseInfo(courseId);
    }
}
