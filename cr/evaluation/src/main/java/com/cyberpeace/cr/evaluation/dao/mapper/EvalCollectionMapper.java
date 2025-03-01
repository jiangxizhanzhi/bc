package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCollectionContextRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalCollection
 */
@Mapper
@Repository
public interface EvalCollectionMapper extends BaseMapper<EvalCollection> {

	List<EvalCollection> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	Integer selectAlgorithmSum(List<String> algorithmIds);

	Integer getMaxId();

	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	List<EvalCollection> selectBythirdIndexId(String indexId);

	List<EvalCollectionContextRsp> selectByEvalIndicatorIds(List<String> evalIndicatorIds);

	List<EvalCollection> selectByEvalIndicators(List<String> evalIndicatorIds);

	List<EvalCollection> selectByIds(List<String> resourceIds);
}




