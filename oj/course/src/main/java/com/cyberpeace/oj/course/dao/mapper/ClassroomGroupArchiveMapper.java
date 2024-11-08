package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupArchive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课堂群档案Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface ClassroomGroupArchiveMapper extends BaseMapper<ClassroomGroupArchive> {

	/**
	 * 根据名称查询课堂分组模板
	 *
	 * @param classroomId
	 * @param name
	 * @return
	 */
	ClassroomGroupArchive selectByRoomAndName(@Param("roomId") String classroomId,
			@Param("name") String name);

	/**
	 * 查询分组模板数据
	 *
	 * @param classroomId
	 * @return
	 */
	List<ClassroomGroupArchive> selectByRoom(@Param("roomId") String classroomId);
}
