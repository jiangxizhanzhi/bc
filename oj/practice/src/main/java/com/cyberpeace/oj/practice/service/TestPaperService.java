package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.req.PracticeTheoryReq;
import com.cyberpeace.oj.practice.controller.req.TestPaperReq;
import com.cyberpeace.oj.practice.dao.entity.TestPaper;
import com.cyberpeace.oj.practice.service.bo.TestPaperInfo;


public interface TestPaperService extends IService<TestPaper> {

    TestPaperInfo getSingleInfo(String resourceId);

    TestPaperInfo create(TestPaperReq testPaperInfo);
}
