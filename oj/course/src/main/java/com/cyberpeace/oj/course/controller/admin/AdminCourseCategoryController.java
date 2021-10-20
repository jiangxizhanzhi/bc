package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.controller.req.BatchDeleteReq;
import com.cyberpeace.oj.course.controller.req.SaveCourseCategoryReq;
import com.cyberpeace.oj.course.controller.req.UpdateCategoryReq;
import com.cyberpeace.oj.course.controller.res.CategoryRes;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;

import io.swagger.annotations.Api;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "oj-后台课程分类api")
@RestController
@RequestMapping("/oj/admin/course/direction")
@RequiresAuthentication
public class AdminCourseCategoryController {

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private CourseCategoryMapper categoryMapper;

	@ApiOperation("课程分类分页查询")
	@GetMapping("/list")
	public BaseRsp<List<CategoryRes>> list(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		List<CourseCategory> courseCategories = courseCategoryService.pageBySearch(page,
				pageReqBase.getPattern());
		List<CategoryRes> categoryRes = StreamApiUtils.simpleMap(courseCategories,
				courseCategory -> BeanUtils.copy(courseCategory, CategoryRes.class));

		return new BaseRsp<>(categoryRes);
	}

	@ApiOperation("课程分类详细信息查询")
	@GetMapping("")
	public BaseRsp<CategoryRes> get(@RequestParam("resource_id") String resourceId) {
		CourseCategory courseCategory = categoryMapper.selectById(resourceId);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp(categoryRes);
	}

	@ApiOperation("课程分类批量删除")
	@DeleteMapping("/batch")
	public BaseRsp<Integer> delete(@RequestBody @Valid BatchDeleteReq batchDeleteReq) {

		Integer row = courseCategoryService.batchDeleteByIds(batchDeleteReq.getResourceIds());
		return new BaseRsp<>(row);
	}

	@ApiOperation("课程分类新增")
	@PostMapping("")
	public BaseRsp<CategoryRes> add(@RequestBody @Valid SaveCourseCategoryReq categoryReq) {

		CategoryAddBo categoryAddBo = BeanUtils.copy(categoryReq, CategoryAddBo.class);
		CourseCategory courseCategory = courseCategoryService.saveOrUpdate(categoryAddBo);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp<>(categoryRes);
	}

	@ApiOperation("课程分类修改")
	@PatchMapping("")
	public BaseRsp<CategoryRes> patch(@RequestBody @Valid UpdateCategoryReq updateCategoryReq) {
		CategoryAddBo categoryAddBo = BeanUtils.copy(updateCategoryReq, CategoryAddBo.class);
		CourseCategory courseCategory = courseCategoryService.saveOrUpdate(categoryAddBo);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp<>(categoryRes);
	}

}
