package com.cyberpeace.oj.course.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.oj_entity.Lesson;

// 新表对应mapper
@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {

    IPage<Lesson> pageByContext(Page page, @Param("context") LessonContext context);

}
