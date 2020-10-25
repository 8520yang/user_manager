package com.lanzhou.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitObject {
    public static DefaultAcsClient initVodClient(String accessKeyId,String accessKeySecret) {
        String regionId="cn-shanghai";
        DefaultProfile profile=DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        return defaultAcsClient;
    }
}
