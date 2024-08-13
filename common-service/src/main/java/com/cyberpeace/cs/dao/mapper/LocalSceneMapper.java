package com.cyberpeace.cs.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 修改销毁时间 空数据也修改
	 * @param localScene
	 */
	void updateEndTime(LocalScene localScene);

	/**
	 * 查询到关闭时间的场景
	 * @param closeTime
	 * @return
	 */
	List<LocalScene> selectByCloseTime(Date closeTime);

	/**
	 * 查询结束时在一定范围内的场景
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<LocalScene> selectBetweenEndTime(@Param("start_time") Date startTime,
			@Param("end_time") Date endTime);

	LocalScene selectBySceneId(@Param("sceneId") String sceneId);

	List<LocalScene> selectAll();

	/**
	 * 查询通过业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene}
	 */
	LocalScene selectByBusinessInstanceId(@Param("businessInstanceId") String businessInstanceId);

	/**
	 * 查询通过topoId
	 * @param topoId
	 * @return
	 */
	LocalScene selectLocalSceneByTopoId(@Param("topoId")String topoId);

	LocalScene selectLocalSceneBytemplateId(@Param("resourceId") String resourceId);

	LocalScene getByTaskId(@Param("templateId") String templateId);
}
