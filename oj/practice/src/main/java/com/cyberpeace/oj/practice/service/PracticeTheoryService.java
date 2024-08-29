package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.practice.controller.req.WebTheoryRecordReq;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressPracticeRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryCountBo;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import java.util.List;

public interface PracticeTheoryService extends IService<PracticeTheory> {

	/**
	 * 分页查询理论基础练习题
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 */
	IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 用户根据编号查询题目详细信息
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	WebTheoryBo selectById(String resourceId, String userId, Boolean isCollected);

	/**
	 * 答案提交评估
	 * @param userId
	 * @param theoryId
	 * @param userAnswer
	 * @return
	 */
	EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer,
			Boolean isCollected);

	/**
	 * 理论基础习题新增
	 * @param practiceTheory
	 * @param authContext
	 * @return
	 */
	Integer create(PracticeTheory practiceTheory, AuthContext authContext);

	/**
	 * 理论基础习题修改
	 * @param practiceTheory
	 * @param authContext
	 * @return
	 */
	int updateTheory(PracticeTheory practiceTheory, AuthContext authContext);

	/**
	 * 获取所有理论习题编号列表
	 * @param authContext
	 * @return
	 */
	List<String> selectPracticeListIds(AuthContext authContext, String practiceSetId);

	/**
	 * 习题批量删除
	 * @param resourceIds
	 * @return
	 */
	Integer batchDelete(List<String> resourceIds);

	/**
	 * 加载理论习题数据
	 * @param practiceSetId
	 * @param dumpBo
	 * @param userId
	 * @return
	 */
	PracticeTheory loadPracticeTheory(String practiceSetId, PracticeTheoryDumpLoadBo dumpBo,
			String userId);

	/**
	 * 导出习题数据
	 * @param practiceTheory
	 * @return
	 */
	PracticeTheoryDumpLoadBo dumpTheory(PracticeTheory practiceTheory);

	/**
	 * 创建远程操作日志
	 * @param practiceId
	 * @param logContent
	 */
	void createMpLog(String practiceId, LogContent logContent);

	/**
	 * 查询理论习题各类型对应数量
	 * @param theoryListQueryBo
	 * @return
	 */
	ExternalTheoryCountBo typeCount(TheoryListQueryBo theoryListQueryBo, AuthContext authContext);

	MdTheoryPractice exportMdTheoryPractice(String path, List<PracticeTheory> theories);

	/**
	 * 统计做题进度
	 * @param practiceSetId
	 * @return
	 */
	ProgressStatisticsRes getProgressStatistics(String practiceSetId);

	/**
	 * 查询所有方向上的做题进度
	 * @return
	 */
	List<CategoryProgressRes> getProgressByCategory();

	/**
	 * 查询练习记录
	 * @param webTheoryRecordReq
	 * @return
	 */
	List<PracticeTheoryListBo> getPracticeRecord(WebTheoryRecordReq webTheoryRecordReq);

	List<String> getPracticeIdsByUserAndSet(AuthContext authContext, String practiceSetId);

	/**
	 * 查询题库中的所有的方向
	 * @return
	 */
	List<PracticeTheory> getCategoryIdAndSetIds();

	List<SolvedTop5> getSolvedTop5(String resourceId);

	List<ProgressPracticeRes> getProgressCategoryStatistics();

	/**
	 * 获取课程方向未做过的练习题
	 * @param categoryId
	 * @param userId
	 * @return
	 */
	List<PracticeTheoryListBo> getRecommend(String categoryId, String userId);


}
