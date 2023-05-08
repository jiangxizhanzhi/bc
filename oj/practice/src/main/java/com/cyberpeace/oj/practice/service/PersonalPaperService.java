package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.req.PersonalPaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.PaperSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PersonalPaper;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

public interface PersonalPaperService extends IService<PersonalPaper> {

	PersonalPaperCreateReq getLastPaperSetting();

	PersonalPaper createPaper(PersonalPaperCreateReq personalPaperCreateReq);

	PaperSummaryRes getPaperSummaryRes(String paperId);


}
