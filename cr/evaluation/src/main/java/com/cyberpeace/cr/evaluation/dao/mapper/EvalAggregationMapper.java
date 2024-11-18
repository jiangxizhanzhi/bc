package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;

/**
 * 评估集合体Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
public interface EvalAggregationMapper extends BaseMapper<EvalAggregation> {

	/**
	 * 查询id列表由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 * @return {@code List<EvalAggregation> }
	 * @throws
	 * @see List<String>
	 * @see List<EvalAggregation>
	 */
	List<EvalAggregation> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	/**
	 * 查询算法数量
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	Integer selectAlgorithmNum(List<String> algorithmIds);

	/**
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




