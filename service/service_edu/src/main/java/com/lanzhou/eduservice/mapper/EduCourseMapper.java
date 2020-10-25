package com.lanzhou.eduservice.mapper;

import com.lanzhou.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanzhou.eduservice.entity.frontVo.CourseWebVo;
import com.lanzhou.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询方法
    public CoursePublishVo getPublishCourseInfo(String courseId);
    //根据课程id查基本信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
