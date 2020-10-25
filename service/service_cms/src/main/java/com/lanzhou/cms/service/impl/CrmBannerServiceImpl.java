package com.lanzhou.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.cms.entity.CrmBanner;
import com.lanzhou.cms.mapper.CrmBannerMapper;
import com.lanzhou.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    //查询banner
    @Cacheable(key ="'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {

        //排序
        QueryWrapper<CrmBanner> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //显示前两条记录
        //last方法，拼接sql
        wrapper.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(null);
        return crmBanners;
    }
}
