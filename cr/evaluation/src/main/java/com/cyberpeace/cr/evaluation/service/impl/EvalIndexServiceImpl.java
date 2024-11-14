package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 * 测评编号Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class EvalIndexServiceImpl extends ServiceImpl<EvalIndexMapper, EvalIndex> implements EvalIndexService {

	/**
	 * 测评编号Mapper
	 */
	@Autowired
	private EvalIndexMapper evalIndexMapper;

	/**
	 * 获取指标由测评系统id
	 *
	 * @param systemId 系统id
	 * @param level    水平
	 * @return {@code List<IndexContext> }
	 */
	@Override
	public List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level) {

		List<EvalIndex> evalIndexList = evalIndexMapper.selectListBySystemIdAndLevel(systemId,
				level);

		return evalIndexList.stream().map(evalIndex -> {
			IndexContext indexContext = BeanUtils.copy(evalIndex, IndexContext.class);

			indexContext.setIndexId(evalIndex.getResourceId());
			indexContext.setChildrenIds(
					JsonUtil.toJsonObjectArray(evalIndex.getChildrenIds(), String.class));
			indexContext.setAlgorithmId(evalIndex.getAggregatingAlgorithmId());
			indexContext.setParams(evalIndex.getAggregatingAlgorithmParams());

			return indexContext;
		}).collect(Collectors.toList());
	}

	/**
	 * 删除批量由测评系统id
	 *
	 * @param evalSystemId 测评系统id
	 */
	@Override
	public void deleteBatchByEvalSystemId(String evalSystemId) {
		evalIndexMapper.deleteBatchByEvalSystemId(evalSystemId);
	}
}
