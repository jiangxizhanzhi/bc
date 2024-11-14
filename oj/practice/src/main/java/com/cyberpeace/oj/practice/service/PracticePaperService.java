package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.req.PracticePaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.PaperRecordDetailRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryResByDay;
import com.cyberpeace.oj.practice.controller.res.PaperSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PracticePaper;

import java.util.List;

/**
 * 练习试卷Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface PracticePaperService extends IService<PracticePaper> {

	/**
	 * 获取最后试卷设置
	 *
	 * @return {@code PracticePaperCreateReq }
	 */
	PracticePaperCreateReq getLastPaperSetting();

	/**
	 * 创建试卷
	 *
	 * @param practicePaperCreateReq 练习试卷创建请求体
	 * @return {@code PracticePaper }
	 */
	PracticePaper createPaper(PracticePaperCreateReq practicePaperCreateReq);

	/**
	 * 获取试卷总结结果
	 *
	 * @param paperId 试卷id
	 * @return {@code PaperSummaryRes }
	 */
	PaperSummaryRes getPaperSummaryRes(String paperId);

	/**
	 * 获取试卷记录
	 *
	 * @return {@code List<PaperRecordRes> }
	 */
	List<PaperRecordRes> getPaperRecord();

	/**
	 * 获取全部试卷记录
	 *
	 * @param isFinish 是否完成
	 * @return {@code List<PaperRecordSummaryResByDay> }
	 */
	List<PaperRecordSummaryResByDay> getAllPaperRecord(Boolean isFinish);

	/**
	 * 获取试卷记录详情
	 *
	 * @param paperId 试卷id
	 * @return {@code PaperRecordDetailRes }
	 */
	PaperRecordDetailRes getPaperRecordDetail(String paperId);

	/**
	 * 练习再
	 *
	 * @param paperId 试卷id
	 * @return {@code PracticePaper }
	 */
	PracticePaper practiceAgain(String paperId);
}
