package com.lanzhou.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.commouils.JwtUtils;
import com.lanzhou.commouils.R;
import com.lanzhou.eduorder.entity.Order;
import com.lanzhou.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    //生成订单方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id得到信息
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo=orderService.createOrders(courseId,memberIdByJwtToken);
        return R.OK().data("orderId",orderNo);
    }

    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order one = orderService.getOne(wrapper);
        return R.OK().data("item",one);
    }




    //判断是否课程被购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        //查询订单的状态
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count==0){
            return false;
        }else{
            return true;
        }
        //查询status是否为1，为1则支付。返回true
    }
}

