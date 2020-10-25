package com.lanzhou.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行的读取内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("****"+demoData);
    }
    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    //读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
