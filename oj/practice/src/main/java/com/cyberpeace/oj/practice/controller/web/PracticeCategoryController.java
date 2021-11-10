package com.cyberpeace.oj.practice.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.oj.practice.service.PracticeCategoryService;

import java.util.List;

@Api(tags = "练习分类")
@RestController
@RequestMapping("/oj/practice/category")
@RequiresAuthentication
public class PracticeCategoryController {

	@Autowired
	private PracticeCategoryService categoryService;

	@ApiOperation("查询习题分类列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "practice_type", value = "分类类型 0=理论基础 2=实践操作"),
			@ApiImplicitParam(name = "pattern", value = "模糊搜索内容"),
			@ApiImplicitParam(name = "children", value = "是否查询子分类") })
	@GetMapping("/list")
	public BaseRsp<List<CategoryListBo>> list(@RequestParam("practice_type") Integer practiceType,
			@RequestParam("pattern") String pattern, @RequestParam("children") Boolean children) {

		CategoryListQuery listQuery = CategoryListQuery.builder().practiceType(practiceType)
				.pattern(pattern).build();

		List<CategoryListBo> boList = categoryService.listByTypeAndName(listQuery, children);
		return new BaseRsp<>(boList);
	}
}
