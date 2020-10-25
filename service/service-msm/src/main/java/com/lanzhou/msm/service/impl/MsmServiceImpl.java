package com.lanzhou.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.lanzhou.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

//"LTAI4G4kzKYC3evgu886rSjZ", "DaKYqVGjMAfV0YVA8rmXFPBFpuV3dV"

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    //发送短信的方法
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G4kzKYC3evgu886rSjZ", "DaKYqVGjMAfV0YVA8rmXFPBFpuV3dV");
        IAcsClient client = new DefaultAcsClient(profile);

        //组装请求对象
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setMethod(MethodType.POST);
        commonRequest.setSysDomain("dysmsapi.aliyuncs.com");
        commonRequest.setVersion("2017-05-25");
        commonRequest.setAction("SendSms");


        //设置发送
        commonRequest.putQueryParameter("PhoneNumbers",phone);//手机号
        commonRequest.putQueryParameter("SignName","我的网站足球之家网站平台");//申请阿里云的签名
        commonRequest.putQueryParameter("TemplateCode","SMS_204126996");
        commonRequest.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码的数据，转json
        //最终发送
        try {
            CommonResponse commonResponse = client.getCommonResponse(commonRequest);
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;

        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
