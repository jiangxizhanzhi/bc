package com.cyberpeace.cr.crbase.controller;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.service.BaseRoleUpdateHandler;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "cr场景相关接口")
@RestController
@Slf4j
@RequestMapping("/cr/scene")
@RequiresAuthentication
public class CrSceneController {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@PutMapping("/update/dynamic")
	@ApiOperation("动态更新场景")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> updateScene(@Valid @RequestBody SceneUpdateReq req) {

		LocalScene localScene = localSceneMapper.selectBySceneId(req.getMpSceneId());
		if (null == localScene) {
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		// get role update handler
		BaseRoleUpdateHandler handler = BaseRoleUpdateHandler
				.getHandler(localScene.getBusinessCode());
		// if remote update fails, roll back
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// rvb , team training
				if (null != handler) {
					handler.updateRoles(localScene.getBusinessInstanceId(), req);
				}
				localSceneService.updateScene(localScene, req.getUpdateScene());
			}
		});

		return new BaseRsp<>();
	}
}
