package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;

@Repository
@Mapper
public interface CrBadgeMapper extends BaseMapper<CrBadge> {

	void insertBatch(@Param("badgeList") List<CrBadge> badgeList);

	Boolean existByBusinessIdAndTypeAndUserId(@Param("businessId") String businessId,
			@Param("type") Integer type, @Param("userId") String userId);

	void removeBatchByBusinessIdAndTypeAndUserId(@Param("businessId") String businessId,
			@Param("type") Integer type, @Param("userId") String userId);
}
