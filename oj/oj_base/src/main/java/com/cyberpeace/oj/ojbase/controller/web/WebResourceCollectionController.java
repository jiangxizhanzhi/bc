package com.cyberpeace.oj.ojbase.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.user.UserInfoContext;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Web资源收集Controller
 *
 * @author SN
 * @date 2024/11/11
 */
@Api(tags = "oj-前台资源收藏相关api")
@RestController
@RequestMapping("/oj/collection")
@RequiresAuthentication
public class WebResourceCollectionController {

	/**
	 * 资源收集Service
	 */
	@Autowired
	private ResourceCollectionService resourceCollectionService;

	/**
	 * 新增
	 *
	 * @param collectionReq 收集请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("资源收藏或取消收藏")
	@PostMapping("")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
					MenuConstant.WEB_OJ_THEORY_PRACTICE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<Boolean> add(@RequestBody @Validated CollectionReq collectionReq) {

		CollectionContext collectionContext = BeanUtils.copy(collectionReq,
				CollectionContext.class);
		collectionContext.setUserId(UserInfoContext.getUserContext().getUserId());

		Boolean result = resourceCollectionService.collectionResource(collectionContext,
				collectionReq.getIsCollection());

		return new BaseRsp<>(result);
	}

}
