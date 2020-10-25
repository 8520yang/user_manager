package com.lanzhou.cms.controller;

import com.lanzhou.cms.entity.CrmBanner;
import com.lanzhou.cms.service.CrmBannerService;
import com.lanzhou.commouils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//前台banner显示
@RestController
@RequestMapping("/cms/crm-bannerFront")
@CrossOrigin

public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list =bannerService.selectAllBanner();
        return R.OK().data("list",list);
    }
}
