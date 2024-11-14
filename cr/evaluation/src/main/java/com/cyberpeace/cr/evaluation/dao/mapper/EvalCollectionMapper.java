package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCollectionContextRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;

/**
 * 测评收集Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface EvalCollectionMapper extends BaseMapper<EvalCollection> {

	/**
	 * 查询id列表由测评指示器id列表
	 *
	 * @param evalIndicatorIds 测评指示器id列表
	 * @return {@code List<EvalCollection> }
	 */
	List<EvalCollection> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	/**
	 * 查询算法和
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 */
	Integer selectAlgorithmSum(List<String> algorithmIds);

	/**
	 * 获取最大id
	 *
	 * @return {@code Integer }
	 */
	Integer getMaxId();

	/**
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	/**
	 * 查询byThird编号id
	 *
	 * @param indexId 编号id
	 * @return {@code List<EvalCollection> }
	 */
	List<EvalCollection> selectBythirdIndexId(String indexId);

	/**
	 * 查询由测评指示器id列表
	 *
	 * @param evalIndicatorIds 测评指示器id列表
	 * @return {@code List<EvalCollectionContextRsp> }
	 */
	List<EvalCollectionContextRsp> selectByEvalIndicatorIds(List<String> evalIndicatorIds);
}




