package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import com.cyberpeace.common.constant.MenuConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.practice.controller.req.LibPracticeListReq;
import com.cyberpeace.oj.practice.controller.req.RandomPracticeReq;
import com.cyberpeace.oj.practice.controller.res.LibSelectRes;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 管理试卷Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "oj-后台课后练习和试卷相关api")
@RestController
@RequestMapping("/oj/admin/paper")
@RequiresAuthentication
public class AdminPaperController {

	/**
	 * 试卷Service
	 */
	@Autowired
	private PaperService paperService;

	/**
	 * 练习类别Service
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 练习设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 查询练习信息
	 *
	 * @param practiceListReq 练习列表请求体
	 * @return {@code BaseRsp<ListRsp<LibPracticeListBo>> }
	 */
	@ApiOperation("获取习题库习题列表")
	@GetMapping("/practice/list")
	@RequiresPermissions(value = {MenuConstant.CMS_EXERCISES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_COURSES}, logical = Logical.OR)
	public BaseRsp<ListRsp<LibPracticeListBo>>
			selectPracticeInfo(LibPracticeListReq practiceListReq) {

		// 查询数据组装
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		Page page = practiceListReq.toPageParam();
		LibPracticeQueryBo queryBo = BeanUtils.copy(practiceListReq, LibPracticeQueryBo.class);

		// 分类数据组装
		if (StringUtils.isNotBlank(practiceListReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(practiceListReq.getCategoryId(), true);
			queryBo.setCategoryIds(categoryIds);
		}

		// 习题数据查询
		IPage<LibPracticeListBo> iPage = paperService.selectLibPractice(page, queryBo, authContext);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	/**
	 * 获取
	 *
	 * @param practiceSetId 练习设置id
	 * @param children      子级
	 * @return {@code BaseRsp<LibSelectRes> }
	 */
	@ApiOperation("题库筛选条件查询")
	@GetMapping("/select_info")
	@ApiImplicitParams({ @ApiImplicitParam(name = "practice_set_id", value = "习题集编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "children", value = "分类是否查询子类", dataTypeClass = Boolean.class) })
	@RequiresPermissions(value = {MenuConstant.CMS_EXERCISES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_COURSES}, logical = Logical.OR)
	public BaseRsp<LibSelectRes> get(
			@RequestParam(value = "practice_set_id", required = false) String practiceSetId,
			@RequestParam(value = "children", required = false) Boolean children) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		LibSelectRes libInfoBo = new LibSelectRes();
		// 习题集数据查询
		List<PracticeSetRes> practiceSetRes = selectPracticeSet(authContext);
		libInfoBo.setPracticeSet(practiceSetRes);

		if (StringUtils.isNotBlank(practiceSetId)) {
			// 联动分类数据查询
			PracticeSet practiceSet = practiceSetMapper.selectById(practiceSetId);
			List<CategoryListBo> categoryListBos = selectPracticeCategory(
					practiceSet.getPracticeType(),
					null == children ? CommonConstant.FALSE : children);
			libInfoBo.setCategory(categoryListBos);

			// 习题类型数据查询
			List<Integer> questionType = selectQuestionType(practiceSet.getPracticeType());
			libInfoBo.setQuestionType(questionType);
		}

		return new BaseRsp<>(libInfoBo);
	}

	/**
	 * 获取随机练习
	 *
	 * @param randomPracticeReq 随机练习请求体
	 * @return {@code BaseRsp<List<LibPracticeListBo>> }
	 */
	@ApiOperation("获取随机数量题目")
	@PostMapping("/random/practice")
	@RequiresPermissions(value = {MenuConstant.CMS_EXERCISES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_COURSES}, logical = Logical.OR)
	public BaseRsp<List<LibPracticeListBo>>
			getRandomPractice(@RequestBody @Validated RandomPracticeReq randomPracticeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		RandomPracticeQuery practiceQuery = BeanUtils.copy(randomPracticeReq,
				RandomPracticeQuery.class);
		practiceQuery.setSelectIds(randomPracticeReq.getResourceIds());

		// 分类数据组装
		if (StringUtils.isNotBlank(randomPracticeReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(randomPracticeReq.getCategoryId(), true);
			practiceQuery.setCategoryIds(categoryIds);
		}

		List<LibPracticeListBo> randomPracticeList = paperService
				.selectRandomPractice(practiceQuery, randomPracticeReq.getScore(), authContext);

		return new BaseRsp<>(randomPracticeList);
	}

	/**
	 * 获取习题类型
	 *
	 * @param practiceType
	 * @return
	 */
	private List<Integer> selectQuestionType(int practiceType) {

		if (practiceType == PracticeCategory.PracticeType.THEORY.getCode().intValue()) {
			List<Integer> listCode = EnumUtils.getListCode(PracticeTheory.TheoryType.class);
			listCode.remove(PracticeTheory.TheoryType.OPERATION.getCode());
			return listCode;
		} else {
			return Lists.newArrayList(PracticeTheory.TheoryType.OPERATION.getCode());
		}
	}

	/**
	 * 习题集分类查询
	 *
	 * @param practiceType
	 * @param children     子级
	 * @return
	 */
	private List<CategoryListBo> selectPracticeCategory(int practiceType, boolean children) {
		CategoryListQuery listQuery = CategoryListQuery.builder().practiceType(practiceType)
				.build();

		return practiceCategoryService.listByTypeAndName(listQuery, children);
	}

	/**
	 * 获取习题集
	 *
	 * @param authContext
	 * @return
	 */
	private List<PracticeSetRes> selectPracticeSet(AuthContext authContext) {

		List<String> practiceSetIds = practiceSetMapper.selectByAuthAndPublic(authContext, true);
		if (CollectionUtils.isEmpty(practiceSetIds)) {
			return Lists.newArrayList();
		}

		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(practiceSetIds);
		return StreamApiUtils.map(practiceSets,
				practiceSet -> BeanUtils.copy(practiceSet, PracticeSetRes.class));
	}

}
