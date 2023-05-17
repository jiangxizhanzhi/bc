package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.controller.req.PracticePaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.*;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PaperPracticeTheoryService;
import com.cyberpeace.oj.practice.service.PracticePaperService;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Service
public class PracticePaperServiceImpl extends ServiceImpl<PracticePaperMapper, PracticePaper> implements PracticePaperService {

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticePaperMapper practicePaperMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PaperPracticeTheoryService paperPracticeTheoryService;

	@Autowired
	private PaperPracticeTheoryMapper paperPracticeTheoryMapper;

	@Override
	public PracticePaperCreateReq getLastPaperSetting() {
		String userId = UserInfoContext.getUserContext().getUserId();
		PracticePaper practicePaper = practicePaperMapper.getLastPaperByUserId(userId);
		PracticePaperCreateReq practicePaperCreateReq = new PracticePaperCreateReq();
		if (practicePaper == null) {
			practicePaperCreateReq.setCount(5);
			practicePaperCreateReq.setDifficulty(Arrays.asList(Difficulty.PRIMARY.getCode()));
			practicePaperCreateReq.setDirection(new ArrayList<>());
			practicePaperCreateReq.setStatus(Arrays.asList(PracticeSolved.SolvedStatus.NOT_START.getCode()));
			practicePaperCreateReq.setType(Arrays.asList(PracticeTheory.TheoryType.SINGLE.getCode()));
			return practicePaperCreateReq;
		}
		practicePaperCreateReq.setType(JsonUtil.toJsonObjectArray(practicePaper.getType(), Integer.class));
		practicePaperCreateReq.setCount(practicePaper.getCount());
		practicePaperCreateReq.setDirection(JsonUtil.toJsonObjectArray(practicePaper.getDirection(), String.class));
		practicePaperCreateReq.setDifficulty(JsonUtil.toJsonObjectArray(practicePaper.getDifficulty(), Integer.class));
		practicePaperCreateReq.setStatus(JsonUtil.toJsonObjectArray(practicePaper.getPracticeStatus(), Integer.class));
		return practicePaperCreateReq;
	}

