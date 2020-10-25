package com.lanzhou.ucenter.controller;

import com.google.gson.Gson;
import com.lanzhou.commouils.JwtUtils;
import com.lanzhou.ucenter.entity.Member;
import com.lanzhou.ucenter.service.MemberService;
import com.lanzhou.ucenter.utils.ConstantWxUtils;
import com.lanzhou.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.HashAttributeSet;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private MemberService memberService;

    @GetMapping("callback")
    public String callback(String code,String state){
        //获取到code值，临时票据
        //拿着code请求微信固定地址，得到accerr_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);

        //请求
        String assesTokenInfo = null;
        try {
            assesTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从accessTokenInfo获取值
        //把accessTokenInfo转换成map
        // 使用json转换工具
        Gson gson = new Gson();
        HashMap tokenMap = gson.fromJson(assesTokenInfo, HashMap.class);
        String access_token = (String) tokenMap.get("access_token");
        String openid = (String) tokenMap.get("openid");
        //拿着得到的信息再去请求，得到扫码人的个人信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
        String userInfo="";
        try {
            userInfo= HttpClientUtils.get(userInfoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(userInfo);
        HashMap hashMap = gson.fromJson(userInfo, HashMap.class);
        String nickname = (String) hashMap.get("nickname");
        String headimgurl = (String) hashMap.get("headimgurl");//头像
        //把扫码人信息添加到数据库
        //判断数据库表里是否存在相同的微信信息
        Member member=memberService.getOpenIdMember(openid);
        if (member==null){
            //如果member是空，表里面没有相同的微信的数据，进行添加
            member=new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            memberService.save(member);

        }
        //最后，回到首页面中
        //使用jwt根据member对象生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());


        return "redirect:http://localhost:3000?token="+jwtToken;
    }






    @GetMapping("login")
    public String getWxCode(){
        //请求微信的地址
        String url="https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //URLEncoder
        String redict_url=ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redict_url = URLEncoder.encode(redict_url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String atguigu = String.format(url, ConstantWxUtils.WX_OPEN_APP_ID,redict_url, "atguigu");
        //重定向
        return "redirect:"+atguigu;
    }




}
