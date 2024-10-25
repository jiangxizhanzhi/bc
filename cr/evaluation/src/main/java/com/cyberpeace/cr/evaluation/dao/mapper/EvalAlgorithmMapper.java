package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm
 */
@Mapper
@Repository
public interface EvalAlgorithmMapper extends BaseMapper<EvalAlgorithm> {

	void updateStateById(String resourceId);

	List<EvalAlgorithm> selectByType(String type);

	boolean existByName(String name, String resourceId);

	IPage<EvalAlgorithm> listEvalAlgorithm(Page page, EvalListQuery listQuery);

	void updateStateByIds(List<String> algorithmIds);

	List<EvalAlgorithm> getAll();

	void deleteAll(List<String> algorithmIds);

	void deleteAllUnUsedBackup(List<String> algorithmIds);
}




