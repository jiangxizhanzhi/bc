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
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.service.BaseCrSceneUpdateHandler;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.controller.req.scene.NodeOpsReq;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.IdNameType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 靶场场景控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "cr场景相关接口")
@RestController
@Slf4j
@RequestMapping("/cr/scene")
@RequiresAuthentication
public class CrSceneController {

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 靶场场景Service
	 */
	@Autowired
	private CrSceneService crSceneService;

	/**
	 * 更新场景
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@PutMapping("/update/dynamic")
	@ApiOperation("动态更新场景")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
					MenuConstant.CMS_CR_TEAM_TRAIN, MenuConstant.WEB_CR_SINGLE_TRAIN,
					MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE,
					MenuConstant.WEB_CR_EVALUATION },
			logical = Logical.OR)
	public BaseRsp<Boolean> updateScene(@Valid @RequestBody SceneUpdateReq req) {
		LocalScene localScene = localSceneService.getOrExceptionByMpSceneId(req.getMpSceneId());
		// get role update handler
		BaseCrSceneUpdateHandler handler = BaseCrSceneUpdateHandler
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
		crSceneService.sendSceneUpdateSceneWs(localScene.getBusinessInstanceId());
		return new BaseRsp<>();
	}

	/**
	 * 获取资产清单
	 * <p>
	 * <p>
	 * 透传中台节点数据
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<ListRsp<JSONObject>>}
	 */
	@ApiOperation("资产清单, 需要开启场景(团队、红蓝)")
	@GetMapping("/asset/list")
	@RequiresPermissions(value = { MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<ListRsp<JSONObject>> getAssets(@Validated AssetPageReq req) {
		CrCommonValidator.validateId(req.getResource_id());
		List<JSONObject> res = crSceneService.getAssetList(req, false);
		return new BaseRsp<>(PageUtil.pageList(res, req.getPage(), req.getPerPage()));
	}

	/**
	 * 获取操作系统名字
	 * <p>
	 * <p>
	 * 透传中台节点数据
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<List<IdNameType>>}
	 */
	@ApiOperation("操作系统列表, 需要开启场景(团队、红蓝)")
	@GetMapping("/os/list")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
					MenuConstant.CMS_CR_TEAM_TRAIN, MenuConstant.WEB_CR_SINGLE_TRAIN,
					MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<List<IdNameType>> getOsNames(
			@ApiParam(name = "resource_id", value = "训练房间id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		List<IdNameType> res = crSceneService.getOsNames(resource_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 删除节点
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@DeleteMapping("/node/delete")
	@ApiOperation("动态删除单个节点")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
					MenuConstant.CMS_CR_TEAM_TRAIN, MenuConstant.WEB_CR_SINGLE_TRAIN,
					MenuConstant.WEB_CR_TEAM_TRAIN, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<Boolean> deleteNode(@Valid @RequestBody NodeOpsReq req) {
		LocalScene localScene = localSceneService.getOrExceptionByMpSceneId(req.getMpSceneId());
		BaseCrSceneUpdateHandler handler = BaseCrSceneUpdateHandler
				.getHandler(localScene.getBusinessCode());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (null != handler) {
					handler.deleteNode(localScene.getBusinessInstanceId(), req.getNodeId());
				}
				localSceneService.deleteNode(req.getMpSceneId(), req.getNodeId());
			}
		});
		// 5.4仅限红蓝、团队
		if (null != handler) {
			// 发送ws提示前端刷新场景数据，包括查看场景、教员资产清单、学员常用资产3个接口
			crSceneService.sendNodeDeleteSceneWs(localScene.getBusinessInstanceId());
		}
		return new BaseRsp<>(true);
	}
}
