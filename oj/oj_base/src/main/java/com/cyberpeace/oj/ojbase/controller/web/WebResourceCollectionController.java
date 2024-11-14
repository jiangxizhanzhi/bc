package com.cyberpeace.oj.ojbase.controller.web;

import java.util.Date;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorResultEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;
import com.cyberpeace.cs.service.BehaviorRecordService;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Web资源收集Controller
 *
 * @author SN
 * @date 2024/11/14
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
	 * 行为记录Service
	 */
	@Autowired
	private BehaviorRecordService behaviorRecordService;

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
		// 用户行为记录
		recordCollection(collectionReq);
		return new BaseRsp<>(result);
	}

	/**
	 * 记录集合
	 *
	 * @param collectionReq 收集请求体
	 */
	private void recordCollection(CollectionReq collectionReq) {
		if (!Boolean.TRUE.equals(collectionReq.getIsCollection())) {
			return;
		}
		BehaviorRecordEntity entity = new BehaviorRecordEntity();
		String descTemplate = "[%s] %s %s";
		if (collectionReq.getBusinessType()
				.equals(ResourceCollection.BusinessType.THEORY.getCode())) {
			// 理论题
			entity.setModule(BehaviorModuleEnum.THEORY.getCode());
			entity.setDescription(
					String.format(descTemplate, UserInfoContext.getUserContext().getName(),
							BehaviorTypeEnum.ADD_TO_FAVORITE.getDesc(),
							BehaviorModuleEnum.THEORY.getDesc()));
		} else if (collectionReq.getBusinessType()
				.equals(ResourceCollection.BusinessType.OPERATION.getCode())) {
			// 实操题
			entity.setModule(BehaviorModuleEnum.OPERATION.getCode());
			entity.setDescription(
					String.format(descTemplate, UserInfoContext.getUserContext().getName(),
							BehaviorTypeEnum.ADD_TO_FAVORITE.getDesc(),
							BehaviorModuleEnum.OPERATION.getDesc()));
		} else {
			return;
		}

		entity.setTime(new Date());
		entity.setResult(BehaviorResultEnum.DEFAULT.getCode());
		entity.setLink(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest().getRequestURI());
		entity.setTarget(collectionReq.getBusinessId());
		entity.setAction(BehaviorTypeEnum.ADD_TO_FAVORITE.getCode());
		entity.setUserId(UserInfoContext.getUserContext().getUserId());
		behaviorRecordService.save(entity);
	}

}
