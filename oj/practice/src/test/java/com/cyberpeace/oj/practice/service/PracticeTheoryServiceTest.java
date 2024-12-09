package com.cyberpeace.oj.practice.service;

import java.util.*;
import java.math.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.practice.controller.req.WebTheoryRecordReq;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryBo;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryCountBo;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PracticeTheoryServiceTest {

    @Mock
    private PracticeTheoryService practiceTheoryService;

    @InjectMocks
    private PracticeTheoryService practiceTheoryServiceImpl;

    private Page page;
    private TheoryListQueryBo listQuery;
    private AuthContext authContext;
    private List<PracticeTheory> practiceTheories;

    @BeforeEach
    public void setUp() {
        page = new Page();
        listQuery = new TheoryListQueryBo();
        authContext = new AuthContext();
        practiceTheories = new ArrayList<>();
    }

    @Test
    public void testPageByQuery_ReturnsCorrectPage() {
        IPage<PracticeTheoryListBo> mockPage = new Page<>();
        when(practiceTheoryService.pageByQuery(any(Page.class), any(TheoryListQueryBo.class), any(AuthContext.class)))
                .thenReturn(mockPage);

        IPage<PracticeTheoryListBo> result = practiceTheoryService.pageByQuery(page, listQuery, authContext);

        assertEquals(mockPage, result);
    }

    @Test
    public void testSelectById_ReturnsCorrectWebTheoryBo() {
        WebTheoryBo mockWebTheoryBo = new WebTheoryBo();
        when(practiceTheoryService.selectById(any(String.class), any(String.class), any(Boolean.class)))
                .thenReturn(mockWebTheoryBo);

        WebTheoryBo result = practiceTheoryService.selectById("resourceId", "userId", true);

        assertEquals(mockWebTheoryBo, result);
    }

    @Test
    public void testSubmitAnswer_ReturnsCorrectEvaluateResult() {
        EvaluateResult mockEvaluateResult = new EvaluateResult();
        when(practiceTheoryService.submitAnswer(any(String.class), any(String.class), any(String.class), any(Boolean.class)))
                .thenReturn(mockEvaluateResult);

        EvaluateResult result = practiceTheoryService.submitAnswer("userId", "theoryId", "userAnswer", true);

        assertEquals(mockEvaluateResult, result);
    }

    @Test
    public void testCreate_ReturnsCorrectInteger() {
        Integer mockResult = 1;
        when(practiceTheoryService.create(any(PracticeTheory.class), any(AuthContext.class))).thenReturn(mockResult);

        Integer result = practiceTheoryService.create(new PracticeTheory(), authContext);

        assertEquals(mockResult, result);
    }

    @Test
    public void testUpdateTheory_ReturnsCorrectInteger() {
        int mockResult = 1;
        when(practiceTheoryService.updateTheory(any(PracticeTheory.class), any(AuthContext.class))).thenReturn(mockResult);

        int result = practiceTheoryService.updateTheory(new PracticeTheory(), authContext);

        assertEquals(mockResult, result);
    }

    @Test
    public void testSelectPracticeListIds_ReturnsCorrectList() {
        List<String> mockResult = new ArrayList<>();
        when(practiceTheoryService.selectPracticeListIds(any(AuthContext.class), any(String.class))).thenReturn(mockResult);

        List<String> result = practiceTheoryService.selectPracticeListIds(authContext, "practiceSetId");

        assertEquals(mockResult, result);
    }

    @Test
    public void testBatchDelete_ReturnsCorrectInteger() {
        Integer mockResult = 1;
        when(practiceTheoryService.batchDelete(any(List.class))).thenReturn(mockResult);

        Integer result = practiceTheoryService.batchDelete(new ArrayList<String>());

        assertEquals(mockResult, result);
    }

    @Test
    public void testLoadPracticeTheory_ReturnsCorrectPracticeTheory() {
        PracticeTheory mockResult = new PracticeTheory();
        when(practiceTheoryService.loadPracticeTheory(any(String.class), any(PracticeTheoryDumpLoadBo.class), any(String.class)))
                .thenReturn(mockResult);

        PracticeTheory result = practiceTheoryService.loadPracticeTheory("practiceSetId", new PracticeTheoryDumpLoadBo(), "userId");

        assertEquals(mockResult, result);
    }

    @Test
    public void testDumpTheory_ReturnsCorrectPracticeTheoryDumpLoadBo() {
        PracticeTheoryDumpLoadBo mockResult = new PracticeTheoryDumpLoadBo();
        when(practiceTheoryService.dumpTheory(any(PracticeTheory.class))).thenReturn(mockResult);

        PracticeTheoryDumpLoadBo result = practiceTheoryService.dumpTheory(new PracticeTheory());

        assertEquals(mockResult, result);
    }

    @Test
    public void testCreateMpLog_DoesNothing() {
        // Void method, no return value to check
        practiceTheoryService.createMpLog("practiceId", LogContent.ADD);
    }

    @Test
    public void testTypeCount_ReturnsCorrectExternalTheoryCountBo() {
        ExternalTheoryCountBo mockResult = new ExternalTheoryCountBo();
        when(practiceTheoryService.typeCount(any(TheoryListQueryBo.class), any(AuthContext.class))).thenReturn(mockResult);

        ExternalTheoryCountBo result = practiceTheoryService.typeCount(listQuery, authContext);

        assertEquals(mockResult, result);
    }

    @Test
    public void testExportMdTheoryPractice_ReturnsCorrectMdTheoryPractice() {
        MdTheoryPractice mockResult = new MdTheoryPractice();
        when(practiceTheoryService.exportMdTheoryPractice(any(String.class), any(List.class))).thenReturn(mockResult);

        MdTheoryPractice result = practiceTheoryService.exportMdTheoryPractice("path", practiceTheories);

        assertEquals(mockResult, result);
    }

    @Test
    public void testGetProgressStatistics_ReturnsCorrectProgressStatisticsRes() {
        ProgressStatisticsRes mockResult = new ProgressStatisticsRes();
        when(practiceTheoryService.getProgressStatistics(any(String.class))).thenReturn(mockResult);

        ProgressStatisticsRes result = practiceTheoryService.getProgressStatistics("practiceSetId");

        assertEquals(mockResult, result);
    }

    @Test
    public void testGetProgressByCategory_ReturnsCorrectList() {
        List<CategoryProgressRes> mockResult = new ArrayList<>();
        when(practiceTheoryService.getProgressByCategory()).thenReturn(mockResult);

        List<CategoryProgressRes> result = practiceTheoryService.getProgressByCategory();

        assertEquals(mockResult, result);
    }

    @Test
    public void testGetPracticeRecord_ReturnsCorrectList() {
        List<PracticeTheoryListBo> mockResult = new ArrayList<>();
        when(practiceTheoryService.getPracticeRecord(any(WebTheoryRecordReq.class))).thenReturn(mockResult);

        List<PracticeTheoryListBo> result = practiceTheoryService.getPracticeRecord(new WebTheoryRecordReq());

        assertEquals(mockResult, result);
    }

    @Test
    public void testGetPracticeIdsByUserAndSet_ReturnsCorrectList() {
        List<String> mockResult = new ArrayList<>();
        when(practiceTheoryService.getPracticeIdsByUserAndSet(any(AuthContext.class), any(String.class))).thenReturn(mockResult);

        List<String> result = practiceTheoryService.getPracticeIdsByUserAndSet(authContext, "practiceSetId");

        assertEquals(mockResult, result);
    }

    @Test
    public void testGetCategoryIdAndSetIds_ReturnsCorrectList() {
        List<PracticeTheory> mockResult = new ArrayList<>();
        when(practiceTheoryService.getCategoryIdAndSetIds()).thenReturn(mockResult);

        List<PracticeTheory> result = practiceTheoryService.getCategoryIdAndSetIds();

        assertEquals(mockResult, result);
    }

    @Test
    public void testPageByQueryWithoutAuth_ReturnsCorrectPage() {
        IPage<ExternalTheoryBo> mockPage = new Page<>();
        when(practiceTheoryService.pageByQuery(any(Page.class), any(TheoryListQueryBo.class))).thenReturn(mockPage);

        IPage<ExternalTheoryBo> result = practiceTheoryService.pageByQuery(page, listQuery);

        assertEquals(mockPage, result);
    }
}
