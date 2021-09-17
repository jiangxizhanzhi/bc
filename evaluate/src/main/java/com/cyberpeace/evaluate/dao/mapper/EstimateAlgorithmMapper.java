package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;

@Mapper
public interface EstimateAlgorithmMapper extends BaseMapper<EstimateAlgorithm> {

	@Override
	int insert(EstimateAlgorithm estimateAlgorithm);

	/**
	 * 跟据类型查询所有算法数据
	 * @return
	 */
	List<EstimateAlgorithm> selectByType(@Param("algorithmType") String algorithmType);

    void deleteById(String algorithmId);

	EstimateAlgorithm selectById(String algorithmId);

	IPage<EstimateAlgorithm> selectPageByType(Page page, @Param("algorithmType") String algorithmType);
}
