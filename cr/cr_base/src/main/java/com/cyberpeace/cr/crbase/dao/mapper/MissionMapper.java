package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

/**
 * 任务Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface MissionMapper extends BaseMapper<Mission> {

	/**
	 * 批量查询
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code List<Mission> }
	 */
	List<Mission> batchSelect(List<String> resourceIds);

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<Mission> }
	 */
	List<Mission> selectByRoomId(String roomId);

	/**
	 * 批量插入
	 *
	 * @param missions 任务
	 */
	void batchInsert(List<Mission> missions);

	/**
	 * 存在由标题和id列表
	 *
	 * @param title      标题
	 * @param missionIds 任务id列表
	 * @return {@code Boolean }
	 */
	Boolean existByTitleAndIds(@Param("title") String title,
			@Param("missionIds") List<String> missionIds);
}
