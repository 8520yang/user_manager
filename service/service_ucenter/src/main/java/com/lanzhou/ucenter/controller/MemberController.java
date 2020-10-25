package com.lanzhou.ucenter.controller;


import com.lanzhou.commouils.JwtUtils;
import com.lanzhou.commouils.R;
import com.lanzhou.commouils.orderVo.MemberVo;
import com.lanzhou.ucenter.entity.Member;
import com.lanzhou.ucenter.entity.Vo.RegisterVo;
import com.lanzhou.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody Member member){
        //调用service的方法实现登录
        //返回一个token值，用jwt
        String token=memberService.login(member);
         return R.OK().data("token",token);
    }
    //注册
    @PostMapping("register")
    public R registeruser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.OK();
    }

    //根据token获取页面信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        //获取头信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //调用数据库中的方法查询数据库
        Member byId = memberService.getById(memberId);
        return R.OK().data("userInfo",byId);
    }
    @GetMapping("getInfoUc/{id}")
    public Member getUcenterPay(@PathVariable String id){
        Member byId = memberService.getById(id);
        return byId;
    }



    //远程调用使用
    //根据id获取信息
    @PostMapping("getUserInfoOrder/{id}")
    public MemberVo getUserInfoOrder(@PathVariable("id") String id){
        Member byId = memberService.getById(id);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(byId,memberVo);
        return memberVo;
    }


    //查询某一天注册的人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count=memberService.countRegisterDay(day);
        return R.OK().data("countRegister",count);
    }
}

