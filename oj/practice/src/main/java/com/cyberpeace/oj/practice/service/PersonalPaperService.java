package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.req.PersonalPaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryRes;
import com.cyberpeace.oj.practice.controller.res.PaperSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PersonalPaper;

import java.util.List;
import java.util.Map;

public interface PersonalPaperService extends IService<PersonalPaper> {

	PersonalPaperCreateReq getLastPaperSetting();

	PersonalPaper createPaper(PersonalPaperCreateReq personalPaperCreateReq);

	PaperSummaryRes getPaperSummaryRes(String paperId);

	List<PaperRecordRes> getPaperRecord();

	Map<String, List<PaperRecordSummaryRes>> getAllPaperRecord(Boolean isFinish);
}
