package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.controller.req.PersonalPaperCreateReq;
import com.cyberpeace.oj.practice.dao.entity.PersonalPaper;

public interface PersonalPaperService {

	PersonalPaperCreateReq getLastPaperSetting();

	PersonalPaper createPaper(PersonalPaperCreateReq personalPaperCreateReq);
}
