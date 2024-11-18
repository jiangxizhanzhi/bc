package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评估算法Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface EvalAlgorithmMapper extends BaseMapper<EvalAlgorithm> {

	/**
	 * 更新州由id
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	void updateStateById(String resourceId);

	/**
	 * 查询由类型
	 *
	 * @param type 类型
	 * @return {@code List<EvalAlgorithm> }
	 * @throws
	 * @see String
	 * @see List<EvalAlgorithm>
	 */
	List<EvalAlgorithm> selectByType(String type);

	/**
	 * 存在由名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 * @throws
	 * @see String
	 * @see String
	 * @see boolean
	 */
	boolean existByName(String name, String resourceId);

	/**
	 * 列出 eval 算法
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalAlgorithm> }
	 * @throws
	 * @see Page
	 * @see EvalListQuery
	 * @see IPage<EvalAlgorithm>
	 */
	IPage<EvalAlgorithm> listEvalAlgorithm(Page page, EvalListQuery listQuery);

	/**
	 * 更新州由id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @throws
	 * @see List<String>
	 */
	void updateStateByIds(List<String> algorithmIds);

	/**
	 * 获取都
	 *
	 * @return {@code List<EvalAlgorithm> }
	 * @throws
	 * @see List<EvalAlgorithm>
	 */
	List<EvalAlgorithm> getAll();

	/**
	 * 全部删除
	 *
	 * @param algorithmIds 算法id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteAll(List<String> algorithmIds);

	/**
	 * 删除所有未使用备份
	 *
	 * @param algorithmIds 算法id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteAllUnUsedBackup(List<String> algorithmIds);
}




