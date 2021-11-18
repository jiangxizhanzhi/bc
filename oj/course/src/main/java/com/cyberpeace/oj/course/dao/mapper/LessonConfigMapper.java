package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LessonConfigMapper extends BaseMapper<LessonConfig> {

    LessonConfig getFirstNode(@Param("courseId") String courseId);

    List<LessonConfig> listByParent(@Param("parentId") String parentId,
                                    @Param("isPublic") Boolean isPublic);

    List<LessonConfig> selectByCourse(@Param("courseId") String courseId,
                                      @Param("isPublic") Boolean isPublic);

    LessonConfig selectLastByParent(@Param("courseId") String courseId);
}
