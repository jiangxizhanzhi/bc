package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCollectionContextRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;

/**
 * 评估收集Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
public interface EvalCollectionMapper extends BaseMapper<EvalCollection> {

	/**
	 * 查询id列表由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
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
	 * 获取maxid
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
	 * 查询byThird指数id
	 *
	 * @param indexId 指数id
	 * @return {@code List<EvalCollection> }
	 */
	List<EvalCollection> selectBythirdIndexId(String indexId);

	/**
	 * 查询由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 * @return {@code List<EvalCollectionContextRsp> }
	 */
	List<EvalCollectionContextRsp> selectByEvalIndicatorIds(List<String> evalIndicatorIds);
}




