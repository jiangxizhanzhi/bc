package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

import java.security.PrivateKey;
import java.util.List;

public interface PracticeTheoryRefService extends IService<PracticeTheoryRef> {

	/**
	 * 获取试卷基础理论习题
	 * @param paperId
	 * @param viewAnswer
	 * @return
	 */
	List<TheoryLessonBo> selectByPaper(String paperId, Boolean viewAnswer);

	/**
	 * 根据习题编号查询试卷习题
	 * @param practiceId
	 * @return
	 */
	PracticeTheory selectByPracticeId(String paperId, String practiceId);

	void exportLessonPractice(String pathname, String paperId);

}
