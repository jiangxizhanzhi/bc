package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.practice.dao.entity.PracticeBase;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PracticeTheoryRefImpl extends ServiceImpl<PracticeTheoryRefMapper, PracticeTheoryRef> implements PracticeTheoryRefService {

    @Autowired
    PracticeTheoryRefMapper practiceTheoryRefMapper;

    @Override
    public List<TestPaperPractice> getListByTestPaper(String testPaper) {
        return practiceTheoryRefMapper.getListByTestPaper(testPaper);
    }
}
