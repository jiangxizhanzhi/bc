package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LessonRecordMapper extends BaseMapper<LessonRecord> {

    List<LessonRecord> selectByCourseAndUser(String courseId, String userId);

}
