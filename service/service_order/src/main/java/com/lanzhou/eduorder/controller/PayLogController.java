package com.lanzhou.eduorder.controller;


import com.lanzhou.commouils.R;
import com.lanzhou.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    //参数是订单号
    @GetMapping("createNative/{id}")
    public R createNative(@PathVariable String id){
        //返回相关的信息，包含二维码地址，还有其他的信息
        Map map=payLogService.createNative(id);
        return R.OK().data(map);
    }




    //查询订单支付的状态
    //根据订单号查询
    @GetMapping("queryPayStatus/{orderId}")
    public R queryPayStatus(@PathVariable String orderId){
        Map<String,String> map =payLogService.queryPayStatus(orderId);
        if (map==null){
            return R.ERROR().message("支付出错");

        }
        //如果不为空，获取订单的状态
        if (map.get("trade_state").equals("SUCCESS")){
            //向我们支付表中加记录，并且跟新订单的状态
            payLogService.updateOrdrStatus(map);
            return R.OK().message("支付成功");
        }
        return R.OK().code(25000).message("支付中。。。");

    }

}

