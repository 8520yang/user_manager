package com.lanzhou.eduorder.service.impl;

import com.lanzhou.commouils.orderVo.CourseOrderVo;
import com.lanzhou.commouils.orderVo.MemberVo;
import com.lanzhou.eduorder.client.EduClient;
import com.lanzhou.eduorder.client.UcenterClient;
import com.lanzhou.eduorder.entity.Order;
import com.lanzhou.eduorder.mapper.OrderMapper;
import com.lanzhou.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanzhou.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    //生成订单的方法
    //远程调用
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        //通过远程调用获取你的用户信息课程信息
        CourseOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        MemberVo userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);
        Order order = new Order();
        //order对象里面设置需要的数据
        //返回订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(userInfoOrder.getId());
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);//支付状态。默认值是0，未支付
        order.setPayType(1);//支付类型，默认是1
        baseMapper.insert(order);
        return order.getOrderNo();//返回一个订单id

    }
}
