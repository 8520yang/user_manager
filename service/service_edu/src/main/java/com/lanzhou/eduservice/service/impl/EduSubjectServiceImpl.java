package com.lanzhou.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.eduservice.entity.EduSubject;
import com.lanzhou.eduservice.entity.excel.SubjectData;
import com.lanzhou.eduservice.entity.subject.OneSubject;
import com.lanzhou.eduservice.entity.subject.TwoSubject;
import com.lanzhou.eduservice.listener.SubjectExcelListener;
import com.lanzhou.eduservice.mapper.EduSubjectMapper;
import com.lanzhou.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try{
            InputStream in=file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){}

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //把查询出所有的一级分类，二级分类
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();

        wrapper.eq("parent_id","0");
        List<EduSubject> list = this.list(wrapper);
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id","0");
        List<EduSubject> list1 = this.list(wrapper1);
        //封装
        //创建list集合
        List<OneSubject> finalSubjectList=new ArrayList<>();
        //封装一级，把我们查询出来的一级分类list集合遍历，获取值，封装到要求的集合里面去
        for (int i = 0; i < list.size(); i++) {
            EduSubject eduSubject = list.get(i);
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
//            finalSubjectList.add(oneSubject);
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);
            List<TwoSubject> twoFinalSubjects=new ArrayList<>();
            for (int j = 0; j <list1.size(); j++) {
                EduSubject eduSubject1 = list1.get(j);
                if (eduSubject1.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1,twoSubject);
                    twoFinalSubjects.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjects);


        }


        return finalSubjectList;
    }
}
















