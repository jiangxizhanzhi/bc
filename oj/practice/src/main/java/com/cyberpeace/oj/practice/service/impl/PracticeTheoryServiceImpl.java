package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.controller.req.WebTheoryRecordReq;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryCountBo;
import com.cyberpeace.oj.practice.service.externalbo.TheorySimpleBo;
import com.cyberpeace.oj.practice.service.md.MdTheoryParser;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;
import com.cyberpeace.oj.practice.service.md.TheoryBaseMd;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PracticeTheoryServiceImpl extends ServiceImpl<PracticeTheoryMapper, PracticeTheory> implements PracticeTheoryService {

	private Integer MAX_CONTENT_LENGTH;

	private Integer MAX_CATEGORY_LENGTH;

	private final Boolean IS_COLLECTED = Boolean.FALSE;

	@PostConstruct
	private void init() {
		MAX_CONTENT_LENGTH = Integer
				.parseInt(ConfigUtil.getContextProperty("oj.practice.content.max.length"));

		MAX_CATEGORY_LENGTH = Integer
				.parseInt(ConfigUtil.getContextProperty("oj.practice.category.max.length"));
	}

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Resource(name = "theory")
	private TheoryEvaluateHandler theoryEvaluateHandler;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	@Autowired
	private PracticeMistakeService practiceMistakeService;

	@Override
	public IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext) {

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
		if (CollectionUtils.isEmpty(authSetIds)
				|| (StringUtils.isNotBlank(listQuery.getPracticeSetId())
				&& !authSetIds.contains(listQuery.getPracticeSetId()))) {
			return null;
		}

		TheoryListQuery theoryListQuery = BeanUtils.copy(listQuery, TheoryListQuery.class);
		theoryListQuery.setQuestionType(listQuery.getTheoryType());
		// 根据习题集过滤习题编号
		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())) {
			authSetIds.clear();
			authSetIds.add(listQuery.getPracticeSetId());
		}
		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
				authContext.getIsWeb());

		if (CollectionUtils.isEmpty(practiceIds)) {
			return null;
		}
		theoryListQuery.setResourceIds(practiceIds);

		// 答题记录过滤习题
		if (authContext.getIsWeb() && null != listQuery.getSolved()) {
			List<String> solvedIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
					CommonConstant.TRUE, IS_COLLECTED);
			List<String> triedIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
					CommonConstant.FALSE, IS_COLLECTED);

			// 求交集 空则返回为空
			if (listQuery.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode())) {
				theoryListQuery.getResourceIds().retainAll(solvedIds);
			} else if (listQuery.getSolved().equals(PracticeSolved.SolvedStatus.TRIED.getCode())){
				theoryListQuery.getResourceIds().retainAll(triedIds);
			} else {
				theoryListQuery.getResourceIds().removeAll(solvedIds);
				theoryListQuery.getResourceIds().removeAll(triedIds);
			}

			// 资源数据判空
			if (CollectionUtils.isEmpty(theoryListQuery.getResourceIds())) {
				return null;
			}
		}

		IPage<PracticeTheory> iPage = practiceTheoryMapper.pageByQuery(page, theoryListQuery,
				authContext.getIsWeb());

		List<String> userIdList = new ArrayList<>();
		List<String> practiceIdList = new ArrayList<>();
		for (PracticeTheory record : iPage.getRecords()) {
			userIdList.add(record.getCreateUserId());
			practiceIdList.add(record.getPracticeSetId());
		}

		Map<String, String> userInfoMap = StreamApiUtils.toMap(remoteUserService.getByIdList(userIdList), UserInfo::getUserId, UserInfo::getUserName);
		Map<String, String> practiceSetMap = StreamApiUtils.toMap(practiceSetMapper.selectBatchIds(practiceIdList), PracticeSet::getResourceId, PracticeSet::getName);

		return iPage.convert(practiceTheory -> {
			PracticeTheoryListBo theoryListBo = BeanUtils.copy(practiceTheory,PracticeTheoryListBo.class);

			if (authContext.getIsWeb()) {
				// 解题状态设置
				if (null != listQuery.getSolved()) {
					theoryListBo.setSolved(listQuery.getSolved());
				} else {
					PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
							authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
							practiceTheory.getResourceId(), IS_COLLECTED);
					if (practiceSolved == null) {
						theoryListBo.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
					}else {
						theoryListBo.setSolved(practiceSolved.getSolved() ?
								PracticeSolved.SolvedStatus.SOLVED.getCode() : PracticeSolved.SolvedStatus.TRIED.getCode());
					}
				}
			}

			String userName= userInfoMap.get(practiceTheory.getCreateUserId());
			theoryListBo.setCreateUserName(null != userName ? userName : "");
			String practiceSetName=	practiceSetMap.get(practiceTheory.getPracticeSetId());
			theoryListBo.setPracticeSetName(null != practiceSetName ? practiceSetName : "");
			return theoryListBo;
		});
	}

	private List<CategoryProgressRes> getAllPracticeTheoryByAuth() {

		TheoryListQueryBo listQuery = new TheoryListQueryBo();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

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
		if (CollectionUtils.isEmpty(authSetIds)
				|| (StringUtils.isNotBlank(listQuery.getPracticeSetId())
				&& !authSetIds.contains(listQuery.getPracticeSetId()))) {
			return null;
		}

		TheoryListQuery theoryListQuery = BeanUtils.copy(listQuery, TheoryListQuery.class);
		theoryListQuery.setQuestionType(listQuery.getTheoryType());
		// 根据习题集过滤习题编号
		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())) {
			authSetIds.clear();
			authSetIds.add(listQuery.getPracticeSetId());
		}
		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
				authContext.getIsWeb());

		if (CollectionUtils.isEmpty(practiceIds)) {
			return null;
		}
		theoryListQuery.setResourceIds(practiceIds);

		// 答题记录过滤习题
		if (authContext.getIsWeb() && null != listQuery.getSolved()) {
			List<String> solvedIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
					CommonConstant.TRUE, IS_COLLECTED);
			List<String> triedIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
					CommonConstant.FALSE, IS_COLLECTED);

			// 求交集 空则返回为空
			if (listQuery.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode())) {
				theoryListQuery.getResourceIds().retainAll(solvedIds);
			} else if (listQuery.getSolved().equals(PracticeSolved.SolvedStatus.TRIED.getCode())) {
				theoryListQuery.getResourceIds().retainAll(triedIds);
			} else {
				theoryListQuery.getResourceIds().removeAll(solvedIds);
				theoryListQuery.getResourceIds().removeAll(triedIds);
			}

			// 资源数据判空
			if (CollectionUtils.isEmpty(theoryListQuery.getResourceIds())) {
				return null;
			}
		}
		return practiceTheoryMapper.selectByGroup(theoryListQuery,
				authContext.getIsWeb(), authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode());
	}

	@Override
	public WebTheoryBo selectById(String resourceId, String userId, Boolean isCollected) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(resourceId);
		if (null == practiceTheory) {
			log.error("Theory practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		WebTheoryBo webTheoryBo = BeanUtils.copy(practiceTheory, WebTheoryBo.class);

		Boolean result = resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.THEORY.getCode(), resourceId);
		webTheoryBo.setIsCollection(null == result ? CommonConstant.FALSE : result);

		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
				.getCode().intValue()) {
			webTheoryBo.setBlankCount(JsonUtil
					.toJsonObjectArray(practiceTheory.getBlank(), BlankAnswer.class).size());
		}

		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.THEORY.getCode(), resourceId, isCollected);

		answerToNull(webTheoryBo, practiceSolved);

		PracticeSet practiceSet = practiceSetMapper.selectById(webTheoryBo.getPracticeSetId());
		webTheoryBo.setPracticeSetName(practiceSet.getName());

		return webTheoryBo;
	}

	/**
	 * 答案置空
	 *
	 * @param webTheoryBo
	 * @param practiceSolved
	 */
	private void answerToNull(WebTheoryBo webTheoryBo, PracticeSolved practiceSolved) {

		if (null == practiceSolved) {
			answerToNull(webTheoryBo);
			webTheoryBo.setSolved(PracticeSolved.SolvedStatus.NOT_START.getCode());
		} else {
			PracticeSolvedDetailBo detailBo = new PracticeSolvedDetailBo();
			if (practiceSolved.getSolved()) {
				detailBo.setCorrectAnswer(webTheoryBo.getAnswer());
				detailBo.setIsSolved(practiceSolved.getSolved());
				detailBo.setSubmitAnswer(practiceSolved.getSubmitAnswer());
				if (webTheoryBo.getTheoryType().intValue() == PracticeTheory.TheoryType.SHORT_ANSWER
						.getCode().intValue()) {
					detailBo.setKeyword(webTheoryBo.getKeyword());
				}
				webTheoryBo.setResolvedDetail(detailBo);
				webTheoryBo.setSolved(PracticeSolved.SolvedStatus.SOLVED.getCode());
			} else {
				PracticeNoSolvedBo practiceNoSolvedBo = new PracticeNoSolvedBo();
				practiceNoSolvedBo.setIsSolved(practiceSolved.getSolved());
				answerToNull(webTheoryBo);
				webTheoryBo.setResolvedDetail(practiceNoSolvedBo);
				webTheoryBo.setSolved(PracticeSolved.SolvedStatus.TRIED.getCode());
			}
		}

	}

	/**
	 * 答案置空
	 *
	 * @param webTheoryBo
	 */
	private void answerToNull(WebTheoryBo webTheoryBo) {
		webTheoryBo.setAnswer(null);
		webTheoryBo.setBlank(null);
		webTheoryBo.setKeyword(null);
	}

	@Override
	public EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer,
			Boolean isCollected) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(theoryId);
		if (null == practiceTheory) {
			log.info("Theory practice not found! practice_id=[{}]", theoryId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 判断是否已完成答题
		PracticeSolved solvedPractice = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.THEORY.getCode(), theoryId, isCollected);
		if (null != solvedPractice && solvedPractice.getSolved()) {
			return BeanUtils.copy(solvedPractice, EvaluateResult.class);
		}

		EvaluateResult evaluateResult = theoryEvaluateHandler.evaluateByBusiness(userId,
				practiceTheory, userAnswer);

		// 将评估记录存储
		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(practiceTheory.getResourceId());
		practiceSolved.setSubmitUserId(userId);
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
		practiceSolved.setSubmitAnswer(userAnswer);

		practiceSolved.setCollection(resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.THEORY.getCode(), practiceTheory.getResourceId()));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				//更新错题集状态
				if (evaluateResult.getSolved()) {
					practiceMistakeService.addCorrect(userId, practiceTheory.getResourceId(), PracticeCategory.PracticeType.THEORY.getCode());
				} else {
					practiceMistakeService.addIncorrect(userId, practiceTheory.getResourceId(), PracticeCategory.PracticeType.THEORY.getCode());
				}
				//更新题目状态
				try {
					practiceSolvedMapper.insert(practiceSolved);
				} catch (DuplicateKeyException e) {
					practiceSolvedMapper.updateSolved(practiceSolved);
				}
			}
		});


		return evaluateResult;
	}

	@Override
	public Integer create(PracticeTheory practiceTheory, AuthContext authContext) {

		// 数据校验
		practiceValidateHandler.validateTheoryContentExist(practiceTheory.getPracticeSetId(),
				practiceTheory.getContent());
		practiceValidateHandler.validateCategoryType(practiceTheory.getCategoryId(),
				PracticeCategory.PracticeType.THEORY.getCode());
		PracticeSet practiceSet = practiceValidateHandler.validateSetType(
				practiceTheory.getPracticeSetId(), PracticeSet.PracticeType.THEORY.getCode());
		practiceValidateHandler.validateFillBlankKeyword(practiceTheory.getTheoryType() ,practiceTheory.getKeyword());
		// 基本数据设置
		practiceTheory.setBuiltin(CommonConstant.FALSE);
		practiceTheory.setScore(CommonConstant.ZERO.doubleValue());
		practiceSet.setPracticeCount(practiceSet.getPracticeCount() + CommonConstant.ONE);

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				practiceSetMapper.updateById(practiceSet);
				return practiceTheoryMapper.insert(practiceTheory);
			}
		});
	}

	@Override
	public int updateTheory(PracticeTheory practiceTheory, AuthContext authContext) {

		// 数据校验
		practiceValidateHandler.validateTheoryContentExistExcludeItself(practiceTheory.getPracticeSetId(),
				practiceTheory.getContent(), practiceTheory);
		practiceValidateHandler.validateCategoryType(practiceTheory.getCategoryId(),
				PracticeCategory.PracticeType.THEORY.getCode());
		practiceValidateHandler.validateSetType(practiceTheory.getPracticeSetId(),
				PracticeSet.PracticeType.THEORY.getCode());
		practiceValidateHandler.validateFillBlankKeyword(practiceTheory.getTheoryType() ,practiceTheory.getKeyword());
		PracticeTheory originPractice = practiceTheoryMapper.selectById(practiceTheory.getResourceId());
		String originChangeBo = BeanUtils.copy(originPractice, TheoryChangeBo.class).toString();
		String changeBo = BeanUtils.copy(practiceTheory, TheoryChangeBo.class).toString();
		// 数据更新
		final int[] result = new int[1];
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				//如果对应的几个字段被修改则删除对应的错题记录和做题记录
				if (!changeBo.equals(originChangeBo)) {
					LambdaQueryWrapper<PracticeSolved> practiceSolvedLambdaQueryWrapper = new LambdaQueryWrapper<>();
					practiceSolvedLambdaQueryWrapper.eq(PracticeSolved::getPracticeId, practiceTheory.getResourceId());
					practiceSolvedMapper.delete(practiceSolvedLambdaQueryWrapper);
					LambdaQueryWrapper<PracticeMistake> practiceMistakeLambdaQueryWrapper = new LambdaQueryWrapper<>();
					practiceMistakeLambdaQueryWrapper.eq(PracticeMistake::getPracticeId, practiceTheory.getResourceId());
					practiceMistakeMapper.delete(practiceMistakeLambdaQueryWrapper);
				}
				result[0] = practiceTheoryMapper.updateById(practiceTheory);
			}
		});

		return result[0];
	}

	@Override
	public List<String> selectPracticeListIds(AuthContext authContext, String practiceSetId) {

		List<String> setIds ;
		if (practiceSetId == null) {
			setIds = practiceSetMapper.selectByAuthContext(authContext);
			if (CollectionUtils.isEmpty(setIds)) {
				return Lists.newArrayList();
			}
		} else {
			setIds = Arrays.asList(practiceSetId);
		}

		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(setIds);
		List<String> publicIds = practiceSets.stream()
				.filter(practiceSet -> practiceSet.getIsPublic())
				.map(practiceSet -> practiceSet.getResourceId()).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(publicIds)) {
			return Lists.newArrayList();
		}

		return practiceTheoryMapper.selectBySetIds(publicIds, authContext.getIsWeb());
	}

	@Override
	public Integer batchDelete(List<String> resourceIds) {
		String practiceId = resourceIds.get(CommonConstant.ZERO);
		PracticeTheory theory = practiceTheoryMapper.selectById(practiceId);

		// 习题集数据更新
		PracticeSet practiceSet = practiceValidateHandler
				.validatePracticeSetExist(theory.getPracticeSetId());
		practiceSet.setPracticeCount(null == practiceSet.getPracticeCount()
				|| practiceSet.getPracticeCount() <= resourceIds.size() ? CommonConstant.ZERO
						: practiceSet.getPracticeCount() - resourceIds.size());

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				practiceMistakeMapper.deleteByPracticeIds(resourceIds, null);
				practiceSolvedMapper.deleteByPracticeIds(resourceIds);
				practiceSetMapper.updateById(practiceSet);
				return practiceTheoryMapper.deleteBatchIds(resourceIds);
			}
		});
	}

	@Override
	public PracticeTheory loadPracticeTheory(String practiceSetId, PracticeTheoryDumpLoadBo dumpBo,
			String userId) {
		// 重复则不导入
		String practiceId = practiceTheoryMapper.selectByContent(practiceSetId,
				dumpBo.getContent());
		if (StringUtils.isNotBlank(practiceId)) {
			log.error("Practice theory exist! practice_id=[{}], practice_name=[{}]", practiceId,
					dumpBo.getContent());
			return null;
		}

		PracticeTheory practiceTheory = BeanUtils.copy(dumpBo, PracticeTheory.class);
		practiceTheory.setPracticeSetId(practiceSetId);
		practiceTheory.setBuiltin(CommonConstant.TRUE);
		practiceTheory.setResourceId(IDGeneratorUtil.getID());
		practiceTheory.setCreateUserId(userId);
		// 增加判断 添加默认值 防止前端无法格式化数据
		if (StringUtils.isBlank(dumpBo.getKeyword())) {
			practiceTheory.setKeyword(CommonConstant.EMPTY_LIST_JSON);
		}
		if (StringUtils.isBlank(dumpBo.getKnowledge())) {
			practiceTheory.setKnowledge(CommonConstant.EMPTY_LIST_JSON);
		}
		if (StringUtils.isBlank(dumpBo.getOption())) {
			practiceTheory.setOption(CommonConstant.EMPTY_OBJECT);
		}

		// 分类数据
		String categoryId = practiceCategoryService.selectCategoryOrCreate(dumpBo.getCategoryName(),
				PracticeCategory.PracticeType.THEORY.getCode(), userId);
		practiceTheory.setCategoryId(categoryId);

		return practiceTheory;
	}

	@Override
	public PracticeTheoryDumpLoadBo dumpTheory(PracticeTheory practiceTheory) {
		PracticeTheoryDumpLoadBo dumpLoadBo = BeanUtils.copy(practiceTheory,
				PracticeTheoryDumpLoadBo.class);

		// 分类数据处理
		PracticeCategory category = practiceValidateHandler
				.validateCategoryExist(practiceTheory.getCategoryId());
		dumpLoadBo.setCategoryName(category.getName());
		return dumpLoadBo;
	}

	@Override
	public void createMpLog(String practiceId, LogContent logContent) {
		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(practiceId);
		String content = LogContent.THEORY_FORMAT.format(practiceTheory.getContent(), logContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.THEORY,
				OperationType.EXERCISE, LogContent.THEORY.getMsg(), content);
	}

	@Override
	public ExternalTheoryCountBo typeCount(TheoryListQueryBo theoryListQueryBo,
			AuthContext authContext) {

		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return new ExternalTheoryCountBo();
		}

		String searchSet = theoryListQueryBo.getPracticeSetId();
		if (StringUtils.isNotBlank(searchSet)) {
			if (setIds.contains(searchSet)) {
				setIds.clear();
				setIds.add(searchSet);
			} else {
				return new ExternalTheoryCountBo();
			}
		}

		// 设置查询条件
		TheoryListQuery listQuery = BeanUtils.copy(theoryListQueryBo, TheoryListQuery.class);
		listQuery.setPracticeSetIds(setIds);

		// 数据查询转换
		List<TheorySimpleBo> bos = practiceTheoryMapper.selectSimple(listQuery, true);
		Map<Integer, List<TheorySimpleBo>> typeMap = StreamApiUtils.groupBy(bos,
				TheorySimpleBo::getTheoryType);
		ExternalTheoryCountBo countBo = new ExternalTheoryCountBo(typeMap);
		return countBo;
	}

	@Override
	public MdTheoryPractice exportMdTheoryPractice(String path, List<PracticeTheory> theories) {

		MdTheoryPractice mdTheoryPractice = new MdTheoryPractice();

		List<TheoryBaseMd> singleList = Lists.newArrayList();

		List<TheoryBaseMd> multipleList = Lists.newArrayList();

		List<TheoryBaseMd> blankList = Lists.newArrayList();

		List<TheoryBaseMd> anwserList = Lists.newArrayList();

		List<TheoryBaseMd> judgeList = Lists.newArrayList();

		for (PracticeTheory theory : theories) {
			Integer code = theory.getTheoryType();
			TheoryBaseMd theoryBaseMd = BeanUtils.copy(theory, TheoryBaseMd.class);

			String difficulty = EnumUtils.getEnumDescByCode(theory.getDifficulty(),
					Difficulty.class);
			theoryBaseMd.setDifficulty(difficulty);
			// 答案增加之前两个空格和换行
			theoryBaseMd.setAnswer("  " + theory.getAnswer() + System.lineSeparator());

			// 分类数据,有可能为空
			String category = "";
			try {
				CategoryListBo categoryListBo = practiceCategoryService
						.selectById(theory.getCategoryId(), false);
				category = categoryListBo.getName();

			} catch (Exception e) {
				log.error("theory category is null . theoryId=[{}]", theory.getResourceId());

			}

			theoryBaseMd.setCategory(category);

			// 单选需要获取选项内容

			if (PracticeTheory.TheoryType.SINGLE.getCode().equals(code)) {
				changeTitleToTheoryBaseMd(theory, theoryBaseMd);
				singleList.add(theoryBaseMd);
			}

			// 多选需要获取选项内容,修改答案
			if (PracticeTheory.TheoryType.MULTIPLE.getCode().equals(code)) {
				changeTitleToTheoryBaseMd(theory, theoryBaseMd);

				String anwser = theory.getAnswer().replace("|", "");

				theoryBaseMd.setAnswer(anwser);
				multipleList.add(theoryBaseMd);

			}

			// 填空需要修改答案
			if (PracticeTheory.TheoryType.FILL_BLANK.getCode().equals(code)) {

				List<BlankAnswer> blankAnswers = JsonUtil.toJsonObjectArray(theory.getAnswer(),
						BlankAnswer.class);

				String answer = "";

				for (int i = 0; i < blankAnswers.size(); i++) {
					String answerValue = blankAnswers.get(i).getValue();
					answer += "  " + (i + 1) + ". " + answerValue + System.lineSeparator();

				}

				theoryBaseMd.setAnswer(answer.trim());
				blankList.add(theoryBaseMd);

			}
			// 判断需要修改答案
			if (PracticeTheory.TheoryType.JUDGMENT.getCode().equals(code)) {

				String answer = theory.getAnswer().equals("A") ? "T" : "F";

				theoryBaseMd.setAnswer(answer);

				judgeList.add(theoryBaseMd);

			}

			// 简答
			if (PracticeTheory.TheoryType.SHORT_ANSWER.getCode().equals(code)) {

				anwserList.add(theoryBaseMd);

			}
			theoryBaseMd
					.setTitle(MdTheoryParser.setRelativeFilePath(path, theoryBaseMd.getTitle()));

		}
		mdTheoryPractice.setSingleList(singleList);
		mdTheoryPractice.setMultipleList(multipleList);
		mdTheoryPractice.setBlankList(blankList);
		mdTheoryPractice.setJudgeList(judgeList);
		mdTheoryPractice.setAnwserList(anwserList);

		return mdTheoryPractice;
	}

	@Override
	public ProgressStatisticsRes getProgressStatistics(String practiceSetId) {
		ProgressStatisticsRes progressStatisticsRes = new ProgressStatisticsRes();
		List<PracticeTheoryListBo> practiceTheoryListBoList = getAllPracticeTheoryByAuthAndSetId(practiceSetId);
		ProgressProportion simple = new ProgressProportion();
		simple.setCount((int) practiceTheoryListBoList.stream()
													  .filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.PRIMARY.getCode())
															  && (practiceTheoryListBo.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode()) ||
															  practiceTheoryListBo.getSolved().equals(PracticeSolved.SolvedStatus.TRIED.getCode()))).count());
		simple.setSum((int) practiceTheoryListBoList.stream()
													.filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.PRIMARY.getCode())).count());
		progressStatisticsRes.setSimple(simple);

		ProgressProportion medium = new ProgressProportion();
		medium.setCount((int) practiceTheoryListBoList.stream()
													  .filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.MIDDLE.getCode())
															  && (practiceTheoryListBo.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode()) ||
															  practiceTheoryListBo.getSolved().equals(PracticeSolved.SolvedStatus.TRIED.getCode()))).count());
		medium.setSum((int) practiceTheoryListBoList.stream()
													.filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.MIDDLE.getCode())).count());
		progressStatisticsRes.setMedium(medium);

		ProgressProportion difficulty = new ProgressProportion();
		difficulty.setCount((int) practiceTheoryListBoList.stream()
													  .filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.HIGH.getCode())
															  && (practiceTheoryListBo.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode()) ||
															  practiceTheoryListBo.getSolved().equals(PracticeSolved.SolvedStatus.TRIED.getCode()))).count());
		difficulty.setSum((int) practiceTheoryListBoList.stream()
													.filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.HIGH.getCode())).count());
		progressStatisticsRes.setDifficulty(difficulty);

		ProgressProportion sum = new ProgressProportion();
		sum.setSum(simple.getSum() + medium.getSum() + difficulty.getSum());
		sum.setCount(simple.getCount() + medium.getCount() + difficulty.getCount());
		progressStatisticsRes.setSum(sum);
		return progressStatisticsRes;
	}

	@Override
	public List<CategoryProgressRes> getProgressByCategory() {
		List<CategoryProgressRes> categoryProgressResList = new ArrayList<>();
		List<CategoryProgressRes> allPracticeTheoryByAuth = getAllPracticeTheoryByAuth();
		Map<String, CategoryProgressRes> categoryProgressResMap = StreamApiUtils.toMap(allPracticeTheoryByAuth, CategoryProgressRes::getCategoryId, item -> item);
		List<PracticeCategory> practiceCategoryList = practiceCategoryMapper.listByTypeAndName(PracticeCategory.PracticeType.THEORY.getCode(), null);
		for (PracticeCategory practiceCategory : practiceCategoryList) {
			CategoryProgressRes categoryProgressRes = categoryProgressResMap.get(practiceCategory.getResourceId());
			categoryProgressResList.add(new CategoryProgressRes(practiceCategory.getName(),
					practiceCategory.getResourceId(),
					categoryProgressRes == null ? 0 : categoryProgressRes.getCount(),
					categoryProgressRes == null ? 0 : categoryProgressRes.getSum()));
		}
		return categoryProgressResList;
	}


	@Override
	public List<PracticeTheoryListBo> getPracticeRecord(WebTheoryRecordReq webTheoryRecordReq) {
		//获取所有可见的习题id
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), true);
		List<String> practiceIds = getPracticeIdsByUserAndSet(authContext, null);
		//获取练习记录
		List<PracticeTheoryListBo> practiceTheoryList = practiceSolvedMapper.selectPracticeTheoryRecord(UserInfoContext.getUserContext().getUserId(), practiceIds,
				TimeUtil.toDate2(webTheoryRecordReq.getStartTime()), TimeUtil.toDate2(webTheoryRecordReq.getEndTime()), webTheoryRecordReq.getSolved());
		//设置习题集名称
		List<String> practiceSetIds = StreamApiUtils.map(practiceTheoryList, PracticeTheoryListBo::getPracticeSetId);
		if (practiceSetIds.size() == 0) {
			return new ArrayList<>();
		}
		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(practiceSetIds);
		Map<String, String> practiceSetIdNameMap = StreamApiUtils.toMap(practiceSets, PracticeSet::getResourceId, PracticeSet::getName);
		for (PracticeTheoryListBo practiceTheoryListBo : practiceTheoryList) {
			practiceTheoryListBo.setPracticeSetName(practiceSetIdNameMap.get(practiceTheoryListBo.getPracticeSetId()));
		}
		return practiceTheoryList;
	}

	/**
	 * 获取该用户所有可见的习题id
	 * @param authContext
	 * @param practiceSetId
	 * @return
	 */
	@Override
	public List<String> getPracticeIdsByUserAndSet(AuthContext authContext, String practiceSetId) {
		// 过滤无权限习题集
		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authSetIds)) {
			return new ArrayList<>();
		}

		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authSetIds.retainAll(setIds);
		if (CollectionUtils.isEmpty(authSetIds)
				|| (StringUtils.isNotBlank(practiceSetId)
				&& !authSetIds.contains(practiceSetId))) {
			return new ArrayList<>();
		}

		// 根据习题集过滤习题编号
		if (StringUtils.isNotBlank(practiceSetId)) {
			authSetIds.clear();
			authSetIds.add(practiceSetId);
		}
		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
				authContext.getIsWeb());

		if (CollectionUtils.isEmpty(practiceIds)) {
			return new ArrayList<>();
		}
		return practiceIds;
	}

	private void changeTitleToTheoryBaseMd(PracticeTheory theory, TheoryBaseMd theoryBaseMd) {
		String[] split = theoryBaseMd.getTitle().split("\n");
		List<String> content = Arrays.asList(split);
		Map<String, String> optionMap = JsonUtil.toJsonObject(theory.getOption(), TreeMap.class);
		String title = "";

		title += "    " + theory.getTitle() + System.lineSeparator() + System.lineSeparator();

		Iterator iterator = optionMap.keySet().iterator();

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = optionMap.get(key).trim();

			title += "    " + key + ". " + value + System.lineSeparator() + System.lineSeparator();
		}

		theoryBaseMd.setTitle(title);

	}

	private List<PracticeTheoryListBo> getAllPracticeTheoryByAuthAndSetId (String practiceSetId){
		PageReqBase pageReqBase = new PageReqBase();
		pageReqBase.setPer_page(2147483647);
		TheoryListQueryBo listQueryBo = new TheoryListQueryBo();
		listQueryBo.setPracticeSetId(practiceSetId);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		IPage<PracticeTheoryListBo> practiceTheoryListBoIPage = pageByQuery(pageReqBase.toPageParam(), listQueryBo, authContext);
		if (practiceTheoryListBoIPage == null) {
			return new ArrayList<>();
		}
		return practiceTheoryListBoIPage.getRecords();
	}
}
