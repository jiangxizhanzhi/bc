package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 测评算法Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface EvalAlgorithmMapper extends BaseMapper<EvalAlgorithm> {

	/**
	 * 更新状态由id
	 *
	 * @param resourceId 资源id
	 */
	void updateStateById(String resourceId);

	/**
	 * 查询由类型
	 *
	 * @param type 类型
	 * @return {@code List<EvalAlgorithm> }
	 */
	List<EvalAlgorithm> selectByType(String type);

	/**
	 * 存在由名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean existByName(String name, String resourceId);

	/**
	 * 列表测评算法
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalAlgorithm> }
	 */
	IPage<EvalAlgorithm> listEvalAlgorithm(Page<EvalAlgorithm> page, EvalListQuery listQuery);

	/**
	 * 更新状态由id列表
	 *
	 * @param algorithmIds 算法id列表
	 */
	void updateStateByIds(List<String> algorithmIds);

	/**
	 * 获取全部
	 *
	 * @return {@code List<EvalAlgorithm> }
	 */
	List<EvalAlgorithm> getAll();

	/**
	 * 删除全部
	 *
	 * @param algorithmIds 算法id列表
	 */
	void deleteAll(List<String> algorithmIds);

	/**
	 * 删除全部联合国使用备份
	 *
	 * @param algorithmIds 算法id列表
	 */
	void deleteAllUnUsedBackup(List<String> algorithmIds);
}




