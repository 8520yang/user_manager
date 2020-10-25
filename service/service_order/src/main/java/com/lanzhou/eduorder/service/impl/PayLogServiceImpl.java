package com.lanzhou.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.lanzhou.eduorder.entity.Order;
import com.lanzhou.eduorder.entity.PayLog;
import com.lanzhou.eduorder.mapper.PayLogMapper;
import com.lanzhou.eduorder.service.OrderService;
import com.lanzhou.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanzhou.eduorder.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    //生成二维码
    @Override
    public Map createNative(String id) {
        try {
            //根据请求得到订单信息
            QueryWrapper<Order> wrapper=new QueryWrapper<>();
            wrapper.eq("order_no",id);
            Order order = orderService.getOne(wrapper);
            //设置参数
            Map m=new HashMap();
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());//工具类，生成随机字符串，生成二维码都不一样
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", id);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//价格对象
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");//支付类型，根据价格生成二维码支付
            //发送http
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml方式的参数
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.setHttps(true);
            //指行
            httpClient.post();
            //返回结果
            String content = httpClient.getContent();
            //返回的内容是使用xml格式返回
            //把xml转换成map，把map返回回来
            Map<String,String> resultMap=WXPayUtil.xmlToMap(content);
            Map map = new HashMap<>();
            map.put("out_trade_no", id);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    //查询订单状态
    @Override
    public Map<String, String> queryPayStatus(String orderId) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderId);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //跟新支付记录
    @Override
    public void updateOrdrStatus(Map<String, String> map) {
        //获取订单号，从map中
        String orderNo = map.get("out_trade_no");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order one = orderService.getOne(wrapper);
        if (one.getStatus().intValue()==1){
            return;
        }
        //修改状态
        one.setStatus(1);
        orderService.updateById(one);
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(one.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);
    }
}
