package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomSceneRecord;

@Mapper
@Repository
public interface ClassroomSceneRecordMapper extends BaseMapper<ClassroomSceneRecord> {

	ClassroomSceneRecord selectByRoomAndUser(@Param("classroomId") String classroomId,
			@Param("userId") String userId);

	List<ClassroomSceneRecord> selectByRoomAndUsers(@Param("classroomId") String classroomId,
			@Param("userIds") List<String> userIds);
}
