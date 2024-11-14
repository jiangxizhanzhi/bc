package com.cyberpeace.gateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.constant.WebConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.AuthOjKey;
import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
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
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryCountBo;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 外部理论Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "外部接口-理论习题相关api")
@Slf4j
@AuthKeyUser
@RestController
@RequestMapping("/gateway/theory")
public class ExternalTheoryController {

	/**
	 * 练习理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 练习设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 练习类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 练习设置Service
	 */
	@Autowired
	private PracticeSetService practiceSetService;

	/**
	 * 练习理论Service
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 获取练习设置列表
	 *
	 * @return {@code BaseRsp<List<ExternalPracticeSetBo>> }
	 */
	@ApiOperation("理论基础习题集")
	@GetMapping("/practice-set/list")
	public BaseRsp<List<ExternalPracticeSetBo>> getPracticeSetList() {

		// 组织校验
		UserInfo userInfo = UserInfoContext.getUserContext();
		
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

	/**
	 * 获取类别列表
	 *
	 * @param practiceSetId 练习设置id
	 * @return {@code BaseRsp<List<ExternalPracticeCategoryBo>> }
	 */
	@AuthOjKey
	@ApiOperation("理论基础分类-全量获取")
	@GetMapping("/category/list")
	public BaseRsp<List<ExternalPracticeCategoryBo>> getCategoryList(
			@RequestParam(name = "practice_set_id", required = false) String practiceSetId) {

		List<PracticeCategory> categoryList = practiceCategoryMapper
				.listByTypeAndName(PracticeCategory.PracticeType.THEORY.getCode(), null);

		List<ExternalPracticeCategoryBo> categoryBoList = BeanUtils.deepCopyList(categoryList,
				ExternalPracticeCategoryBo.class);

		if(!StringUtils.hasText(practiceSetId)) {
			return new BaseRsp<>(categoryBoList);
		}

		// 将理论题的方向和习题集全部查出来
		List<PracticeTheory> allCategoryIds = practiceTheoryService.getCategoryIdAndSetIds();
		// 筛选出对应习题集的方向id
		if(CollectionUtils.isEmpty(allCategoryIds)) {
			return new BaseRsp<>();
		}
		List<String> categoryIds = allCategoryIds.stream().filter(item -> item.getPracticeSetId().equals(practiceSetId))
				.map(PracticeTheory::getCategoryId).collect(Collectors.toList());
		// 将对应的方向数据筛选出来
		categoryBoList = categoryBoList.stream().filter(item -> categoryIds.contains(item.getResourceId())).collect(Collectors.toList());

		return new BaseRsp<>(categoryBoList);
	}

	/**
	 * 获取理论列表
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp<ListRsp<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-分页查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<PracticeTheory>> getTheoryList(PracticeTheoryListReq queryParams) {
		
		// 数据校验
		UserInfo userInfo = UserInfoContext.getUserContext();

		// 习题集权限查询
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetService.getSetIds(authContext,
				queryParams.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(PageUtil.getNullResult());
		}

		Page<PracticeTheory> page = queryParams.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		IPage<PracticeTheory> result = practiceTheoryMapper.getList(page, queryParams.getPattern(),
				setIds, queryParams.getCategory(), queryParams.getQuestionType(),
				queryParams.getDifficulty());
		return new BaseRsp<>(PageUtil.getListResult(result));
	}

	/**
	 * 批量获取
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code BaseRsp<List<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-批量查询")
	@GetMapping("/batch-get")
	@AuthOjKey
	public BaseRsp<List<PracticeTheory>> batchGet(
			@ApiParam("资源编号数组") @RequestParam("resource_ids") @NotBlank List<String> resourceIds) {
		
		if (resourceIds == null) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		return new BaseRsp<>(practiceTheoryMapper.batchGet(resourceIds));
	}

	/**
	 * 获取理论随机
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp<List<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-随机获取数条")
	@GetMapping("/random-choice")
	public BaseRsp<List<PracticeTheory>> getTheoryRandom(PracticeRandomChoiceReq queryParams) {
		
		UserInfo userInfo = UserInfoContext.getUserContext();
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

	/**
	 * 搜索计数
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ExternalTheoryCountBo> }
	 */
	@ApiOperation("查询各类型理论习题数量")
	@GetMapping("/type/count")
	public BaseRsp<ExternalTheoryCountBo> searchCount(@Validated PracticeTheoryListReq listReq) {

		UserInfo userInfo = UserInfoContext.getUserContext();

		// 设置查询条件
		TheoryListQueryBo queryBo = BeanUtils.copy(listReq, TheoryListQueryBo.class);
		if (StringUtils.hasText(listReq.getCategory())) {
			queryBo.setCategoryIds(Lists.newArrayList(listReq.getCategory()));
		}
		queryBo.setPracticeSetId(listReq.getPracticeSet());

		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		ExternalTheoryCountBo countBo = practiceTheoryService.typeCount(queryBo, authContext);
		return new BaseRsp<>(countBo);
	}

}
