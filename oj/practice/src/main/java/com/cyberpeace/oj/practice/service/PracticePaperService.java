package com.cyberpeace.oj.practice.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.req.PracticePaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.PaperRecordDetailRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryResByDay;
import com.cyberpeace.oj.practice.controller.res.PaperSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.MockPaper;
import com.cyberpeace.oj.practice.dao.entity.PracticePaper;
import com.cyberpeace.oj.practice.service.bo.mock.MockDeleteReq;
import com.cyberpeace.oj.practice.service.bo.mock.MockExamAddReq;
import com.cyberpeace.oj.practice.service.bo.mock.MockExamModifyReq;

public interface PracticePaperService extends IService<PracticePaper> {

	PracticePaperCreateReq getLastPaperSetting();

	PracticePaper createPaper(PracticePaperCreateReq practicePaperCreateReq);

	PaperSummaryRes getPaperSummaryRes(String paperId);

	List<PaperRecordRes> getPaperRecord();

	List<PaperRecordSummaryResByDay> getAllPaperRecord(Boolean isFinish);

	PaperRecordDetailRes getPaperRecordDetail(String paperId);

	PracticePaper practiceAgain(String paperId);

}
