package com.cyberpeace.oj.practice.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

/**
 * 练习理论裁判Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface PracticeTheoryRefService extends IService<PracticeTheoryRef> {

	/**
	 * 获取试卷基础理论习题
	 *
	 * @param paperId
	 * @param viewAnswer
	 * @return
	 */
	List<TheoryLessonBo> selectByPaper(String paperId, Boolean viewAnswer);

	/**
	 * 根据习题编号查询试卷习题
	 *
	 * @param paperId    试卷id
	 * @param practiceId
	 * @return
	 */
	PracticeTheory selectByPracticeId(String paperId, String practiceId);

	/**
	 * 导出课时练习
	 *
	 * @param pathname 路径
	 * @param paperId  试卷id
	 */
	void exportLessonPractice(String pathname, String paperId);
}
