package com.lanzhou.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.commouils.JwtUtils;
import com.lanzhou.commouils.R;
import com.lanzhou.commouils.orderVo.CourseOrderVo;
import com.lanzhou.eduservice.client.OrdersClient;
import com.lanzhou.eduservice.entity.EduChapter;
import com.lanzhou.eduservice.entity.EduCourse;
import com.lanzhou.eduservice.entity.EduTeacher;
import com.lanzhou.eduservice.entity.chapter.ChapterVo;
import com.lanzhou.eduservice.entity.frontVo.CourseFrontVo;
import com.lanzhou.eduservice.entity.frontVo.CourseWebVo;
import com.lanzhou.eduservice.service.EduChapterService;
import com.lanzhou.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    //条件查询带分页
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrdersClient ordersClient;
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getfrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse=new Page<>(page,limit);
        Map<String,Object> map=eduCourseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.OK().data(map);
    }
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId,HttpServletRequest request){
        //根据id
        CourseWebVo courseWebVo=eduCourseService.getBaseCourseInfo(courseId);
        //根据id查询章小节
        List<ChapterVo> chapterVideoByCourseId = eduChapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询课程是否已经被支付过
        //远程调用过程
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = ordersClient.isBuyCourse(courseId, userId);
        return R.OK().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoByCourseId).data("isBuy",buyCourse);
    }



    //根据课程id查询课程的信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable String id ){
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseOrderVo courseOrderVo = new CourseOrderVo();

        BeanUtils.copyProperties(courseInfo,courseOrderVo);
        return courseOrderVo;
    }
}
