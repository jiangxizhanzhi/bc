package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;

import java.util.List;

public interface PracticeOperationRefService extends IService<PracticeOperationRef> {

	/**
	 * 根据试卷查询查询课后练习习题数据
	 * @param lessonId 用于场景数据查询
	 * @param paperId 试卷编号
	 * @param viewAnswer 是否展示答案 true展示
	 * @param simpleData 是否只查询基本数据 true 仅查询基本数据
	 * @param authContext
	 * @return
	 */
	List<OperationLessonBo> selectByPaperId(String lessonId, String paperId, Boolean viewAnswer,
			Boolean simpleData, AuthContext authContext);

	/**
	 * 根据习题编号查询
	 * @param paperId
	 * @param practiceId
	 * @return
	 */
	PracticeOperation selectByPracticeId(String paperId, String practiceId);

	void exportLessonPractice(String pathname, String paperId, Boolean disableImageExport);

}
