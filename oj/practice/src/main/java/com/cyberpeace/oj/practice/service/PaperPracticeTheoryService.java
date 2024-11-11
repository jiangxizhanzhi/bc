package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import com.cyberpeace.oj.practice.service.bo.EvaluateResultTheory;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

import java.util.List;

/**
 * 试卷练习理论Service
 *
 * @author SN
 * @date 2024/11/11
 */
public interface PaperPracticeTheoryService extends IService<PaperPracticeTheory> {

	/**
	 * 获取练习详情
	 *
	 * @param practiceRefId 练习裁判id
	 * @return {@code WebTheoryBo }
	 */
	WebTheoryBo getPracticeDetail(String practiceRefId);

	/**
	 * 提交答案
	 *
	 * @param practiceId   练习id
	 * @param submitAnswer 提交答案
	 * @return {@code EvaluateResultTheory }
	 */
	EvaluateResultTheory submitAnswer(String practiceId, String submitAnswer);

	/**
	 * 收集
	 *
	 * @param collectionReq 收集请求体
	 * @return {@code Boolean }
	 */
	Boolean collection(CollectionReq collectionReq);

	/**
	 * 获取练习详情
	 *
	 * @param practiceRefIds 练习裁判id列表
	 * @return {@code List<WebTheoryBo> }
	 */
	List<WebTheoryBo> getPracticeDetail(List<String> practiceRefIds);
}
