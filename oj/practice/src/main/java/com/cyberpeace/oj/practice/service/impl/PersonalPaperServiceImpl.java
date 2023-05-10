package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.controller.req.PersonalPaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.*;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PaperPracticeTheoryService;
import com.cyberpeace.oj.practice.service.PersonalPaperService;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Service
public class PersonalPaperServiceImpl extends ServiceImpl<PersonalPaperMapper, PersonalPaper> implements PersonalPaperService {

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PersonalPaperMapper personalPaperMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PaperPracticeTheoryService paperPracticeTheoryService;

	@Autowired
	private PaperPracticeTheoryMapper paperPracticeTheoryMapper;

	@Override
	public PersonalPaperCreateReq getLastPaperSetting() {
		String userId = UserInfoContext.getUserContext().getUserId();
		PersonalPaper personalPaper = personalPaperMapper.getLastPaperByUserId(userId);
		PersonalPaperCreateReq personalPaperCreateReq = new PersonalPaperCreateReq();
		if (personalPaper == null) {
			personalPaperCreateReq.setCount(5);
			personalPaperCreateReq.setDifficulty(Arrays.asList(Difficulty.PRIMARY.getCode()));
			personalPaperCreateReq.setDirection(new ArrayList<>());
			personalPaperCreateReq.setStatus(Arrays.asList(PracticeSolved.SolvedStatus.NOT_START.getCode()));
			personalPaperCreateReq.setType(Arrays.asList(PracticeTheory.TheoryType.SINGLE.getCode()));
			return personalPaperCreateReq;
		}
		personalPaperCreateReq.setType(JsonUtil.toJsonObjectArray(personalPaper.getType(), Integer.class));
		personalPaperCreateReq.setCount(personalPaper.getCount());
		personalPaperCreateReq.setDirection(JsonUtil.toJsonObjectArray(personalPaper.getDirection(), String.class));
		personalPaperCreateReq.setDifficulty(JsonUtil.toJsonObjectArray(personalPaper.getDifficulty(), Integer.class));
		personalPaperCreateReq.setStatus(JsonUtil.toJsonObjectArray(personalPaper.getPracticeStatus(), Integer.class));
		return personalPaperCreateReq;
	}

	@Override
	public PersonalPaper createPaper(PersonalPaperCreateReq personalPaperCreateReq) {
		PersonalPaper personalPaper = new PersonalPaper();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), true);
		List<String> practiceIds = getPracticeIdsByQueryAndSolved(personalPaperCreateReq.getStatus(), authContext);
		List<PracticeTheory> practiceTheorySumList = practiceTheoryMapper.selectAllByCreatePaper(personalPaperCreateReq.getDifficulty(),
				personalPaperCreateReq.getType(), personalPaperCreateReq.getDirection(), practiceIds);
		if (practiceTheorySumList.size() < personalPaperCreateReq.getCount()) {
			throw new BusinessException(OjBusinessErrorCode.NOT_ENOUGH_PRACTICE);
		}
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
		int sum = personalPaperCreateReq.getCount();
		int size = practiceTheoryListByDirection.size();
		for (List<PracticeTheory> practiceTheories : practiceTheoryListByDirection) {
			int count = sum/size;
			sum -= count;
			size--;
			Collections.shuffle(practiceTheories);
			practiceTheoryList.addAll(practiceTheories.subList(0, count));
		}
		personalPaper.setCount(personalPaperCreateReq.getCount());
		personalPaper.setIsFinish(false);
		personalPaper.setDifficulty(JsonUtil.toJsonString(personalPaperCreateReq.getDifficulty()));
		personalPaper.setDirection(JsonUtil.toJsonString(personalPaperCreateReq.getDirection()));
		personalPaper.setPracticeStatus(JsonUtil.toJsonString(personalPaperCreateReq.getStatus()));
		personalPaper.setType(JsonUtil.toJsonString(personalPaperCreateReq.getType()));
		personalPaper.setUserId(UserInfoContext.getUserContext().getUserId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				personalPaperMapper.insert(personalPaper);
				List<PaperPracticeTheory> paperPracticeTheories = new ArrayList<>();
				for (PracticeTheory practiceTheory : practiceTheoryList){
					PaperPracticeTheory paperPracticeTheory = new PaperPracticeTheory();
					paperPracticeTheory.setPaperId(personalPaper.getResourceId());
					paperPracticeTheory.setOriginId(practiceTheory.getResourceId());
					paperPracticeTheory.setSnapshot(JsonUtil.toJsonString(practiceTheory));
					paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
					paperPracticeTheories.add(paperPracticeTheory);
				}
				paperPracticeTheoryService.saveBatch(paperPracticeTheories);
			}
		});
		return personalPaper;
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
		return personalPaperMapper.getPaperRecord(userId);
	}

	@Override
	public Map<String, List<PaperRecordSummaryRes>> getAllPaperRecord(Boolean isFinish) {
		Map<String, List<PaperRecordSummaryRes>> recordListMap = new HashMap<>();
		List<PaperRecordSummaryRes> paperRecordSummaryResList = personalPaperMapper.getAllPaperRecord(UserInfoContext.getUserContext().getUserId());
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
		return recordListMap;
	}

	@Override
	public PaperRecordDetailRes getPaperRecordDetail(String paperId) {
		PaperRecordSummaryRes paperRecordSummaryRes = personalPaperMapper.getPaperRecordByPaper(paperId);
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
