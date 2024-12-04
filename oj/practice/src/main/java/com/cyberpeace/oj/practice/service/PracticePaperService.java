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
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PracticePaperService extends IService<PracticePaper> {

	/**
	 * 获取最后纸设置
	 *
	 * @return {@code PracticePaperCreateReq }
	 * @throws
	 * @see PracticePaperCreateReq
	 */
	PracticePaperCreateReq getLastPaperSetting();

	/**
	 * 创建 Paper
	 *
	 * @param practicePaperCreateReq 实践纸创造请求体
	 * @return {@code PracticePaper }
	 * @throws
	 * @see PracticePaperCreateReq
	 * @see PracticePaper
	 */
	PracticePaper createPaper(PracticePaperCreateReq practicePaperCreateReq);

	/**
	 * 获取纸总结结果
	 *
	 * @param paperId 纸id
	 * @return {@code PaperSummaryRes }
	 * @throws
	 * @see String
	 * @see PaperSummaryRes
	 */
	PaperSummaryRes getPaperSummaryRes(String paperId);

	/**
	 * 获取纸记录
	 *
	 * @return {@code List<PaperRecordRes> }
	 * @throws
	 * @see List<PaperRecordRes>
	 */
	List<PaperRecordRes> getPaperRecord();

	/**
	 * 获取都纸记录
	 *
	 * @param isFinish 结束了
	 * @return {@code List<PaperRecordSummaryResByDay> }
	 * @throws
	 * @see Boolean
	 * @see List<PaperRecordSummaryResByDay>
	 */
	List<PaperRecordSummaryResByDay> getAllPaperRecord(Boolean isFinish);

	/**
	 * 获取纸记录详情
	 *
	 * @param paperId 纸id
	 * @return {@code PaperRecordDetailRes }
	 * @throws
	 * @see String
	 * @see PaperRecordDetailRes
	 */
	PaperRecordDetailRes getPaperRecordDetail(String paperId);

	/**
	 * 再次练习
	 *
	 * @param paperId 纸id
	 * @return {@code PracticePaper }
	 * @throws
	 * @see String
	 * @see PracticePaper
	 */
	PracticePaper practiceAgain(String paperId);
}
