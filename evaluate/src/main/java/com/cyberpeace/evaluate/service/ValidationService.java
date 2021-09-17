package com.cyberpeace.evaluate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.dao.entity.TestInfo;
import com.cyberpeace.evaluate.service.bo.TestRecordBO;

public interface ValidationService {

    ListRsp<TestInfo> pageInfo(String userId, Page pageBase);

    void addTestInfo(TestInfo testInfo);

    /**
     * 根据评估id查询测试记录
     * @param infoId
     * @return
     */
    TestRecordBO getTestRecord(String infoId);

    void deleteTestInfo(String infoId);
}
