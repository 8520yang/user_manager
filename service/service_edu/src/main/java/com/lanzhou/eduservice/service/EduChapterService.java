package com.lanzhou.eduservice.service;

import com.lanzhou.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanzhou.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
