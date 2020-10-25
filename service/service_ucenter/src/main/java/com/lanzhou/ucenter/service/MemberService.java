package com.lanzhou.ucenter.service;

import com.lanzhou.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanzhou.ucenter.entity.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-08
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
