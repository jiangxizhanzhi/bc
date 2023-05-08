package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;

/**cr得分映射器
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.CrScore
 @author cyberpeace
 @date 2023/05/06
 */
@Mapper
@Repository
public interface CrScoreMapper extends BaseMapper<CrScore> {

	/**
	 * 通过房间id和用户获取
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link CrScore}
	 */
	CrScore getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 通过房间id和用户id获取
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id
	 * @return {@link List}<{@link CrScore}>
	 */
	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

}
