package com.cyberpeace.oj.practice.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.controller.res.PracticeSolvedRes;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.MockPaperMapper;
import com.cyberpeace.oj.practice.dao.mapper.PaperPracticeTheoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.MockPaperService;
import com.cyberpeace.oj.practice.service.PaperPracticeTheoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.PresetMockService;
import com.cyberpeace.oj.practice.service.bo.mock.*;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MockPaperServiceImpl extends ServiceImpl<MockPaperMapper, MockPaper> implements MockPaperService {

	@Autowired
	private PaperPracticeTheoryMapper paperPracticeTheoryMapper;

	@Autowired
	private PresetMockService presetMockService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PaperPracticeTheoryService paperPracticeTheoryService;

	@Override
	public TheorySummary getPaperSummaryRes(String paperId) {
		// 题目排序依赖PaperPracticeTheory的主键id
		List<PracticeSolvedRes> practiceSolvedResList = paperPracticeTheoryMapper
				.selectSolvedByPaperId(paperId);
		for (PracticeSolvedRes solvedRes : practiceSolvedResList) {
			solvedRes.setAnswered(solvedRes.answered());
		}
		Map<Integer, List<PracticeSolvedRes>> map = StreamApiUtils.groupBy(practiceSolvedResList,
				PracticeSolvedRes::getType);

		MockPaper paper = this.getById(paperId);
		PresetMockBo presetMockBo = presetMockService.getBoById(paper.getPresetId());
		TheorySummary res = new TheorySummary();
		res.setTitle(paper.getTitle());
		res.setStatus(paper.getTheoryStatus());
		res.setCount(presetMockBo.getTheoryCount());
		res.setSolvedCount(
				(int) practiceSolvedResList.stream().filter(PracticeSolvedRes::isAnswered).count());
		res.setCountDown(
				(int) TimeUtil.getIntervalSeconds(new Date(), paper.getExpectedTheoryEndTime()));

		Map<String, TheorySummaryByType> typeMap = Maps.newHashMap();
		for (CountAndScore countAndScore : presetMockBo.getTheoryDist()) {
			TheorySummaryByType summary = new TheorySummaryByType();
			summary.setType(countAndScore.getType());
			summary.setCount(countAndScore.getCount());
			summary.setScore(countAndScore.getScore());
			summary.setPracticeSolved(map.get(summary.getType()));
			typeMap.put(
					EnumUtils.getEnumDescByCode(summary.getType(), PracticeTheory.TheoryType.class),
					summary);
		}
		res.setMap(typeMap);
		return res;
	}

	@Override
	public MockPaperDetail getDetail(String paperId) {
		MockPaper paper = this.getById(paperId);
		MockPaperDetail detail = BeanUtils.copy(paper, MockPaperDetail.class);
		detail.setTheoryCountDown(
				(int) TimeUtil.getIntervalSeconds(new Date(), paper.getExpectedTheoryEndTime()));
		return detail;
	}

	@Override
	public void mark(MarkReq req) {
		PaperPracticeTheory theory = paperPracticeTheoryMapper.selectById(req.getResourceId());
		if (null == theory) {
			return;
		}
		if (req.isMark() ^ Boolean.TRUE.equals(theory.getMarked())) {
			theory.setMarked(req.isMark());
			paperPracticeTheoryMapper.updateById(theory);
		}
	}

	@Override
	public List<MockListBo> listMocks() {
		String userId = UserInfoContext.getUserContext().getUserId();
		List<MockPaper> mockPaperList = this.getBaseMapper().selectByCreator(userId, false);
		List<MockListBo> res = Lists.newArrayList();
		for (MockPaper paper : mockPaperList) {
			MockListBo bo = new MockListBo();
			bo.setResourceId(paper.getResourceId());
			bo.setTitle(paper.getTitle());
			bo.setCreateTime(paper.getCreateTime());
			bo.setCount(paper.getCount());
			bo.setStatus(paper.getStatus());

			res.add(bo);
		}
		return res;
	}

	@Override
	public List<MockListBo> listActualMocks() {
		List<PresetMock> actualPresetMocks = presetMockService.listAllMock(true);
		String userId = UserInfoContext.getUserContext().getUserId();
		List<MockPaper> mockPaperList = this.getBaseMapper().selectByCreator(userId, true);
		Map<String, MockPaper> paperMap = StreamApiUtils.toMap(mockPaperList,
				MockPaper::getPresetId, p -> p);
		List<MockListBo> res = Lists.newArrayList();
		for (PresetMock actualPresetMock : actualPresetMocks) {
			MockListBo bo = new MockListBo();
			bo.setTitle(actualPresetMock.getName());
			bo.setLogo(actualPresetMock.getLogo());

			String presetId = actualPresetMock.getResourceId();
			if (paperMap.containsKey(presetId)) {
				bo.setResourceId(paperMap.get(presetId).getResourceId());
				bo.setStatus(paperMap.get(presetId).getStatus());
			} else {
				bo.setResourceId(presetId);
				bo.setStatus(MockStatus.NOT_STARTED);
			}

			res.add(bo);
		}
		return res;
	}

	@Override
	public void createActualExam(String presetId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		MockPaper mockPaper = this.getBaseMapper().selectByPresetIdAndUserId(presetId, userId);
		if (null != mockPaper) {
			if (mockPaper.isOngoing()) {
				throw new BusinessException(OjBusinessErrorCode.ACTUAL_EXAM_ONGOING);
			}
			if (mockPaper.isFinished()) {
				// todo
			}
		}
		makeActual(presetId);

	}

	private void makeActual(String presetId) {
		PresetMockBo bo = presetMockService.getBoById(presetId);
		MockPaper paper = this.makeMockPaper(bo, true);

		// collect theory ids
		List<String> theoryIds = Lists.newArrayList();
		for (CountAndScore countAndScore : bo.getTheoryDist()) {
			theoryIds.addAll(countAndScore.getPracticeIds());
		}
		// 获取选中的理论题
		List<PracticeTheory> selected = practiceTheoryService.getBaseMapper()
				.selectBatchIds(theoryIds);
		Map<String, PracticeTheory> selectedMap = StreamApiUtils.toMap(selected,
				PracticeTheory::getResourceId, p -> p);
		List<PaperPracticeTheory> paperPracticeTheories = Lists.newArrayList();
		for (CountAndScore countAndScore : bo.getTheoryDist()) {
			for (String practiceId : countAndScore.getPracticeIds()) {
				PracticeTheory theory = selectedMap.get(practiceId);
				if (theory == null) {
					log.info("cannot find theory by id [{}]", practiceId);
					continue;
				}
				PaperPracticeTheory paperPracticeTheory = new PaperPracticeTheory();
				paperPracticeTheory.setPaperId(paper.getResourceId());
				paperPracticeTheory.setOriginId(practiceId);
				paperPracticeTheory.setSnapshot(JsonUtil.toJsonString(theory));
				paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
				paperPracticeTheory.setType(theory.getTheoryType());
				paperPracticeTheory.setScore(countAndScore.getScore());
				paperPracticeTheories.add(paperPracticeTheory);
			}
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				save(paper);
				paperPracticeTheoryService.saveBatch(paperPracticeTheories);
			}
		});
	}

	@Override
	public MockPaper createMock(MockExamAddReq req) {
		// todo validate
		PresetMockBo presetMock = presetMockService.getBoById(req.getPresetId());

		MockPaper paper = this.makeMockPaper(presetMock, false);
		if (StringUtils.isBlank(req.getTitle())) {
			paper.setTitle(TimeUtil.format9(new Date()) + " " + presetMock.getName());
		} else {
			paper.setTitle(req.getTitle());
		}

		// 按条件获取所有可组卷的题目
		List<PracticeTheory> practiceTheoryList = practiceTheoryMapper.selectAllIdAndType();
		Map<Integer, List<PracticeTheory>> typeMap = StreamApiUtils.groupBy(practiceTheoryList,
				PracticeTheory::getTheoryType);

		// distribute by type
		// PracticeTheory.TheoryType.values().
		List<String> selectedIds = Lists.newArrayList();
		Map<String, Integer> scoreMap = Maps.newHashMap();
		for (CountAndScore countAndScore : presetMock.getTheoryDist()) {
			Integer type = countAndScore.getType();
			PracticeTheory.TheoryType theoryType = EnumUtils.getEnumByCode(type,
					PracticeTheory.TheoryType.class);
			List<PracticeTheory> theoryIds = typeMap.get(type);
			if (CollectionUtils.isEmpty(theoryIds) || theoryIds.size() < countAndScore.getCount()) {
				log.error("theoryIds.size() < countAndScore.getCount()! MockExamAddReq=[{}]",
						JsonUtil.toJsonString(req));
				throw new BusinessException(OjBusinessErrorCode.NOT_ENOUGH_PRACTICE,
						theoryType.getDesc());
			}
			Collections.shuffle(theoryIds);
			List<String> ids = StreamApiUtils.map(theoryIds.subList(0, countAndScore.getCount()),
					PracticeTheory::getResourceId);
			for (String id : ids) {
				scoreMap.put(id, countAndScore.getScore());
			}
			selectedIds.addAll(ids);
		}
		// 获取选中的理论题
		List<PracticeTheory> selected = practiceTheoryMapper.selectBatchIds(selectedIds);
		List<PaperPracticeTheory> paperPracticeTheories = Lists.newArrayList();
		for (PracticeTheory theory : selected) {
			PaperPracticeTheory paperPracticeTheory = new PaperPracticeTheory();
			paperPracticeTheory.setPaperId(paper.getResourceId());
			paperPracticeTheory.setOriginId(theory.getResourceId());
			paperPracticeTheory.setSnapshot(JsonUtil.toJsonString(theory));
			paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
			paperPracticeTheory.setType(theory.getTheoryType());
			paperPracticeTheory.setScore(scoreMap.get(theory.getResourceId()));
			paperPracticeTheories.add(paperPracticeTheory);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				save(paper);
				paperPracticeTheoryService.saveBatch(paperPracticeTheories);
			}
		});
		return paper;
	}

	@Override
	public void updateMock(MockExamModifyReq req) {
		MockPaper paper = this.getById(req.getResourceId());
		if (null == paper) {
			return;
		}
		paper.setTitle(req.getTitle());
		this.save(paper);
	}

	@Override
	public void deleteMock(MockDeleteReq req) {
		this.removeById(req.getResourceId());
	}

	@Override
	public void submit(String paperId) {
		MockPaper paper = this.getById(paperId);
		if (paper.isTheoryFinished()) {
			// todo
			throw new BusinessException();
		}
		submitPaper(paper);
	}

	private MockPaper makeMockPaper(PresetMockBo bo, boolean actual) {
		MockPaper paper = new MockPaper();

		paper.setCount(bo.getCount());
		paper.setOperationDesc(bo.getOperationDesc());
		paper.setOperationLength(bo.getOperationLength());
		paper.setOperationScore(0);
		paper.setOperationStatus(MockStatus.NOT_STARTED);
		paper.setResourceId(IDGeneratorUtil.getID());
		paper.setTheoryDesc(bo.getTheoryDesc());
		paper.setTheoryLength(bo.getTheoryLength());
		paper.setTheoryScore(0);
		paper.setTheoryStatus(MockStatus.NOT_STARTED);
		paper.setPresetId(bo.getResourceId());
		paper.setUserId(UserInfoContext.getUserContext().getUserId());

		if (actual) {
			paper.setTitle(TimeUtil.format9(new Date()) + " " + bo.getName());
		}
		paper.setActual(actual);

		return paper;
	}

	/**
	 * 超时自动交卷
	 * @return boolean
	 */
	private boolean overTimeHandout(MockPaper paper) {
		if (paper.getExpectedTheoryEndTime().after(new Date())) {
			return true;
		}
		submitPaper(paper);
		return false;
	}

	private void submitPaper(MockPaper paper) {

		List<PaperPracticeTheory> theoryList = paperPracticeTheoryMapper
				.selectByPaperId(paper.getResourceId());
		int sum = 0;
		for (PaperPracticeTheory theory : theoryList) {
			if (theory.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode())) {
				sum += theory.getScore();
			}
		}
		// todo ctf

		int finalScore = sum * 100 / paper.getTheoryScore();
		paper.setTheoryScore(finalScore);
		if (finalScore >= 60) {
			paper.setTheoryStatus(MockStatus.PASSED);
		} else {
			paper.setTheoryStatus(MockStatus.FAILED);
		}
		paper.setTheoryEndTime(new Date());
		this.updateById(paper);
	}
}
