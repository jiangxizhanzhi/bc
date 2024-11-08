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
 * 实践纸Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface PracticePaperService extends IService<PracticePaper> {

	/**
	 * 获取最后纸设置
	 *
	 * @return {@code PracticePaperCreateReq }
	 */
	PracticePaperCreateReq getLastPaperSetting();

	/**
	 * 创建 Paper
	 *
	 * @param practicePaperCreateReq 实践纸创造请求体
	 * @return {@code PracticePaper }
	 */
	PracticePaper createPaper(PracticePaperCreateReq practicePaperCreateReq);

	/**
	 * 获取纸总结结果
	 *
	 * @param paperId 纸id
	 * @return {@code PaperSummaryRes }
	 */
	PaperSummaryRes getPaperSummaryRes(String paperId);

	/**
	 * 获取纸记录
	 *
	 * @return {@code List<PaperRecordRes> }
	 */
	List<PaperRecordRes> getPaperRecord();

	/**
	 * 获取都纸记录
	 *
	 * @param isFinish 结束了
	 * @return {@code List<PaperRecordSummaryResByDay> }
	 */
	List<PaperRecordSummaryResByDay> getAllPaperRecord(Boolean isFinish);

	/**
	 * 获取纸记录详情
	 *
	 * @param paperId 纸id
	 * @return {@code PaperRecordDetailRes }
	 */
	PaperRecordDetailRes getPaperRecordDetail(String paperId);

	/**
	 * 再次练习
	 *
	 * @param paperId 纸id
	 * @return {@code PracticePaper }
	 */
	PracticePaper practiceAgain(String paperId);
}
