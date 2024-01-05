package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.MockPaper;
import com.cyberpeace.oj.practice.service.bo.mock.*;

import java.util.List;

public interface MockPaperService extends IService<MockPaper> {

	TheorySummary getPaperSummaryRes(String paperId);

    MockPaperDetail getDetail(String paperId);

    void mark(MarkReq req);

    List<MockListBo> listMocks();


    List<MockListBo> listActualMocks();

    /**
     * 真题考试
     *
     * @param presetId 请求体
     */
    void createActualExam(String presetId);

    MockPaper createMock(MockExamAddReq req);

    void updateMock(MockExamModifyReq req);

    void deleteMock(MockDeleteReq req);

    /**
     * 交卷
     * @param paperId
     */
    void submit(String paperId);
}
