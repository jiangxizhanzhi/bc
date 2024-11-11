package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.ProgressProportion;
import com.cyberpeace.oj.practice.service.externalbo.TheorySimpleBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 练习理论Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface PracticeTheoryMapper extends BaseMapper<PracticeTheory> {

	/**
	 * 分页查询理论基础习题 #外部接口使用
	 *
	 * @param page
	 * @param pattern
	 * @param practiceSetIds
	 * @param category
	 * @param questionType
	 * @param difficulty
	 * @return
	 */
	IPage<PracticeTheory> getList(Page page, @Param("pattern") String pattern,
			@Param("practice_set_ids") List<String> practiceSetIds, @Param("category") String category,
			@Param("question_type") Integer questionType, @Param("difficulty") Integer difficulty);

	/**
	 * 随机查询理论习题 #外部接口使用
	 *
	 * @param theoryListQuery
	 * @param count
	 * @return
	 */
	List<PracticeTheory> randomChoice(@Param("query") TheoryListQuery theoryListQuery,
			@Param("limit") Integer count);

	/**
	 * 根据编号查询理论习题 #外部接口使用
	 *
	 * @param resourceIds
	 * @return
	 */
	List<PracticeTheory> batchGet(@Param("resource_ids") List<String> resourceIds);

	/**
	 * 分页由查询
	 *
	 * @param page            分页
	 * @param theoryListQuery 理论列表查询
	 * @param isPublic        是公共
	 * @return {@code IPage<PracticeTheory> }
	 */
	IPage<PracticeTheory> pageByQuery(Page page,
			@Param("listQuery") TheoryListQuery theoryListQuery,
			@Param("isPublic") Boolean isPublic);

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
	 * 查询由内容
	 *
	 * @param practiceSetId 练习设置id
	 * @param content       内容
	 * @return {@code String }
	 */
	String selectByContent(
			@Param("practiceSetId") String practiceSetId, @Param("content") String content);

	/**
	 * 查询简单
	 *
	 * @param theoryListQuery 理论列表查询
	 * @param isPublic        是公共
	 * @return {@code List<TheorySimpleBo> }
	 */
	List<TheorySimpleBo> selectSimple(@Param("query") TheoryListQuery theoryListQuery,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 搜索内置由设置id列表
	 *
	 * @param setIds 设置id列表
	 * @return {@code String }
	 */
	String searchBuiltinBySetIds(@Param("setIds") List<String> setIds);

	/**
	 * 查询都由创造试卷
	 *
	 * @param difficulties 困难
	 * @param types        类型
	 * @param directions   方向
	 * @param practiceIds  练习id列表
	 * @return {@code List<PracticeTheory> }
	 */
	List<PracticeTheory> selectAllByCreatePaper(@Param("difficulties") List<Integer> difficulties,
												@Param("types") List<Integer> types,
												@Param("directions") List<String> directions,
												@Param("practiceIds") List<String> practiceIds);

	/**
	 * 查询由群类别
	 *
	 * @param theoryListQuery 理论列表查询
	 * @param isPublic        是公共
	 * @param userId          用户id
	 * @param authSetIds      认证设置id列表
	 * @param practiceType    练习类型
	 * @return {@code List<CategoryProgressRes> }
	 */
	List<CategoryProgressRes> selectByGroupCategory(@Param("listQuery") TheoryListQuery theoryListQuery,
											@Param("isPublic") Boolean isPublic,
											@Param("userId") String userId,
											@Param("authSetIds")List<String> authSetIds,
											@Param("practiceType") Integer practiceType);

	/**
	 * 查询由群困难
	 *
	 * @param theoryListQuery 理论列表查询
	 * @param isPublic        是公共
	 * @param userId          用户id
	 * @param authSetIds      认证设置id列表
	 * @param practiceType    练习类型
	 * @return {@code List<ProgressProportion> }
	 */
	List<ProgressProportion> selectByGroupDifficulty(@Param("listQuery") TheoryListQuery theoryListQuery,
													 @Param("isPublic") Boolean isPublic,
													 @Param("userId") String userId,
													 @Param("authSetIds")List<String> authSetIds,
													 @Param("practiceType") Integer practiceType);

	/**
	 * 查询现有题目的方向id和习题集id
	 *
	 * @return
	 */
	List<PracticeTheory> selectAllCategoryIds();
}
