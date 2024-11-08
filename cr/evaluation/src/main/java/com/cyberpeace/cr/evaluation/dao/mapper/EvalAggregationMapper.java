package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 评估集合体Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface EvalAggregationMapper extends BaseMapper<EvalAggregation> {

	/**
	 * 查询id列表由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 * @return {@code List<EvalAggregation> }
	 */
	List<EvalAggregation> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	/**
	 * 查询算法数量
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 */
	Integer selectAlgorithmNum(List<String> algorithmIds);

	/**
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




