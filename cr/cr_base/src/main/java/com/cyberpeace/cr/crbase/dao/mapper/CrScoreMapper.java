package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;

/**
 * 靶场得分Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrScoreMapper extends BaseMapper<CrScore> {

	/**
	 * 获取由房间id和用户
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code CrScore }
	 */
	CrScore getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 查询由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<CrScore> }
	 */
	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrScore> }
	 */
	List<CrScore> selectByRoomId(@Param("roomId") String roomId);
}
