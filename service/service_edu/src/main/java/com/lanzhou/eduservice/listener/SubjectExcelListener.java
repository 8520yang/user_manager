package com.lanzhou.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.eduservice.entity.EduSubject;
import com.lanzhou.eduservice.entity.excel.SubjectData;
import com.lanzhou.eduservice.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {

    }

    //读取内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            System.out.println("为空");

        }
        //一行一行读取，第一个值是一级分类，第二个是二级分类
        EduSubject eduSubject = this.existOneSubject(subjectData.getOneSubjectName(), subjectService);
        if (eduSubject==null){
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(eduSubject);
        }
        String pid=eduSubject.getId();
        EduSubject eduSubject1 = this.existTwoSubject(subjectData.getTwoSubjectName(), subjectService, pid);
        if (eduSubject1==null){
            eduSubject1 = new EduSubject();
            eduSubject1.setParentId(pid);
            eduSubject1.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduSubject1);
        }
    }



    //一级分类不能重复
    private EduSubject existOneSubject(String name,EduSubjectService service){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }
    //二级不重复
    private EduSubject existTwoSubject(String name,EduSubjectService service,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject  two= subjectService.getOne(wrapper);
        return two;
    }





    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
