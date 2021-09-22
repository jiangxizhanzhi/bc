package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.practice.controller.req.SinglePracticeReq;
import com.cyberpeace.oj.practice.controller.req.TestPaperReq;
import com.cyberpeace.oj.practice.dao.constants.PracticeType;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.TestPaperService;
import com.cyberpeace.oj.practice.service.bo.TestPaperInfo;
import com.cyberpeace.oj.practice.service.bo.TestPaperPractice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class TestPaperImpl extends ServiceImpl<TestPaperMapper, TestPaper> implements TestPaperService {
    @Autowired
    PracticeTheoryMapper practiceTheoryMapper;

    @Autowired
    PracticeTheoryRefMapper practiceTheoryRefMapper;

    @Autowired
    PracticeOperationMapper practiceOperationMapper;

    @Autowired
    PracticeOperationRefMapper practiceOperationRefMapper;

    @Autowired
    @Qualifier("ojTransactionTemplate")
    TransactionTemplate transactionTemplate;

    /**
     * 随机唯一字符串
     */
    private String rk() {
        return DigestUtils.md5Hex(String.valueOf(UUID.randomUUID()));
    }

    @Override
    public TestPaperInfo getSingleInfo(String resourceId) {
        TestPaperInfo object = new TestPaperInfo();
        BeanUtils.copyProperties(getById(resourceId), object);

        List<TestPaperPractice> practiceList = Lists.newArrayList();
        List<TestPaperPractice> theoryPractices = practiceTheoryRefMapper.getListByTestPaper(object.getResourceId());
        List<TestPaperPractice> operationPractices = practiceOperationRefMapper.getListByTestPaper(object.getResourceId());
        practiceList.addAll(theoryPractices);
        practiceList.addAll(operationPractices);

        object.setPracticeList(practiceList);
        return object;
    }

    @Override
    public TestPaperInfo create(TestPaperReq testPaperInfo) {
        TestPaper object = new TestPaper();
        BeanUtils.copyProperties(testPaperInfo, object);
        // 提前生成唯一标识
        object.setResourceId(rk());
        object.setPracticeCount(testPaperInfo.getPracticeList().size());
        object.setCreateUser(UserInfoContext.getUserContext().getUserId());

        // 解析practiceList, 设置试卷基础信息
        float totalScore = 0f;
        Map<String, SinglePracticeReq> practiceMap = Maps.newHashMap();
        List<String> theoryIds = Lists.newArrayList();
        List<String> operationIds = Lists.newArrayList();
        for (SinglePracticeReq single : testPaperInfo.getPracticeList()) {
            if (single.getPracticeType().equals(PracticeType.THEORY.getValue())) {
                theoryIds.add(single.getResourceId());
            } else {
                operationIds.add(single.getResourceId());
            }
            totalScore = totalScore + single.getScore();
            practiceMap.put(single.getResourceId(), single);
        }
        object.setTotalScore(totalScore);

        // 生成理论习题练习
        List<PracticeTheoryRef> theoryEntityList = Lists.newArrayList();
        if (theoryIds.size() > 0) {
            List<PracticeTheory> originTheoryList = practiceTheoryMapper.batchGet(theoryIds);
            for (PracticeTheory originItem : originTheoryList) {
                PracticeTheoryRef refItem = new PracticeTheoryRef();
                BeanUtils.copyProperties(originItem, refItem, "id", "resourceId");
                SinglePracticeReq reqPractice = practiceMap.get(originItem.getResourceId());

                refItem.setOriginId(originItem.getResourceId());
                refItem.setResourceId(rk());
                refItem.setScore(reqPractice.getScore());
                refItem.setIsOptionRandom(reqPractice.getIsOptionRandom());
                refItem.setTestPaper(object.getResourceId());

                theoryEntityList.add(refItem);
            }
        }

        // 生成实践操作练习
        List<PracticeOperationRef> operationEntityList = Lists.newArrayList();
        if (operationIds.size() > 0) {
            List<PracticeOperation> originOperationList = practiceOperationMapper.batchGet(operationIds);
            for (PracticeOperation originItem : originOperationList) {
                PracticeOperationRef refItem = new PracticeOperationRef();
                BeanUtils.copyProperties(originItem, refItem, "id", "resourceId");
                SinglePracticeReq reqPractice = practiceMap.get(originItem.getResourceId());

                refItem.setOriginId(originItem.getResourceId());
                refItem.setResourceId(rk());
                refItem.setScore(reqPractice.getScore());
                refItem.setTestPaper(object.getResourceId());

                operationEntityList.add(refItem);
            }
        }

        // insert db
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                save(object);
                if (theoryEntityList.size() > 0) {
                    practiceTheoryRefMapper.saveBatch(theoryEntityList);
                }
                if (operationEntityList.size() > 0) {
                    practiceOperationRefMapper.saveBatch(operationEntityList);
                }
            }
        });

        return getSingleInfo(object.getResourceId());
    }

    @Override
    public ListRsp<TestPaper> getList(Page page, String pattern) {
        return getBaseMapper().getList(page, pattern);
    }
}
