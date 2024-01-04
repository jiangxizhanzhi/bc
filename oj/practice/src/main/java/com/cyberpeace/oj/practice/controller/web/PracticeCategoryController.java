package com.cyberpeace.oj.practice.controller.web;

import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-前台练习分类相关api")
@RestController
@RequestMapping("/oj/practice/category")
public class PracticeCategoryController {

	@Autowired
	private PracticeCategoryService categoryService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;
	@GetMapping("/listAll")
	public BaseRsp<List<PracticeCategory>>  queryAll() {
		return new BaseRsp<>(categoryService.list());
	}



	@ApiOperation("查询习题分类列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "practice_type", value = "分类类型 0=理论基础 2=实践操作", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "pattern", value = "模糊搜索内容", dataTypeClass = String.class),
			@ApiImplicitParam(name = "children", value = "是否查询子分类", dataTypeClass = String.class)})
	@GetMapping("/list")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_THEORY_PRACTICE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<List<CategoryListBo>> list(
			@RequestParam(value = "practice_type") Integer practiceType,
			@RequestParam(value = "pattern", required = false) String pattern,
			@RequestParam(value = "children", required = false) Boolean children,
			@RequestParam(name = "practice_set_id", required = false) String practiceSetId) {

		CategoryListQuery listQuery = CategoryListQuery.builder().practiceType(practiceType)
				.build();
		listQuery.setPattern(pattern);
		List<CategoryListBo> boList = categoryService.listByTypeAndName(listQuery,
				null == children ? CommonConstant.FALSE : children);

		if(StringUtils.isEmpty(practiceSetId)) {
			return new BaseRsp<>(boList);
		}

		if(practiceType == 2) {
			List<PracticeOperation> categoryIdAndSetIds = practiceOperationService.getCategoryIdAndSetIds();

			// 筛选出对应习题集的方向id
			if(CollectionUtils.isEmpty(categoryIdAndSetIds)) {
				return new BaseRsp<>();
			}
			List<String> categoryIds = categoryIdAndSetIds.stream().filter(item -> item.getPracticeSetId().equals(practiceSetId))
					.map(PracticeOperation::getCategoryId).collect(Collectors.toList());
			// 将对应的方向数据筛选出来
			boList = boList.stream().filter(item -> categoryIds.contains(item.getResourceId())).collect(Collectors.toList());

		} else {
			// 将理论题的方向和习题集全部查出来
			List<PracticeTheory> allCategoryIds = practiceTheoryService.getCategoryIdAndSetIds();

			// 筛选出对应习题集的方向id
			if(CollectionUtils.isEmpty(allCategoryIds)) {
				return new BaseRsp<>();
			}
			List<String> categoryIds = allCategoryIds.stream().filter(item -> item.getPracticeSetId().equals(practiceSetId))
					.map(PracticeTheory::getCategoryId).collect(Collectors.toList());
			// 将对应的方向数据筛选出来
			boList = boList.stream().filter(item -> categoryIds.contains(item.getResourceId())).collect(Collectors.toList());
		}

		return new BaseRsp<>(boList);
	}
}
