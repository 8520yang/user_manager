package com.lanzhou.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.commouils.JwtUtils;
import com.lanzhou.commouils.R;
import com.lanzhou.eduservice.client.UcenterClient;
import com.lanzhou.eduservice.entity.EduComment;
import com.lanzhou.eduservice.entity.vo.Member;
import com.lanzhou.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-13
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
@CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment eduComment, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberIdByJwtToken)){
            return R.ERROR().code(28004).message("请登录");
        }
        eduComment.setMemberId(memberIdByJwtToken);
        Member ucenterPay = ucenterClient.getUcenterPay(memberIdByJwtToken);
        eduComment.setNickname(ucenterPay.getNickname());
        eduComment.setAvatar(ucenterPay.getAvatar());
        commentService.save(eduComment);
        return R.OK();
    }





    @GetMapping("{page}/{limit}")
    public R index(@PathVariable Long page,@PathVariable Long limit,String courseId){
        Page<EduComment> page1=new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(page1,wrapper);
        List<EduComment> records = page1.getRecords();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("items1",records);
        hashMap.put("current1",page1.getCurrent());
        hashMap.put("pages1",page1.getPages());
        hashMap.put("size1",page1.getSize());
        hashMap.put("total1",page1.getTotal());
        hashMap.put("hasNext1",page1.hasNext());
        hashMap.put("hasPrevious1",page1.hasPrevious());
        return R.OK().data("comment",hashMap);
    }
}

