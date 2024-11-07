package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;

/**
 * 靶场徽章映射
 *
 * @author SN
 * @date 2024/11/07
 */
@Repository
@Mapper
public interface CrBadgeMapper extends BaseMapper<CrBadge> {

	/**
	 * 插入批量
	 *
	 * @param badgeList 徽章列表
	 */
	void insertBatch(@Param("badgeList") List<CrBadge> badgeList);

	/**
	 * 存在由业务id和类型和用户id
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userId     用户id
	 * @return {@code Boolean }
	 */
	Boolean existByBusinessIdAndTypeAndUserId(@Param("businessId") String businessId,
			@Param("type") Integer type, @Param("userId") String userId);

	/**
	 * 删除批量由业务id和类型和用户id
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userId     用户id
	 */
	void removeBatchByBusinessIdAndTypeAndUserId(@Param("businessId") String businessId,
			@Param("type") Integer type, @Param("userId") String userId);
}
