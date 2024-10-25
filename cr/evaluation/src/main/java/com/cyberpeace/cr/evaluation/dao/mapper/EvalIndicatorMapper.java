package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator
 */
@Mapper
public interface EvalIndicatorMapper extends BaseMapper<EvalIndicator> {

	Integer selectAlgorithmSum(List<String> algorithmIds);

    IPage<EvalIndicator> listEvalIndicator(Page page, EvalListQuery listQuery);

	boolean existByName(String name, String resourceId);

	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	Integer selectIndexUsedNum(List<String> ids);
}
