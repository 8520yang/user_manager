package com.lanzhou.eduorder.client;

import com.lanzhou.commouils.orderVo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public MemberVo getUserInfoOrder(@PathVariable("id") String id);
}
