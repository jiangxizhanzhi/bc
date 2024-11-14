package com.cyberpeace.oj.practice.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeSetPageReq;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 练习设置Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "oj-前台习题集相关api")
@RestController
@RequestMapping("/oj/practice/set")
@RequiresAuthentication
public class PracticeSetController {

	/**
	 * 练习设置Service
	 */
	@Autowired
	private PracticeSetService practiceSetService;

	/**
	 * 获取列表
	 *
	 * @param setPageReq 设置分页请求体
	 * @return {@code BaseRsp<ListRsp<AdminPracticeSetBo>> }
	 */
	@ApiOperation("获取习题集")
	@GetMapping("/list")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_THEORY_PRACTICE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<ListRsp<AdminPracticeSetBo>> getList(@Validated PracticeSetPageReq setPageReq) {

		// 设置默认排序
		Page page = setPageReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		IPage<AdminPracticeSetBo> boIPage = practiceSetService.selectByTypeAndName(page,
				setPageReq.getPracticeType(), setPageReq.getPattern(), authContext);
		return new BaseRsp<>(PageUtil.getListResult(boIPage));
	}

	/**
	 * 获取
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<AdminPracticeSetBo> }
	 */
	@ApiOperation("习题集详细信息查询")
	@GetMapping("/detail")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_THEORY_PRACTICE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<AdminPracticeSetBo> get(@ApiParam(name = "resource_id",
			value = "习题集编号") @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		AdminPracticeSetBo setBo = practiceSetService.selectById(resourceId, authContext);
		return new BaseRsp<>(setBo);
	}

	/**
	 * 获取更多练习设置
	 *
	 * @param resourceId 资源id
	 * @param setType    设置类型
	 * @return {@code BaseRsp<List<AdminPracticeSetBo>> }
	 */
	@ApiOperation("更多习题集")
	@GetMapping("/more_set")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_THEORY_PRACTICE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<List<AdminPracticeSetBo>> getMorePracticeSet(
			@ApiParam(name = "resource_id", value = "习题集编号") @RequestParam("resource_id") String resourceId,
			@ApiParam(name = "set_type", value = "习题集类型 0=理论基础 2=实践操作") @RequestParam("set_type") Integer setType) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), true);
		return new BaseRsp<>(practiceSetService.getMorePracticeSet(authContext, resourceId, setType));
	}

}
