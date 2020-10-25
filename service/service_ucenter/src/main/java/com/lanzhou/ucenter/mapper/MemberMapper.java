package com.lanzhou.ucenter.mapper;

import com.lanzhou.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-10-08
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegisterDay(String day);
}
