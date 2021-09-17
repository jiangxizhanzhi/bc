package com.cyberpeace.evaluate.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.EstimateInfo;

@Mapper
public interface EstimateInfoMapper {

	void insert(EstimateInfo estimateInfo);

	EstimateInfo getByEstimateId(@Param("estimateId") String estimateId);

	/**
	 * 查询用户创建的评估数量
	 * @param userId
	 * @return Integer
	 * */
	Integer getCountByUserId(@Param("userId") String userId);

	void deleteById(@Param("estimateId")String estimateId);

	IPage<EstimateInfo> pageByUserId(Page page, @Param("userId") String userId);
}