	@Override
	public PracticePaper createPaper(PracticePaperCreateReq practicePaperCreateReq) {
		//按条件获取所有可组卷的题目
		PracticePaper practicePaper = new PracticePaper();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), true);
		List<String> practiceIds = getPracticeIdsByQueryAndSolved(practicePaperCreateReq.getStatus(), authContext);
		List<PracticeTheory> practiceTheorySumList = practiceTheoryMapper.selectAllByCreatePaper(practicePaperCreateReq.getDifficulty(),
				practicePaperCreateReq.getType(), practicePaperCreateReq.getDirection(), practiceIds);
		if (practiceTheorySumList.size() < practicePaperCreateReq.getCount()) {
			throw new BusinessException(OjBusinessErrorCode.NOT_ENOUGH_PRACTICE);
		}
		//按方向进行统计并按每个方向的题目数量进行排序（产品要求组卷时各个方向的题目数量尽量一样）
		Map<String, List<PracticeTheory>> directionMap = new HashMap<>();
		for (PracticeTheory practiceTheory: practiceTheorySumList) {
			if (directionMap.get(practiceTheory.getCategoryId()) == null) {
				directionMap.put(practiceTheory.getCategoryId(), new ArrayList<>(Arrays.asList(practiceTheory)));
			} else {
				directionMap.get(practiceTheory.getCategoryId()).add(practiceTheory);
			}
		}
		List<List<PracticeTheory>> practiceTheoryListByDirection = new ArrayList<>(directionMap.values());
		practiceTheoryListByDirection.sort(Comparator.comparing(List::size));
		List<PracticeTheory> practiceTheoryList = new ArrayList<>();
		int sum = practicePaperCreateReq.getCount();
		int size = practiceTheoryListByDirection.size();
		//从题目数量少的方向依次拿题
		for (List<PracticeTheory> practiceTheories : practiceTheoryListByDirection) {
			int count = sum/size;
			Collections.shuffle(practiceTheories);
			if (practiceTheories.size() < count) {
				practiceTheoryList.addAll(practiceTheories);
				sum -= practiceTheories.size();
			}else {
				practiceTheoryList.addAll(practiceTheories.subList(0, count));
				sum -= count;
			}
			size--;
		}
		Collections.shuffle(practiceTheoryList);
		practicePaper.setCount(practicePaperCreateReq.getCount());
		practicePaper.setIsFinish(false);
		practicePaper.setDifficulty(JsonUtil.toJsonString(practicePaperCreateReq.getDifficulty()));
		practicePaper.setDirection(JsonUtil.toJsonString(practicePaperCreateReq.getDirection()));
		practicePaper.setPracticeStatus(JsonUtil.toJsonString(practicePaperCreateReq.getStatus()));
		practicePaper.setType(JsonUtil.toJsonString(practicePaperCreateReq.getType()));
		practicePaper.setUserId(UserInfoContext.getUserContext().getUserId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				practicePaperMapper.insert(practicePaper);
				List<PaperPracticeTheory> paperPracticeTheories = new ArrayList<>();
				for (PracticeTheory practiceTheory : practiceTheoryList){
					PaperPracticeTheory paperPracticeTheory = new PaperPracticeTheory();
					paperPracticeTheory.setPaperId(practicePaper.getResourceId());
					paperPracticeTheory.setOriginId(practiceTheory.getResourceId());
					paperPracticeTheory.setSnapshot(JsonUtil.toJsonString(practiceTheory));
					paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
					paperPracticeTheories.add(paperPracticeTheory);
				}
				paperPracticeTheoryService.saveBatch(paperPracticeTheories);
			}
		});
		return practicePaper;
	}

	@Override
	public PaperSummaryRes getPaperSummaryRes(String paperId) {
		PaperSummaryRes paperSummaryRes = new PaperSummaryRes();
		List<PracticeSolvedRes> practiceSolvedResList = paperPracticeTheoryMapper.selectSolvedByPaperId(paperId);
		paperSummaryRes.setPracticeSolved(practiceSolvedResList);
		paperSummaryRes.setRight((int) practiceSolvedResList.stream()
															.filter(practiceSolvedRes -> practiceSolvedRes.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode()))
															.count());
		paperSummaryRes.setWrong((int) practiceSolvedResList.stream()
															.filter(practiceSolvedRes -> practiceSolvedRes.getSolved().equals(PracticeSolved.SolvedStatus.TRIED.getCode()))
															.count());
		paperSummaryRes.setNotStart((int) practiceSolvedResList.stream()
															.filter(practiceSolvedRes -> practiceSolvedRes.getSolved().equals(PracticeSolved.SolvedStatus.NOT_START.getCode()))
															.count());
		return paperSummaryRes;
	}

	/**
	 * 组卷记录
	 * @return
	 */
	@Override
	public List<PaperRecordRes> getPaperRecord() {
		String userId = UserInfoContext.getUserContext().getUserId();
		return practicePaperMapper.getPaperRecord(userId);
	}

	@Override
	public List<PaperRecordSummaryResByDay> getAllPaperRecord(Boolean isFinish) {
		Map<String, List<PaperRecordSummaryRes>> recordListMap = new HashMap<>();
		List<PaperRecordSummaryResByDay> sumRecordList = new ArrayList<>();
		List<PaperRecordSummaryRes> paperRecordSummaryResList = practicePaperMapper.getAllPaperRecord(UserInfoContext.getUserContext().getUserId());
		if (paperRecordSummaryResList.size() > 40) {
			paperRecordSummaryResList = paperRecordSummaryResList.subList(0, 40);
		}
		if (isFinish != null) {
			paperRecordSummaryResList = StreamApiUtils.filter(paperRecordSummaryResList, paperRecordSummaryRes -> paperRecordSummaryRes.getIsFinish() == isFinish);
		}
		for (PaperRecordSummaryRes paperRecordSummaryRes : paperRecordSummaryResList) {
			String dayTime = TimeUtil.format6(TimeUtil.toDate1(paperRecordSummaryRes.getPaperTime()));
			paperRecordSummaryRes.setPaperTime(TimeUtil.format9(TimeUtil.toDate1(paperRecordSummaryRes.getPaperTime())));
			if (paperRecordSummaryRes.getIsFinish()) {
				paperRecordSummaryRes.setAccuracyRate(paperRecordSummaryRes.getRight() * 100 / paperRecordSummaryRes.getCount());
			}
			if (recordListMap.get(dayTime) == null) {
				List<PaperRecordSummaryRes> recordList = new ArrayList<>();
				recordList.add(paperRecordSummaryRes);
				recordListMap.put(dayTime, recordList);
			} else {
				recordListMap.get(dayTime).add(paperRecordSummaryRes);
			}
		}
		for (String time : recordListMap.keySet()) {
			PaperRecordSummaryResByDay paperRecordSummaryResByDay = new PaperRecordSummaryResByDay();
			paperRecordSummaryResByDay.setTime(time);
			paperRecordSummaryResByDay.setSummaryRes(recordListMap.get(time));
			sumRecordList.add(paperRecordSummaryResByDay);
		}
		sumRecordList.sort(Comparator.comparing(PaperRecordSummaryResByDay::getTime).reversed());
		return sumRecordList;
	}

	@Override
	public PaperRecordDetailRes getPaperRecordDetail(String paperId) {
		PaperRecordSummaryRes paperRecordSummaryRes = practicePaperMapper.getPaperRecordByPaper(paperId);
		paperRecordSummaryRes.setPaperTime(TimeUtil.format10(TimeUtil.toDate1(paperRecordSummaryRes.getPaperTime())));
		if (paperRecordSummaryRes.getIsFinish()) {
			paperRecordSummaryRes.setAccuracyRate(paperRecordSummaryRes.getRight() * 100 / paperRecordSummaryRes.getCount());
			paperRecordSummaryRes.setWrong(paperRecordSummaryRes.getWrong() + paperRecordSummaryRes.getNotStart());
		}
		PaperRecordDetailRes paperRecordDetailRes = BeanUtils.copy(paperRecordSummaryRes, PaperRecordDetailRes.class);
		List<String> practiceRefIds = paperPracticeTheoryMapper.selectPracticeRefIdsByPaperId(paperId);
		List<WebTheoryBo> webTheoryBoList = paperPracticeTheoryService.getPracticeDetail(practiceRefIds);
		webTheoryBoList.forEach(webTheoryBo -> {
			if (webTheoryBo.getSolved().equals(PracticeSolved.SolvedStatus.NOT_START.getCode())) {
				webTheoryBo.setSolved(PracticeSolved.SolvedStatus.TRIED.getCode());
			}
		});
		paperRecordDetailRes.setPracticeList(webTheoryBoList);
		return paperRecordDetailRes;
	}

	@Override
	public PracticePaper practiceAgain(String paperId) {
		PracticePaper practicePaper = practicePaperMapper.selectById(paperId);
		practicePaper.setId(null);
		practicePaper.setResourceId(null);
		practicePaper.setIsFinish(false);
		practicePaper.setUpdateTime(null);
		practicePaper.setCreateTime(null);

		LambdaQueryWrapper<PaperPracticeTheory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(PaperPracticeTheory::getPaperId, paperId);
		List<PaperPracticeTheory> paperPracticeTheoryList = paperPracticeTheoryMapper.selectList(lambdaQueryWrapper);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				practicePaperMapper.insert(practicePaper);
				for (PaperPracticeTheory paperPracticeTheory : paperPracticeTheoryList){
					paperPracticeTheory.setResourceId(null);
					paperPracticeTheory.setId(null);
					paperPracticeTheory.setPaperId(practicePaper.getResourceId());
					paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
					paperPracticeTheory.setSubmitAnswer(null);
					paperPracticeTheory.setCreateTime(null);
					paperPracticeTheory.setUpdateTime(null);
				}
				paperPracticeTheoryService.saveBatch(paperPracticeTheoryList);
			}
		});
		return practicePaper;
	}

	/**
	 * 根据解题状态和用户过滤习题
	 * @param status
	 * @param authContext
	 * @return
	 */
	private List<String> getPracticeIdsByQueryAndSolved(List<Integer> status, AuthContext authContext) {
		List<String> practiceIds = new ArrayList<>();
		// 过滤无权限习题集
		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authSetIds)) {
			return null;
		}

		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authSetIds.retainAll(setIds);

		practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
				authContext.getIsWeb());

		if (CollectionUtils.isEmpty(practiceIds)) {
			return new ArrayList<>();
		}

		List<String> solvedIds = practiceSolvedMapper.selectUserAndSolved(
				authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
				CommonConstant.TRUE, false);
		List<String> triedIds = practiceSolvedMapper.selectUserAndSolved(
				authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
				CommonConstant.FALSE, false);

		solvedIds.retainAll(practiceIds);
		triedIds.retainAll(practiceIds);

		practiceIds.removeAll(solvedIds);
		practiceIds.removeAll(triedIds);
		List<String> notStartIds = new ArrayList<>(practiceIds);

		practiceIds.clear();

		if (status.contains(PracticeSolved.SolvedStatus.TRIED.getCode())) {
			practiceIds.addAll(triedIds);
		}
		if (status.contains(PracticeSolved.SolvedStatus.SOLVED.getCode())) {
			practiceIds.addAll(solvedIds);
		}
		if (status.contains(PracticeSolved.SolvedStatus.NOT_START.getCode())) {
			practiceIds.addAll(notStartIds);
		}

		return practiceIds;
	}

}
