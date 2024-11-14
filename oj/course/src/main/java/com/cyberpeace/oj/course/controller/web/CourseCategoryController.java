package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程类别Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "oj-前台课程分类相关api")
@RestController
@RequestMapping("/oj/course/direction")
@RequiresAuthentication
public class CourseCategoryController {

	/**
	 * 课程类别Service
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 列表方向
	 *
	 * @param children 子级
	 * @return {@code BaseRsp<List<CourseCategoryBo>> }
	 */
	@GetMapping("")
	@ApiOperation("课程分类")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<List<CourseCategoryBo>>
			listDirection(@RequestParam(value = "children", required = false) Boolean children) {
		List<CourseCategoryBo> categories = courseCategoryService
				.selectAll(null != children ? children : CommonConstant.FALSE);

		return new BaseRsp<>(categories);
	}

}
