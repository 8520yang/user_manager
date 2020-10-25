package com.lanzhou.msm.controller;

import com.lanzhou.commouils.R;
import com.lanzhou.msm.service.MsmService;
import com.lanzhou.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edu/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //先从redis中获取验证码
        String s = redisTemplate.opsForValue().get(phone);
        if (s!=null){
            return R.OK();
        }else {
            //取不到则发送

            //调用service中的方法将短信进行发送
            //接受到的验证码有可能四位，有可能六位，有可能八位，是我们程序自己生成的
            String code= RandomUtil.getFourBitRandom();
            //传递给阿里云短信服务
            Map<String,Object> param=new HashMap<>();
            param.put("code",code);
            //用service的方法
            boolean isSend=msmService.send(param,phone);
            if (isSend){
                //发送成功。设置有效时间
                redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
                return R.OK();
            }else {
                return R.ERROR().message("发送失败");
            }

        }

    }
}
