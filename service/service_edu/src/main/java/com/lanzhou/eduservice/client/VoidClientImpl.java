package com.lanzhou.eduservice.client;

import com.lanzhou.commouils.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VoidClientImpl implements VodClient {
    @Override
    public R removeAlyVideo(String id) {

        return R.ERROR().message("删除视频出错");
    }

}
