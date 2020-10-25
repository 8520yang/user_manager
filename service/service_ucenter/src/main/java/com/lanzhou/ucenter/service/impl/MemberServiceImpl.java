package com.lanzhou.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.commouils.JwtUtils;
import com.lanzhou.commouils.MD5;
import com.lanzhou.commouils.R;
import com.lanzhou.ucenter.entity.Member;
import com.lanzhou.ucenter.entity.Vo.RegisterVo;
import com.lanzhou.ucenter.mapper.MemberMapper;
import com.lanzhou.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-08
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(Member member) {
        //登陆的方法
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            System.out.println("登陆失败");

        }
        //判断手机号
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member member1 = baseMapper.selectOne(wrapper);
        if (member1==null){
            System.out.println("登陆失败");
        }
        //判断密码
        if (!MD5.encrypt(password).equals(member1.getPassword()))
        {
            System.out.println("失败");
        }
        //判断是否禁用
        if (member1.getIsDisabled()){
            System.out.println("登陆失败");
        }
        //登录成功
        //按规则生成token的字符串
        String jwtToken = JwtUtils.getJwtToken(member1.getId(), member1.getNickname());

        //返回一个token值

        return jwtToken;
    }
    //在service里面注册
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            System.out.println("注册失败");
        }
        //判断验证码
        //获取redis中验证码
        String s = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(s)){
            System.out.println("注册失败");

        }
        //判断手机号是否重复
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer>0){
            System.out.println("注册失败");

        }

        //添加到数据库
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);


    }
    //根据openid查询
    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);

        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

}
