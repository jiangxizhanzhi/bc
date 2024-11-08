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
 * 评估算法Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalAlgorithmMapper extends BaseMapper<EvalAlgorithm> {

	/**
	 * 更新州由id
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
	 * 列出 eval 算法
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalAlgorithm> }
	 */
	IPage<EvalAlgorithm> listEvalAlgorithm(Page page, EvalListQuery listQuery);

	/**
	 * 更新州由id列表
	 *
	 * @param algorithmIds 算法id列表
	 */
	void updateStateByIds(List<String> algorithmIds);

	/**
	 * 获取都
	 *
	 * @return {@code List<EvalAlgorithm> }
	 */
	List<EvalAlgorithm> getAll();

	/**
	 * 全部删除
	 *
	 * @param algorithmIds 算法id列表
	 */
	void deleteAll(List<String> algorithmIds);

	/**
	 * 删除所有未使用备份
	 *
	 * @param algorithmIds 算法id列表
	 */
	void deleteAllUnUsedBackup(List<String> algorithmIds);
}




