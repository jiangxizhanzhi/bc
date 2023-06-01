package com.cyberpeace.cr.crbase.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.service.BaseRoleUpdateHandler;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.IdNameType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

	@Autowired
	private CrSceneService crSceneService;

	@PutMapping("/update/dynamic")
	@ApiOperation("动态更新场景")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> updateScene(@Valid @RequestBody SceneUpdateReq req) {

		LocalScene localScene = localSceneMapper.selectBySceneId(req.getMpSceneId());
		if (null == localScene) {
			log.error("Unknown scene! sceneId = [{}]", req.getMpSceneId());
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
		// 发送ws提示前端刷新场景数据
		crSceneService.sendAssetUpdateSceneWs(localScene.getBusinessInstanceId());
		return new BaseRsp<>();
	}

	/**
	 * 获取资产清单
	 * 透传中台节点数据
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<ListRsp<JSONObject>>}
	 */
	@ApiOperation("资产清单, 需要开启场景(团队、红蓝)")
	@GetMapping("/asset/list")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<ListRsp<JSONObject>> getAssets(@Validated AssetPageReq req) {
		CrCommonValidator.validateId(req.getResource_id());
		List<JSONObject> res = crSceneService.getAssetList(req);
		return new BaseRsp<>(PageUtil.pageList(res, req.getPage(), req.getPerPage()));
	}

	/**
	 * 获取操作系统名字
	 * 透传中台节点数据
	 *
	 * @return {@code BaseRsp<List<IdNameType>>}
	 */
	@ApiOperation("操作系统列表, 需要开启场景(团队、红蓝)")
	@GetMapping("/os/list")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<IdNameType>> getOsNames(
			@ApiParam(name = "resource_id", value = "训练房间id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		List<IdNameType> res = crSceneService.getOsNames(resource_id);
		return new BaseRsp<>(res);
	}
}
