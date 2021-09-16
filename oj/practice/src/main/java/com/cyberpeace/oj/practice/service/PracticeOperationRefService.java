package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;

import java.util.List;

public interface PracticeOperationRefService extends IService<PracticeOperationRef> {
    List<TestPaperPractice> getListByTestPaper(String testPaper);

}
