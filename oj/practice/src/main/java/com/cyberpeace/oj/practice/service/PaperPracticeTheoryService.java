package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import com.cyberpeace.oj.practice.service.bo.EvaluateResultTheory;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

import java.util.List;

/**
 * 纸实践理论Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface PaperPracticeTheoryService extends IService<PaperPracticeTheory> {

	/**
	 * 获取实践详情
	 *
	 * @param practiceRefId 实践裁判id
	 * @return {@code WebTheoryBo }
	 */
	WebTheoryBo getPracticeDetail(String practiceRefId);

	/**
	 * 提交答案
	 *
	 * @param practiceId   实践id
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
	 * 获取实践详情
	 *
	 * @param practiceRefIds 实践裁判id列表
	 * @return {@code List<WebTheoryBo> }
	 */
	List<WebTheoryBo> getPracticeDetail(List<String> practiceRefIds);
}
