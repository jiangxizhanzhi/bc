package com.cyberpeace.gateway.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.gateway.service.bo.ExternalPracticeCategoryBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeSetBo;
import com.cyberpeace.oj.practice.controller.req.external.PracticeRandomChoiceReq;
import com.cyberpeace.oj.practice.controller.req.external.PracticeTheoryListReq;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "外部接口-理论习题相关api")
@RestController
@RequestMapping("/gateway/theory")
public class ExternalTheoryController {

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 接口认证(key获取方式后续可能调整)
	 * @param key headers: OPT-OJ-KEY
	 */
	private void allowAccess(String key) {
		if (!key.equals(WebConstant.OJ_HEADER_KEY_VALUE)) {
			throw new UnauthorizedException("secretKey is failed.");
		}
	}

	@ApiOperation("理论基础习题集")
	@GetMapping("/practice-set/list")
	public BaseRsp<List<ExternalPracticeSetBo>>
			getPracticeSetList(@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key) {
		allowAccess(key);

		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), false);
		List<ExternalPracticeSetBo> setBoList = BeanUtils.deepCopyList(practiceSets,
				ExternalPracticeSetBo.class);
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
		allowAccess(key);
		IPage<PracticeTheory> result = practiceTheoryMapper.getList(queryParams.toPageParam(),
				queryParams.getPattern(), queryParams.getPracticeSet(), queryParams.getCategory(),
				queryParams.getQuestionType(), queryParams.getDifficulty());
		return new BaseRsp<>(PageUtil.getListResult(result));
	}

	@ApiOperation("理论基础题库-批量查询")
	@GetMapping("/batch-get")
	public BaseRsp<List<PracticeTheory>> batchGet(
			@RequestHeader(WebConstant.OJ_HEADER_KEY) @NotBlank String key,
			@RequestParam("resource_ids") @NotBlank List<String> resourceIds) {
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
		TheoryListQuery listQuery = BeanUtils.copy(queryParams, TheoryListQuery.class);
		if (StringUtils.hasText(queryParams.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(queryParams.getCategory()));
		}

		listQuery.setPracticeSetId(queryParams.getPracticeSet());

		List<PracticeTheory> result = practiceTheoryMapper.randomChoice(listQuery,
				queryParams.getCount());
		for (PracticeTheory item : result) {
			item.setScore(queryParams.getScore());
		}
		return new BaseRsp<>(result);
	}

}
