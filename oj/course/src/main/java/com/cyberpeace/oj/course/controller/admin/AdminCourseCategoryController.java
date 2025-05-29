package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.BatchDeleteReq;
import com.cyberpeace.oj.course.controller.req.CourseCategoryListReq;
import com.cyberpeace.oj.course.controller.req.SaveCourseCategoryReq;
import com.cyberpeace.oj.course.controller.req.UpdateCategoryReq;
import com.cyberpeace.oj.course.controller.res.CategoryRes;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理课程类别Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码是一个用于管理课程类别的控制器类 `AdminCourseCategoryController`。以下是对其主要功能和结构的简要分析： 
 * ### 主要功能 
 * 1. **列表查询**：提供分页查询课程分类的功能。 
 * 2. **详细信息查询**：根据资源ID查询课程分类的详细信息，并可选择是否查询子分类。 
 * 3. **批量删除**：允许根据资源ID列表批量删除课程分类。 
 * 4. **新增分类**：允许添加新的课程分类，并进行必要的验证（如名称唯一性检查）。 
 * 5. **更新分类**：允许更新现有的课程分类，并进行相应的验证。 
 * ### 主要组件 
 * - **依赖注入**： 
 * - `CourseCategoryService`：处理课程分类的核心业务逻辑。 
 * - `CourseCategoryMapper`：与数据库交互，执行SQL操作。 
 * - `CourseValidateHandler`：处理业务验证逻辑。 
 * - **Swagger注解**： 
 * - 用于API文档生成，描述接口的功能、参数等。 
 * ### 方法分析 
 * 1. **列表查询 (`list`)**： 
 * - 使用分页参数和搜索条件进行查询。 
 * - 默认按更新时间降序排序。 
 * 2. **详细信息查询 (`get`)**： 
 * - 根据资源ID获取课程分类信息。 
 * - 可以选择是否查询子分类。 
 * 3. **批量删除 (`delete`)**： 
 * - 接收一个包含多个资源ID的请求体，批量删除对应的课程分类。 
 * 4. **新增分类 (`add`)**： 
 * - 验证分类名称的唯一性。 
 * - 将请求体中的数据转换为业务对象并保存到数据库。 
 * - 返回新增后的分类信息。 
 * 5. **更新分类 (`patch`)**： 
 * - 根据资源ID获取现有分类信息。 
 * - 进行必要的验证（如名称唯一性、内置分类不可修改等）。 
 * - 更新分类信息并返回更新后的分类信息。 
 * ### 安全性 
 * - 使用了Shiro的权限注解 `@RequiresPermissions` 来控制访问权限。 
 * - 在新增和更新操作中进行了业务逻辑验证，确保数据的完整性和一致性。 
 * ### 总结 
 * 这个控制器类提供了完整的课程分类管理功能，包括CRUD操作，并且通过依赖注入和业务逻辑验证保证了系统的稳定性和安全性。 
**/
@Api(tags = "oj-后台课程分类api")
@RestController
@RequestMapping("/oj/admin/course/direction")
@RequiresAuthentication
@Slf4j
public class AdminCourseCategoryController {

	/**
	 * 课程类别Service
	 *
	 * @see CourseCategoryService
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 课程类别Mapper
	 *
	 * @see CourseCategoryMapper
	 */
	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	/**
	 * 课程验证处理器
	 *
	 * @see CourseValidateHandler
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<CourseCategoryBo>> }
	 * @throws
	 * @see CourseCategoryListReq
	 * @see BaseRsp<ListRsp<CourseCategoryBo>>
	 */
	@ApiOperation("课程分类分页查询")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_COURSE_STATISTICS }, logical = Logical.OR)
	public BaseRsp<ListRsp<CourseCategoryBo>> list(@Validated CourseCategoryListReq listReq) {
		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}
		IPage<CourseCategoryBo> courseCategories = courseCategoryService.pageBySearch(page,
				listReq.getPattern(), listReq.getChildren());

		return new BaseRsp(PageUtil.getListResult(courseCategories));
	}

	/**
	 * 获取
	 *
	 * @param resourceId 资源id
	 * @param children   子级
	 * @return {@code BaseRsp<CourseCategoryBo> }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see BaseRsp<CourseCategoryBo>
	 */
	@ApiOperation("课程分类详细信息查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "resource_id", value = "分类编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "children", value = "是否查询子类 true查询 false仅查当前级", dataTypeClass = Boolean.class)})
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CourseCategoryBo> get(@RequestParam("resource_id") String resourceId,
			@RequestParam(value = "children", required = false) Boolean children) {

		CourseCategoryBo courseCategoryBo = courseCategoryService.selectById(resourceId,
				null == children ? CommonConstant.FALSE : children);
		return new BaseRsp(courseCategoryBo);
	}

	/**
	 * 删除
	 *
	 * @param batchDeleteReq 批量删除请求体
	 * @return {@code BaseRsp<Integer> }
	 * @throws
	 * @see BatchDeleteReq
	 * @see BaseRsp<Integer>
	 */
	@ApiOperation("课程分类批量删除")
	@DeleteMapping("/batch")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Integer> delete(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		Integer row = courseCategoryService.batchDeleteByIds(batchDeleteReq.getResourceIds());
		return new BaseRsp<>(row);
	}

	/**
	 * 新增
	 *
	 * @param categoryReq 类别请求体
	 * @return {@code BaseRsp<CategoryRes> }
	 * @throws
	 * @see SaveCourseCategoryReq
	 * @see BaseRsp<CategoryRes>
	 */
	@ApiOperation("课程分类新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CategoryRes>
			add(@RequestBody @Validated(Create.class) SaveCourseCategoryReq categoryReq) {

		courseValidateHandler.categoryNameLengthValidate(categoryReq.getName());

		// 名称校验
		CourseCategory existCategory = courseCategoryMapper.selectByName(categoryReq.getName());
		if (null != existCategory) {
			log.error("[{}] is exist", existCategory.getName());
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}

		// 数据新增
		CategoryAddBo categoryAddBo = BeanUtils.copy(categoryReq, CategoryAddBo.class);
		CourseCategory courseCategory = courseCategoryService.saveOrUpdate(categoryAddBo);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp<>(categoryRes);
	}

	/**
	 * 补丁
	 *
	 * @param updateCategoryReq 更新类别请求体
	 * @return {@code BaseRsp<CategoryRes> }
	 * @throws
	 * @see UpdateCategoryReq
	 * @see BaseRsp<CategoryRes>
	 */
	@ApiOperation("课程分类修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CategoryRes>
			patch(@RequestBody @Validated(Update.class) UpdateCategoryReq updateCategoryReq) {

		CourseCategory oldCategory  =  courseCategoryMapper.selectById(updateCategoryReq.getResourceId());
		if(null == oldCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(Boolean.TRUE.equals(oldCategory.getBuiltin())){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}

		if (StringUtils.isNotBlank(updateCategoryReq.getName())) {
			courseValidateHandler.categoryNameLengthValidate(updateCategoryReq.getName());
		}

		CategoryAddBo categoryAddBo = BeanUtils.copy(updateCategoryReq, CategoryAddBo.class);
		CourseCategory courseCategory = courseCategoryService.saveOrUpdate(categoryAddBo);
		CategoryRes categoryRes = BeanUtils.copy(courseCategory, CategoryRes.class);
		return new BaseRsp<>(categoryRes);
	}

}

