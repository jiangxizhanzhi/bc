package com.cyberpeace.cs.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 本地场景映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	/**
	 * 由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@link LocalScene}
	 */
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

	/**
	 * 选择通过场景id
	 *
	 * @param sceneId 场景id
	 * @return {@link LocalScene}
	 */
	LocalScene selectBySceneId(@Param("sceneId") String sceneId);

	/**
	 * 选择所有
	 *
	 * @return {@link List}<{@link LocalScene}>
	 */
	List<LocalScene> selectAll();
}
