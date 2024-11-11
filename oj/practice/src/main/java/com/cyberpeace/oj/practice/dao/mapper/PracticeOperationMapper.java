package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;

/**
 * 练习实操Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
public interface PracticeOperationMapper extends BaseMapper<PracticeOperation> {

	/**
	 * 分页由查询
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @param isPublic  是公共
	 * @return {@code IPage<PracticeOperation> }
	 */
	IPage<PracticeOperation> pageByQuery(@Param("page") Page page,
			@Param("listQuery") OperationListQuery listQuery, @Param("isPublic") Boolean isPublic);

	/**
	 * 查询由设置id列表
	 *
	 * @param setIds   设置id列表
	 * @param isPublic 是公共
	 * @return {@code List<String> }
	 */
	List<String> selectBySetIds(@Param("setIds") List<String> setIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 查询由类别id列表
	 *
	 * @param categoryIds 类别id列表
	 * @param isPublic    是公共
	 * @return {@code List<String> }
	 */
	List<String> selectByCategoryIds(@Param("categoryIds") List<String> categoryIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 随机获取实操题
	 *
	 * @param listQuery
	 * @param count
	 * @return
	 */
	List<PracticeOperation> randomChoice(@Param("query") OperationListQuery listQuery,
			@Param("limit") Integer count);

	/**
	 * 根据习题集和标题查询数据
	 *
	 * @param practiceSetId
	 * @param title
	 * @return
	 */
	String selectByTitle(@Param("practiceSetId") String practiceSetId,
			@Param("title") String title);

	/**
	 * 拓扑id列表由id列表
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code List<String> }
	 */
	List<String> topoIdsByIds(@Param("resource_ids") List<String> resourceIds);

	/**
	 * 按查询列出
	 *
	 * @param listQuery 列表查询
	 * @param isPublic  是公共
	 * @return {@code List<PracticeOperation> }
	 */
	List<PracticeOperation> listByQuery(@Param("listQuery") OperationListQuery listQuery,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 搜索内置由设置id列表
	 *
	 * @param setIds 设置id列表
	 * @return {@code String }
	 */
	String searchBuiltinBySetIds(@Param("setIds") List<String> setIds);

	/**
	 * 查询都类别id列表
	 *
	 * @return {@code List<PracticeOperation> }
	 */
	List<PracticeOperation> selectAllCategoryIds();
}
