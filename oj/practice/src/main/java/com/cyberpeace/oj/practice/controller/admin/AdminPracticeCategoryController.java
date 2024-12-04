package com.cyberpeace.oj.practice.controller.admin;

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
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.CategoryUpdateReq;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryListReq;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryReq;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 管理实践类别Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Api(tags = "oj-后台习题分类相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/admin/practice/category")
public class AdminPracticeCategoryController {

    /**
     * 实践类别Service
     *
     * @see PracticeCategoryService
     */
    @Autowired
	private PracticeCategoryService practiceCategoryService;

    /**
     * 列表
     *
     * @param categoryListReq 类别列表请求体
     * @return {@code BaseRsp<ListRsp<CategoryListBo>> }
     * @throws
     * @see PracticeCategoryListReq
     * @see BaseRsp<ListRsp<CategoryListBo>>
     */
    @ApiOperation("分页查询练习分类")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_THEORY_PRACTICE, MenuConstant.CMS_PRACTICE_OPERATION }, logical = Logical.OR)
	public BaseRsp<ListRsp<CategoryListBo>>
			list(@Validated PracticeCategoryListReq categoryListReq) {

		Page page = categoryListReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}
		CategoryListQuery query = BeanUtils.copy(categoryListReq, CategoryListQuery.class);

		IPage<CategoryListBo> iPage = practiceCategoryService.pageByListQuery(page, query,
				categoryListReq.getChildren());

		return new BaseRsp(PageUtil.getListResult(iPage));
	}

    /**
     * 获取
     *
     * @param resourceId 资源id
     * @param children   子级
     * @return {@code BaseRsp<CategoryListBo> }
     * @throws
     * @see String
     * @see Boolean
     * @see BaseRsp<CategoryListBo>
     */
    @ApiOperation("查询分类详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "resource_id", value = "分类编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "children", value = "是否查询子类", dataTypeClass = Boolean.class) })
	@GetMapping("")
	public BaseRsp<CategoryListBo> get(@RequestParam("resource_id") String resourceId,
			@RequestParam(value = "children", required = false) Boolean children) {

		CategoryListBo categoryListBo = practiceCategoryService.selectById(resourceId,
				null != children ? children : CommonConstant.FALSE);
		return new BaseRsp<>(categoryListBo);
	}

    /**
     * 更新
     *
     * @param updateReq 更新请求体
     * @return {@code BaseRsp<Integer> }
     * @throws
     * @see CategoryUpdateReq
     * @see BaseRsp<Integer>
     */
    @ApiOperation("修改分类信息")
	@PatchMapping("")
	@RequiresPermissions(value = { MenuConstant.CMS_THEORY_PRACTICE, MenuConstant.CMS_PRACTICE_OPERATION }, logical = Logical.OR)
	public BaseRsp<Integer> update(@Validated CategoryUpdateReq updateReq) {

		updateReq.validate();
		// 分类数据处理
		PracticeCategory category = BeanUtils.copy(updateReq, PracticeCategory.class);
		if (StringUtils.isNotBlank(updateReq.getParentId())) {
			String code = practiceCategoryService.getCodeByParent(updateReq.getParentId());
			category.setCode(code);
		}

		Integer result = practiceCategoryService.updateCategory(category);
		return new BaseRsp<>(result);
	}

    /**
     * 新增
     *
     * @param categoryReq 类别请求体
     * @return {@code BaseRsp<Integer> }
     * @throws
     * @see PracticeCategoryReq
     * @see BaseRsp<Integer>
     */
    @ApiOperation("新增分类信息")
	@PostMapping("")
	@RequiresPermissions(value = { MenuConstant.CMS_THEORY_PRACTICE, MenuConstant.CMS_PRACTICE_OPERATION }, logical = Logical.OR)
	public BaseRsp<Integer> add(@Validated PracticeCategoryReq categoryReq) {

		categoryReq.validate();
		// 分类数据处理
		PracticeCategory practiceCategory = BeanUtils.copy(categoryReq, PracticeCategory.class);
		if (StringUtils.isNotBlank(categoryReq.getParentId())) {
			String code = practiceCategoryService.getCodeByParent(categoryReq.getParentId());
			practiceCategory.setCode(code);
		} else {
			practiceCategory.setResourceId(IDGeneratorUtil.getID());
			practiceCategory.setCode(practiceCategory.getResourceId());
		}

		// 数据新增
		Integer result = practiceCategoryService.addCategory(practiceCategory,
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp<>(result);

	}

    /**
     * 删除
     *
     * @param delReq 戴尔请求体
     * @return {@code BaseRsp<Integer> }
     * @throws
     * @see ResourceDelReq
     * @see BaseRsp<Integer>
     */
    @ApiOperation("批量删除分类信息")
	@DeleteMapping("")
	@RequiresPermissions(value = { MenuConstant.CMS_THEORY_PRACTICE, MenuConstant.CMS_PRACTICE_OPERATION }, logical = Logical.OR)
	public BaseRsp<Integer> delete(@RequestBody @Validated ResourceDelReq delReq) {

		int result = practiceCategoryService.batchDelete(delReq.getResourceIds());
		return new BaseRsp<>(result);
	}

}
