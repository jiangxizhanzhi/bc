package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;

@Mapper
public interface PracticeOperationMapper extends BaseMapper<PracticeOperation> {

	IPage<PracticeOperation> pageByQuery(@Param("page") Page page,
			@Param("listQuery") OperationListQuery listQuery, @Param("isPublic") Boolean isPublic);

	List<String> selectBySetIds(@Param("setIds") List<String> setIds,
			@Param("isPublic") Boolean isPublic);

	List<String> selectByCategoryIds(@Param("categoryIds") List<String> categoryIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 随机获取实操题
	 * @param listQuery
	 * @param count
	 * @return
	 */
	List<PracticeOperation> randomChoice(@Param("query") OperationListQuery listQuery,
			@Param("limit") Integer count);

	/**
	 * 根据习题集和标题查询数据
	 * @param practiceSetId
	 * @param title
	 * @return
	 */
	String selectByTitle(@Param("practiceSetId") String practiceSetId,
			@Param("title") String title);

	List<String> topoIdsByIds(@Param("resource_ids") List<String> resourceIds);

	List<PracticeOperation> listByQuery(@Param("listQuery") OperationListQuery listQuery,
			@Param("isPublic") Boolean isPublic);

	String searchBuiltinBySetIds(@Param("setIds") List<String> setIds);
}
