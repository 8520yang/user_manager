package com.lanzhou.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.cms.entity.CrmBanner;
import com.lanzhou.cms.service.CrmBannerService;
import com.lanzhou.commouils.R;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-05
 */
@RestController
@RequestMapping("/cms/crm-banner")
@CrossOrigin
@MapperScan("com.lanzhou.cms.mapper")
public class CrmBannerAdmainController {

    private CrmBannerService bannerService;

    //实现对banner的增删改查操作
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> page1 = new Page<>(page,limit);
        bannerService.page(page1,null);
        return R.OK().data("items",page1.getRecords()).data("total",page1.getTotal());
    }


    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.OK();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.OK();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.OK();

    }
}

