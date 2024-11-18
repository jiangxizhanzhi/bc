package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估指示器Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
public interface EvalIndicatorMapper extends BaseMapper<EvalIndicator> {

	/**
	 * 查询算法和
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	Integer selectAlgorithmSum(List<String> algorithmIds);

	/**
	 * 列出评估指标
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalIndicator> }
	 * @throws
	 * @see Page
	 * @see EvalListQuery
	 * @see IPage<EvalIndicator>
	 */
	IPage<EvalIndicator> listEvalIndicator(Page page, EvalListQuery listQuery);

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
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	/**
	 * 查询指数使用数量
	 *
	 * @param ids id列表
	 * @return {@code Integer }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	Integer selectIndexUsedNum(List<String> ids);
}
