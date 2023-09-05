package com.cyberpeace.oj.practice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.ojbase.service.bo.check.CheckPointQuestion;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.ProgressProportion;
import com.cyberpeace.oj.practice.service.externalbo.TheorySimpleBo;

@Mapper
@Repository
public interface PracticeTheoryMapper extends BaseMapper<PracticeTheory> {

	/**
	 * 分页查询理论基础习题 #外部接口使用
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
	 * @param theoryListQuery
	 * @param count
	 * @return
	 */
	List<PracticeTheory> randomChoice(@Param("query") TheoryListQuery theoryListQuery,
			@Param("limit") Integer count);

	/**
	 * 根据编号查询理论习题 #外部接口使用
	 * @param resourceIds
	 * @return
	 */
	List<PracticeTheory> batchGet(@Param("resource_ids") List<String> resourceIds);

	IPage<PracticeTheory> pageByQuery(Page page,
			@Param("listQuery") TheoryListQuery theoryListQuery,
			@Param("isPublic") Boolean isPublic);

	List<String> selectBySetIds(@Param("setIds") List<String> setIds,
			@Param("isPublic") Boolean isPublic);

	List<String> selectByCategoryIds(@Param("categoryIds") List<String> categoryIds,
			@Param("isPublic") Boolean isPublic);

	String selectByContent(
			@Param("practiceSetId") String practiceSetId, @Param("content") String content);

	List<TheorySimpleBo> selectSimple(@Param("query") TheoryListQuery theoryListQuery,
			@Param("isPublic") Boolean isPublic);

	String searchBuiltinBySetIds(@Param("setIds") List<String> setIds);

	List<PracticeTheory> selectAllByCreatePaper(@Param("difficulties") List<Integer> difficulties,
												@Param("types") List<Integer> types,
												@Param("directions") List<String> directions,
												@Param("practiceIds") List<String> practiceIds);

	List<CategoryProgressRes> selectByGroupCategory(@Param("listQuery") TheoryListQuery theoryListQuery,
											@Param("isPublic") Boolean isPublic,
											@Param("userId") String userId,
											@Param("authSetIds")List<String> authSetIds,
											@Param("practiceType") Integer practiceType);

	List<ProgressProportion> selectByGroupDifficulty(@Param("listQuery") TheoryListQuery theoryListQuery,
													 @Param("isPublic") Boolean isPublic,
													 @Param("userId") String userId,
													 @Param("authSetIds")List<String> authSetIds,
													 @Param("practiceType") Integer practiceType);

	/**
	 * 查询现有题目的方向id和习题集id
	 * @return
	 */
	List<PracticeTheory> selectAllCategoryIds();

	List<CheckPointQuestion>
			getCheckPointQuestionsByIds(@Param("theoryIds") List<String> theoryIds);

	/**
	 * 获取单选、多选、判断
	 *
	 * @param page           分页
	 * @param pattern
	 * @param practiceSetIds practice设置id列表
	 * @param category
	 * @param questionType
	 * @param difficulty
	 * @return {@code IPage<PracticeTheory>}
	 */
	IPage<PracticeTheory> getSingleMultiJudgeList(Page page, @Param("pattern") String pattern,
			@Param("practice_set_ids") List<String> practiceSetIds,
			@Param("category") String category, @Param("question_type") Integer questionType,
			@Param("difficulty") Integer difficulty);
}
