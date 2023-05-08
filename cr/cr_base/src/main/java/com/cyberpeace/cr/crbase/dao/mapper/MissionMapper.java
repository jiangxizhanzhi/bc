package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

/**
 * 任务映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface MissionMapper extends BaseMapper<Mission> {

	/**
	 * 批量获取
	 *
	 * @param resourceIds 资源id
	 * @return {@link List}<{@link Mission}>
	 */
	List<Mission> batchSelect(List<String> resourceIds);

	/**
	 * 通过房间id获取
	 *
	 * @param roomId 房间id
	 * @return {@link List}<{@link Mission}>
	 */
	List<Mission> selectByRoomId(String roomId);

	/**
	 * 批量插入
	 *
	 * @param missions 任务
	 */
	void batchInsert(List<Mission> missions);

	/**
	 * 根据标题和ids判断存在
	 *
	 * @param title      标题
	 * @param missionIds 任务id
	 * @return {@link Boolean}
	 */
	Boolean existByTitleAndIds(@Param("title") String title,
			@Param("missionIds") List<String> missionIds);
}
