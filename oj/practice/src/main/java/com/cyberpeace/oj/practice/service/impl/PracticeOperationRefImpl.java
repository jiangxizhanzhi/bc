package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.practice.dao.entity.PracticeBase;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PracticeOperationRefImpl extends ServiceImpl<PracticeOperationRefMapper, PracticeOperationRef> implements PracticeOperationRefService {
    @Autowired
    PracticeOperationRefMapper practiceOperationRefMapper;

    @Override
    public List<TestPaperPractice> getListByTestPaper(String testPaper) {
        return practiceOperationRefMapper.getListByTestPaper(testPaper);
    }
}
