package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.req.PracticePaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.PaperRecordDetailRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryRes;
import com.cyberpeace.oj.practice.controller.res.PaperSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PracticePaper;

import java.util.List;
import java.util.Map;

public interface PracticePaperService extends IService<PracticePaper> {

	PracticePaperCreateReq getLastPaperSetting();

	PracticePaper createPaper(PracticePaperCreateReq practicePaperCreateReq);

	PaperSummaryRes getPaperSummaryRes(String paperId);

	List<PaperRecordRes> getPaperRecord();

	Map<String, List<PaperRecordSummaryRes>> getAllPaperRecord(Boolean isFinish);

	PaperRecordDetailRes getPaperRecordDetail(String paperId);
}
