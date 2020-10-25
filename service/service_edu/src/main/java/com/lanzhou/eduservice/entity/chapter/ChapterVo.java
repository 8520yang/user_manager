package com.lanzhou.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VedioVo> children=new ArrayList<>();
}
