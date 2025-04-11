package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.practice.controller.req.WebTheoryRecordReq;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryBo;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryCountBo;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import java.io.File;
import java.util.List;

/**
 * 实践理论Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PracticeTheoryService extends IService<PracticeTheory> {

	/**
	 * 分页查询理论基础练习题
	 *
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 * @throws
	 * @see Page
	 * @see TheoryListQueryBo
	 * @see AuthContext
	 * @see IPage<PracticeTheoryListBo>
	 */
	IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 用户根据编号查询题目详细信息
	 *
	 * @param resourceId
	 * @param userId
	 * @param isCollected 被收集
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see WebTheoryBo
	 */
	WebTheoryBo selectById(String resourceId, String userId, Boolean isCollected);

	/**
	 * 答案提交评估
	 *
	 * @param userId
	 * @param theoryId
	 * @param userAnswer
	 * @param isCollected 被收集
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see EvaluateResult
	 */
	EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer,
			Boolean isCollected);

	/**
	 * 理论基础习题新增
	 *
	 * @param practiceTheory
	 * @param authContext
	 * @return
	 * @throws
	 * @see PracticeTheory
	 * @see AuthContext
	 * @see Integer
	 */
	Integer create(PracticeTheory practiceTheory, AuthContext authContext);

	/**
	 * 理论基础习题修改
	 *
	 * @param practiceTheory
	 * @param authContext
	 * @return
	 * @throws
	 * @see PracticeTheory
	 * @see AuthContext
	 * @see int
	 */
	int updateTheory(PracticeTheory practiceTheory, AuthContext authContext);

	/**
	 * 获取所有理论习题编号列表
	 *
	 * @param authContext
	 * @param practiceSetId 实践设置id
	 * @return
	 * @throws
	 * @see AuthContext
	 * @see String
	 * @see List<String>
	 */
	List<String> selectPracticeListIds(AuthContext authContext, String practiceSetId);

	/**
	 * 习题批量删除
	 *
	 * @param resourceIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	Integer batchDelete(List<String> resourceIds);

	/**
	 * 加载理论习题数据
	 *
	 * @param practiceSetId
	 * @param dumpBo
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeTheoryDumpLoadBo
	 * @see String
	 * @see PracticeTheory
	 */
	PracticeTheory loadPracticeTheory(String practiceSetId, PracticeTheoryDumpLoadBo dumpBo,
			String userId);

	/**
	 * 导出习题数据
	 *
	 * @param practiceTheory
	 * @return
	 * @throws
	 * @see PracticeTheory
	 * @see PracticeTheoryDumpLoadBo
	 */
	PracticeTheoryDumpLoadBo dumpTheory(PracticeTheory practiceTheory);

	/**
	 * 创建远程操作日志
	 *
	 * @param practiceId
	 * @param logContent 日志内容
	 * @throws
	 * @see String
	 * @see LogContent
	 */
	void createMpLog(String practiceId, LogContent logContent);

	/**
	 * 查询理论习题各类型对应数量
	 *
	 * @param theoryListQueryBo
	 * @param authContext       auth 上下文
	 * @return
	 * @throws
	 * @see TheoryListQueryBo
	 * @see AuthContext
	 * @see ExternalTheoryCountBo
	 */
	ExternalTheoryCountBo typeCount(TheoryListQueryBo theoryListQueryBo, AuthContext authContext);

	/**
	 * 导出Markdown理论实践
	 *
	 * @param path     路径
	 * @param theories 理论
	 * @return {@code MdTheoryPractice }
	 * @throws
	 * @see String
	 * @see List<PracticeTheory>
	 * @see MdTheoryPractice
	 */
	MdTheoryPractice exportMdTheoryPractice(String path, List<PracticeTheory> theories);

	/**
	 * 统计做题进度
	 *
	 * @param practiceSetId
	 * @return
	 * @throws
	 * @see String
	 * @see ProgressStatisticsRes
	 */
	ProgressStatisticsRes getProgressStatistics(String practiceSetId);

	/**
	 * 查询所有方向上的做题进度
	 *
	 * @return
	 * @throws
	 * @see List<CategoryProgressRes>
	 */
	List<CategoryProgressRes> getProgressByCategory();

	/**
	 * 查询练习记录
	 *
	 * @param webTheoryRecordReq
	 * @return
	 * @throws
	 * @see WebTheoryRecordReq
	 * @see List<PracticeTheoryListBo>
	 */
	List<PracticeTheoryListBo> getPracticeRecord(WebTheoryRecordReq webTheoryRecordReq);

	/**
	 * 获取实践id列表由用户和设置
	 *
	 * @param authContext   auth 上下文
	 * @param practiceSetId 实践设置id
	 * @return {@code List<String> }
	 * @throws
	 * @see AuthContext
	 * @see String
	 * @see List<String>
	 */
	List<String> getPracticeIdsByUserAndSet(AuthContext authContext, String practiceSetId);

	/**
	 * 查询题库中的所有的方向
	 *
	 * @return
	 * @throws
	 * @see List<PracticeTheory>
	 */
	List<PracticeTheory> getCategoryIdAndSetIds();

	/**
	 * 分页由查询
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<ExternalTheoryBo> }
	 * @throws
	 * @see Page
	 * @see TheoryListQueryBo
	 * @see IPage<ExternalTheoryBo>
	 */
	IPage<ExternalTheoryBo> pageByQuery(Page page, TheoryListQueryBo listQuery);

	/**
	 * 导出批量
	 *
	 * @param ids id列表
	 * @return {@code File }
	 * @throws
	 * @see List<String>
	 * @see File
	 */
	File dumpBatch(List<String> ids);

    Boolean loadBatch(String realPath, String userId);

	/**
	 * 导出批量西尔姆
	 *
	 * @param ids id列表
	 * @return {@code File }
	 * @throws
	 * @see List<String>
	 * @see File
	 */
	File dumpBatchSlim(List<String> ids);

	/**
	 * 加载批量简洁
	 *
	 * @param realPath 真实路径
	 * @param userId   用户id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean loadBatchSlim(String realPath, String userId);
}
