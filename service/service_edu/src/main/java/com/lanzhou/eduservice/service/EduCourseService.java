package com.lanzhou.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanzhou.eduservice.entity.frontVo.CourseFrontVo;
import com.lanzhou.eduservice.entity.frontVo.CourseWebVo;
import com.lanzhou.eduservice.entity.vo.CourseInfoVo;
import com.lanzhou.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseIInfo(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
