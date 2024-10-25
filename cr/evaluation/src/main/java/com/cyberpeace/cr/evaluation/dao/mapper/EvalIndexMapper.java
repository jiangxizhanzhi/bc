package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
@Mapper
public interface EvalIndexMapper extends BaseMapper<EvalIndex> {

    List<EvalIndex> selectListBySystemIdAndLevel(String systemId, Integer level);

    List<String> selectIdsByEvalSystemIds(List<String> evalSystemIds);

    void deleteBatchByEvalSystemId(String evalSystemId);

	Integer selectAlgorithmSum(List<String> algorithmIds);

	boolean existByName(String name, String resourceId);

	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




