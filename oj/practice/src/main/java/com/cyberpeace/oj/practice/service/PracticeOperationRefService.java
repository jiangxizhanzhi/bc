package com.cyberpeace.oj.practice.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;

/**
 * 练习实操裁判Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface PracticeOperationRefService extends IService<PracticeOperationRef> {

	/**
	 * 根据试卷查询查询课后练习习题数据
	 *
	 * @param lessonId    用于场景数据查询
	 * @param paperId     试卷编号
	 * @param viewAnswer  是否展示答案 true展示
	 * @param simpleData  是否只查询基本数据 true 仅查询基本数据
	 * @param authContext
	 * @return
	 */
	List<OperationLessonBo> selectByPaperId(String lessonId, String paperId, Boolean viewAnswer,
			Boolean simpleData, AuthContext authContext);

	/**
	 * 根据习题编号查询
	 *
	 * @param paperId
	 * @param practiceId
	 * @return
	 */
	PracticeOperation selectByPracticeId(String paperId, String practiceId);

	/**
	 * 导出课时练习
	 *
	 * @param pathname           路径
	 * @param paperId            试卷id
	 * @param disableImageExport 禁用图像导出
	 */
	void exportLessonPractice(String pathname, String paperId, Boolean disableImageExport);

	/**
	 * 查询课后练习中的拓扑编号数据
	 *
	 * @param paperIds
	 * @return
	 */
	List<String> selectLessonPracticeTopoIds(List<String> paperIds);

	/**
	 * 根据试卷编号查询课后练习
	 *
	 * @param paperId  试卷编号
	 * @param onlyTopo 仅获取包含拓扑的实操题目
	 * @return
	 */
	List<PracticeOperation> operationByPaper(String paperId, boolean onlyTopo);

	/**
	 * 查询课后练习场景数据
	 *
	 * @param lessonId    课时id
	 * @param paperId
	 * @param authContext auth 上下文
	 * @return
	 */
	List<OjLocalScene> operationByPaper(String lessonId, String paperId, AuthContext authContext);
}
