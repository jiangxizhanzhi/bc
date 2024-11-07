package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估算法Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class EvalAlgorithmServiceImpl extends ServiceImpl<EvalAlgorithmMapper, EvalAlgorithm> implements EvalAlgorithmService {

	/**
	 * 评估算法Mapper
	 */
	@Autowired
	EvalAlgorithmMapper evalAlgorithmMapper;

	/**
	 * 评估集合体Mapper
	 */
	@Autowired
	EvalAggregationMapper evalAggregationMapper;

	/**
	 * 评估收集Mapper
	 */
	@Autowired
	EvalCollectionMapper evalCollectionMapper;

	/**
	 * 评估指数Mapper
	 */
	@Autowired
	EvalIndexMapper evalIndexMapper;

	/**
	 * 评估指示器Mapper
	 */
	@Autowired
	EvalIndicatorMapper evalIndicatorMapper;

	/**
	 * 查询使用数量
	 *
	 * @param ids id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer selectUsedNum(List<String> ids) {
		Integer num = evalAggregationMapper.selectAlgorithmNum(ids)
				+ evalCollectionMapper.selectAlgorithmSum(ids)
				+ evalIndexMapper.selectAlgorithmSum(ids)
				+ evalIndicatorMapper.selectAlgorithmSum(ids);
		return num;
	}

	/**
	 * 删除
	 *
	 * @param ids id列表
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean delete(List<String> ids) {

		evalAlgorithmMapper.deleteBatchIds(ids);
		return true;
	}

	/**
	 * 更新州由id
	 *
	 * @param resourceId 资源id
	 */
	@Override
	public void updateStateById(String resourceId) {
		evalAlgorithmMapper.updateStateById(resourceId);
	}

	/**
	 * 查询由类型
	 *
	 * @param type 类型
	 * @return {@code List<EvalAlgorithm> }
	 */
	@Override
	public List<EvalAlgorithm> selectByType(String type) {
		return evalAlgorithmMapper.selectByType(type);
	}

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalAlgorithm> }
	 */
	@Override
	public IPage<EvalAlgorithm> page(EvalListQuery listQuery) {

		Page page = listQuery.toPageParam();

        return evalAlgorithmMapper.listEvalAlgorithm(page, listQuery);
	}

	/**
	 * 查询使用算法id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> selectUsedAlgorithmIds(List<String> ids) {
		List<String> usedAlgorithmIds = evalAggregationMapper.selectUsedAlgorithmIds(ids);

		usedAlgorithmIds.addAll(evalCollectionMapper.selectUsedAlgorithmIds(ids));
		usedAlgorithmIds.addAll(evalIndexMapper.selectUsedAlgorithmIds(ids));
		usedAlgorithmIds.addAll(evalIndicatorMapper.selectUsedAlgorithmIds(ids));

		return usedAlgorithmIds.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 更新州由id列表
	 *
	 * @param algorithmIds 算法id列表
	 */
	@Override
	public void updateStateByIds(List<String> algorithmIds) {
		evalAlgorithmMapper.updateStateByIds(algorithmIds);
	}
}
