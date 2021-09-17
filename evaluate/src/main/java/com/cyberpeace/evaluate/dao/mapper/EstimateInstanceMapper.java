package com.cyberpeace.evaluate.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.EstimateInstance;

@Mapper
public interface EstimateInstanceMapper extends BaseMapper<EstimateInstance> {

	@Override
	int insert(EstimateInstance estimateInstance);

	EstimateInstance getByEstimateIdAndStatus(@Param("estimateId") String estimateId,
			@Param("status") Integer status);

	void update(EstimateInstance estimateInstance);

	EstimateInstance getByInstanceId(@Param("instanceId") String instanceId);

	IPage<EstimateInstance> getByEstimateIdAndSave(Page page, @Param("estimateId") String estimateId,
												   @Param("save") Integer save, @Param("status") Integer status);

	Integer getCountByEstimateIdAndSave(@Param("estimateId") String estimateId,
			@Param("save") Integer save, @Param("status") Integer status);
}
