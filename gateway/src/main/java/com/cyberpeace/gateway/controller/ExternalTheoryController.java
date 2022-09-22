package com.cyberpeace.gateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.gateway.service.bo.ExternalPracticeCategoryBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeSetBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.practice.controller.req.external.PracticeRandomChoiceReq;
import com.cyberpeace.oj.practice.controller.req.external.PracticeTheoryListReq;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-理论习题相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/theory")
public class ExternalTheoryController {

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private PracticeSetService practiceSetService;

	/**
	 * 接口认证(key获取方式后续可能调整)
	 * @param key headers: OPT-OJ-KEY
	 */
	private void allowAccess(String key) {
		if (!key.equals(WebConstant.OJ_HEADER_KEY_VALUE)) {
			throw new UnauthorizedException("secretKey is failed.");
		}
	}

	/**
	 * 校验用户
	 * @param userId
	 */
	private UserInfo validateUser(String userId) {
		UserInfo userInfo = remoteUserService.getById(userId);
		if (null == userInfo) {
			log.error("User does not exist！user_id=[{}]", userId);
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}

		return userInfo;
	}

	@ApiOperation("理论基础习题集")
	@GetMapping("/practice-set/list")
	public BaseRsp<List<ExternalPracticeSetBo>> getPracticeSetList(
			@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key,
			@RequestParam("userId") @NotBlank(
					message = ErrorCode.USER_INFO_MISSING) String userId) {
		// 数据校验
		allowAccess(key);
		UserInfo userInfo = validateUser(userId);

		// 组织校验
		if (!StringUtils.hasText(userInfo.getOrganizationId())) {
			log.warn("The current user has no organization information！User=[id={}, user_name={}]",
					userInfo.getUserId(), userInfo.getUserName());
			return new BaseRsp<>(Lists.newArrayList());
		}
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(Lists.newArrayList());
		}

		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), false);
		List<ExternalPracticeSetBo> setBoList = practiceSets.stream()
				.filter(practiceSet -> setIds.contains(practiceSet.getResourceId()))
				.map(practiceSet -> BeanUtils.copy(practiceSet, ExternalPracticeSetBo.class))
				.collect(Collectors.toList());

		return new BaseRsp<>(setBoList);
	}

	@ApiOperation("理论基础分类-全量获取")
	@GetMapping("/category/list")
	public BaseRsp<List<ExternalPracticeCategoryBo>>
			getCategoryList(@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key) {
		allowAccess(key);

		List<PracticeCategory> categoryList = practiceCategoryMapper
				.listByTypeAndName(PracticeCategory.PracticeType.THEORY.getCode(), null);
		List<ExternalPracticeCategoryBo> categoryBoList = BeanUtils.deepCopyList(categoryList,
				ExternalPracticeCategoryBo.class);

		return new BaseRsp<>(categoryBoList);
	}

	@ApiOperation("理论基础题库-分页查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<PracticeTheory>> getTheoryList(
			@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key,
			PracticeTheoryListReq queryParams) {
		// 数据校验
		allowAccess(key);
		UserInfo userInfo = validateUser(queryParams.getUserId());
		if (!StringUtils.hasText(userInfo.getOrganizationId())) {
			return new BaseRsp<>(PageUtil.getNullResult());
		}

		// 习题集权限查询
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetService.getSetIds(authContext,
				queryParams.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(PageUtil.getNullResult());
		}

		Page page = queryParams.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		IPage<PracticeTheory> result = practiceTheoryMapper.getList(page, queryParams.getPattern(),
				setIds, queryParams.getCategory(), queryParams.getQuestionType(),
				queryParams.getDifficulty(),queryParams.getKeyword(), queryParams.getKnowledge());
		return new BaseRsp<>(PageUtil.getListResult(result));
	}

	@ApiOperation("理论基础题库-批量查询")
	@GetMapping("/batch-get")
	public BaseRsp<List<PracticeTheory>> batchGet(
			@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key,
			@ApiParam("资源编号数组") @RequestParam("resource_ids") @NotBlank List<String> resourceIds) {
		allowAccess(key);
		if (resourceIds == null) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		return new BaseRsp<>(practiceTheoryMapper.batchGet(resourceIds));
	}

	@ApiOperation("理论基础题库-随机获取数条")
	@GetMapping("/random-choice")
	public BaseRsp<List<PracticeTheory>> getTheoryRandom(
			@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key,
			PracticeRandomChoiceReq queryParams) {

		allowAccess(key);
		UserInfo userInfo = validateUser(queryParams.getUserId());
		if (!StringUtils.hasText(userInfo.getOrganizationId())) {
			return new BaseRsp<>(Lists.newArrayList());
		}

		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetService.getSetIds(authContext,
				queryParams.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(Lists.newArrayList());
		}

		TheoryListQuery listQuery = BeanUtils.copy(queryParams, TheoryListQuery.class);
		if (StringUtils.hasText(queryParams.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(queryParams.getCategory()));
		}
		listQuery.setPracticeSetIds(setIds);
		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(setIds, true);
		listQuery.setResourceIds(practiceIds);

		List<PracticeTheory> result = practiceTheoryMapper.randomChoice(listQuery,
				queryParams.getCount());
		for (PracticeTheory item : result) {
			item.setScore(queryParams.getScore());
		}
		return new BaseRsp<>(result);
	}

}
