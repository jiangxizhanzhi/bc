package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.controller.req.BatchDeleteReq;
import com.cyberpeace.oj.course.controller.req.CourseCategoryListReq;
import com.cyberpeace.oj.course.controller.req.SaveCourseCategoryReq;
import com.cyberpeace.oj.course.controller.req.UpdateCategoryReq;
import com.cyberpeace.oj.course.controller.res.CategoryRes;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequiresPermissions(MenuConstant.CMS_COURSES)
public class AdminCourseCategoryController {

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@ApiOperation("课程分类分页查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<CourseCategoryBo>> list(@Validated CourseCategoryListReq listReq) {
		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}
		IPage<CourseCategoryBo> courseCategories = courseCategoryService.pageBySearch(page,
				listReq.getPattern(), listReq.getChildren());

		return new BaseRsp(PageUtil.getListResult(courseCategories));
	}

	@ApiOperation("课程分类详细信息查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "resource_id", value = "分类编号"),
			@ApiImplicitParam(name = "children", value = "是否查询子类 true查询 false仅查当前级") })
	@GetMapping("")
	public BaseRsp<CourseCategoryBo> get(@RequestParam("resource_id") String resourceId,
			@RequestParam(value = "children", required = false) Boolean children) {

		CourseCategoryBo courseCategoryBo = courseCategoryService.selectById(resourceId,
				null == children ? CommonConstant.FALSE : children);
		return new BaseRsp(courseCategoryBo);
	}

	@ApiOperation("课程分类批量删除")
	@DeleteMapping("/batch")
	public BaseRsp<Integer> delete(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		Integer row = courseCategoryService.batchDeleteByIds(batchDeleteReq.getResourceIds());
		return new BaseRsp<>(row);
	}

	@ApiOperation("课程分类新增")
	@PostMapping("")
	public BaseRsp<CategoryRes>
			add(@RequestBody @Validated(Create.class) SaveCourseCategoryReq categoryReq) {

		courseValidateHandler.categoryNameLengthValidate(categoryReq.getName());

		// 名称校验
		CourseCategory existCategory = courseCategoryMapper.selectByName(categoryReq.getName());
		if (null != existCategory) {
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}

		// 数据新增
		CategoryAddBo categoryAddBo = BeanUtils.copy(categoryReq, CategoryAddBo.class);
		CourseCategory courseCategory = courseCategoryService.saveOrUpdate(categoryAddBo);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp<>(categoryRes);
	}

	@ApiOperation("课程分类修改")
	@PatchMapping("")
	public BaseRsp<CategoryRes>
			patch(@RequestBody @Validated(Update.class) UpdateCategoryReq updateCategoryReq) {

		if (StringUtils.isNotBlank(updateCategoryReq.getName())) {
			courseValidateHandler.categoryNameLengthValidate(updateCategoryReq.getName());
		}

		CategoryAddBo categoryAddBo = BeanUtils.copy(updateCategoryReq, CategoryAddBo.class);
		CourseCategory courseCategory = courseCategoryService.saveOrUpdate(categoryAddBo);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp<>(categoryRes);
	}

}
