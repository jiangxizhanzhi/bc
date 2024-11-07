package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估算法Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface EvalAlgorithmService extends IService<EvalAlgorithm> {

	/**
	 * 查询已使用的算法数量
	 *
	 * @param ids
	 * @return
	 */
	Integer selectUsedNum(List<String> ids);

	/**
	 * 批量删除算法
	 *
	 * @param ids
	 * @return
	 */
	Boolean delete(List<String> ids);

	/**
	 * 更新算法
	 *
	 * @param resourceId 资源id
	 */
	void updateStateById(String resourceId);

	/**
	 * 通过类型查询算法
	 *
	 * @param type
	 * @return
	 */
	List<EvalAlgorithm> selectByType(String type);

	/**
	 * 分页查询算法列表
	 *
	 * @param listQuery
	 * @return
	 */
	IPage<EvalAlgorithm> page(EvalListQuery listQuery);

	/**
	 * 查询已使用的算法列表
	 *
	 * @param algorithmIds
	 * @return
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	/**
	 * 更新已使用的算法状态为隐藏
	 *
	 * @param selectUsedAlgorithmIds 查询使用算法id列表
	 */
	void updateStateByIds(List<String> selectUsedAlgorithmIds);
}
