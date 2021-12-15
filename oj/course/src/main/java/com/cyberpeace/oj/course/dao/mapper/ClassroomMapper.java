package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {

	List<Classroom> listByTime(@Param("start") Date startOfDay, @Param("end") Date endOfDay);

	List<Classroom> listByTimeAndOrg(@Param("start") Date startOfDay, @Param("end") Date endOfDay,
			@Param("org") List<String> organization, @Param("user") String createUser);

	/**
	 * 根据组织和课时查询课堂信息
	 * @param orgId
	 * @param lessonId
	 * @return
	 */
	Classroom selectByOrgAndLesson(@Param("orgId") String orgId, @Param("lessonId") String lessonId);
}
