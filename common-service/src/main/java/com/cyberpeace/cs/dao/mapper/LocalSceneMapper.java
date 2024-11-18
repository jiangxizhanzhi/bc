package com.cyberpeace.cs.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 当地场景映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	/**
	 * 获取由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 修改销毁时间 空数据也修改
	 *
	 * @param localScene 当地场景
	 * @throws
	 * @see LocalScene
	 */
	void updateEndTime(LocalScene localScene);

	/**
	 * 查询到关闭时间的场景
	 *
	 * @param closeTime
	 * @return
	 * @throws
	 * @see Date
	 * @see List<LocalScene>
	 */
	List<LocalScene> selectByCloseTime(Date closeTime);

	/**
	 * 查询结束时在一定范围内的场景
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws
	 * @see Date
	 * @see Date
	 * @see List<LocalScene>
	 */
	List<LocalScene> selectBetweenEndTime(@Param("start_time") Date startTime,
			@Param("end_time") Date endTime);

	/**
	 * 查询由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	LocalScene selectBySceneId(@Param("sceneId") String sceneId);

	/**
	 * 查询都
	 *
	 * @return {@code List<LocalScene> }
	 * @throws
	 * @see List<LocalScene>
	 */
	List<LocalScene> selectAll();

	/**
	 * 查询通过业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene}
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	LocalScene selectByBusinessInstanceId(@Param("businessInstanceId") String businessInstanceId);
}
