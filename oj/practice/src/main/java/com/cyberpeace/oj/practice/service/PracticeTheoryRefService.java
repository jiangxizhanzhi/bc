package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;

import java.util.List;

public interface PracticeTheoryRefService extends IService<PracticeTheoryRef> {
    List<TestPaperPractice> getListByTestPaper(String testPaper);
}
