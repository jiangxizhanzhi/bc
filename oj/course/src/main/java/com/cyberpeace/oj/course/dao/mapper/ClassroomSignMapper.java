package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassroomSignMapper extends BaseMapper<ClassroomSign> {

    /**
     * 根据课堂查询签到信息
     * @param classroomId
     * @return
     */
    List<ClassroomSign> selectByRoom(@Param("roomId") String classroomId);
}

