package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import com.cyberpeace.oj.practice.service.bo.EvaluateResultTheory;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

public interface PaperPracticeTheoryService extends IService<PaperPracticeTheory> {

	WebTheoryBo getPracticeDetail(String practiceRefId);

	EvaluateResultTheory submitAnswer(String practiceId, String submitAnswer);

	Boolean collection(CollectionReq collectionReq);
}
