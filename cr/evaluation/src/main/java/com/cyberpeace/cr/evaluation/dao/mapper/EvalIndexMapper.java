package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
@Mapper
@Repository
public interface EvalIndexMapper extends BaseMapper<EvalIndex> {

    List<EvalIndex> selectListBySystemIdAndLevel(String systemId, Integer level);

    List<String> selectIdsByEvalSystemIds(List<String> evalSystemIds);

    void deleteBatchByEvalSystemId(String evalSystemId);

	Integer selectAlgorithmSum(List<String> algorithmIds);

	boolean existByName(String name, String resourceId);

	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




