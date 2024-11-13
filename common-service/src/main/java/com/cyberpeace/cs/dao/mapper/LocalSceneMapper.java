package com.cyberpeace.cs.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 当地场景Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	/**
	 * 获取由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene }
	 */
	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 修改销毁时间 空数据也修改
	 *
	 * @param localScene 当地场景
	 */
	void updateEndTime(LocalScene localScene);

	/**
	 * 查询到关闭时间的场景
	 *
	 * @param closeTime
	 * @return
	 */
	List<LocalScene> selectByCloseTime(Date closeTime);

	/**
	 * 查询结束时在一定范围内的场景
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<LocalScene> selectBetweenEndTime(@Param("start_time") Date startTime,
			@Param("end_time") Date endTime);

	/**
	 * 查询由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code LocalScene }
	 */
	LocalScene selectBySceneId(@Param("sceneId") String sceneId);

	/**
	 * 查询自删除
	 *
	 * @param businessCodes 业务代码
	 * @return {@code List<LocalScene> }
	 */
	List<LocalScene> selectToDelete(@Param("businessCodes") List<Integer> businessCodes);

	/**
	 * 查询通过业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene}
	 */
	LocalScene selectByBusinessInstanceId(@Param("businessInstanceId") String businessInstanceId);
}
