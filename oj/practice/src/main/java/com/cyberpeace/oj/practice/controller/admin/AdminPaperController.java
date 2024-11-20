package com.cyberpeace.oj.practice.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理纸Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "oj-后台课后练习和试卷相关api")
@RestController
@RequestMapping("/oj/admin/paper")
@RequiresAuthentication
public class AdminPaperController {

	/**
	 * 纸Service
	 */
	@Autowired
	private PaperService paperService;

	/**
	 * 实践类别Service
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 实践设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 查询实践信息
	 *
	 * @param practiceListReq 实践列表请求体
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

		return new BaseRsp(PageUtil.getListResult(iPage));
	}

	/**
	 * 获取
	 *
	 * @param children      子级
	 * @return {@code BaseRsp<LibSelectRes> }
	 */
	@ApiOperation("题库筛选条件查询")
	@GetMapping("/select_info")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "practice_type", value = "习题类型, 0=理论题，2=实操题",
					dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "children", value = "分类是否查询子类", dataTypeClass = Boolean.class) })
	@RequiresPermissions(value = { MenuConstant.CMS_EXERCISES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_COURSES }, logical = Logical.OR)
	public BaseRsp<LibSelectRes> get(
			@RequestParam(value = "practice_type", required = false) Integer practice_type,
			@RequestParam(value = "children", required = false) Boolean children) {

		LibSelectRes libInfoBo = new LibSelectRes();
		if (null != practice_type) {
			// 联动分类数据查询
			List<CategoryListBo> categoryListBos = selectPracticeCategory(practice_type,
					null == children ? CommonConstant.FALSE : children);
			libInfoBo.setCategory(categoryListBos);

			// 习题类型数据查询
			List<Integer> questionType = selectQuestionType(practice_type);
			libInfoBo.setQuestionType(questionType);
		}

		return new BaseRsp<>(libInfoBo);
	}

	/**
	 * 获取随机实践
	 *
	 * @param randomPracticeReq 随机实践请求体
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

		if (practiceType == PracticeCategory.PracticeType.THEORY.getCode()) {
			List<Integer> listCode = EnumUtils.getListCode(PracticeTheory.TheoryType.class);
			listCode.remove(PracticeTheory.TheoryType.OPERATION.getCode());
			listCode.remove(PracticeTheory.TheoryType.SHORT_ANSWER.getCode());
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
