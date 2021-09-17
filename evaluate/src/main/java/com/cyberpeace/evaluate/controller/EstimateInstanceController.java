package com.cyberpeace.evaluate.controller;

import java.util.List;

import javax.validation.Valid;

import com.cyberpeace.common.model.evaluation.ProbeOriginData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.controller.req.EstimateInstanceResultReq;
import com.cyberpeace.evaluate.controller.req.EstimateOriginDataReq;
import com.cyberpeace.evaluate.controller.res.EstimateInstanceBody;
import com.cyberpeace.evaluate.dao.entity.EstimateInstance;
import com.cyberpeace.evaluate.service.EstimateInstanceService;
import com.cyberpeace.evaluate.service.bo.EstimateInstanceBO;
import com.cyberpeace.evaluate.service.bo.EstimateInstanceViewBO;
import com.cyberpeace.evaluate.service.bo.EstimateReviewBo;
import com.cyberpeace.evaluate.service.context.EstimateInstanceContext;

@Api(tags = "es-装备评估实例相关api")
@RestController
@RequestMapping("/estimate/instance")
@RequiresAuthentication
public class EstimateInstanceController {

	@Autowired
	private EstimateInstanceService estimateInstanceService;

	/**
	 * 创建一个评估实例
	 * */
	@GetMapping("/start")
	@ApiOperation("开始评估")
	BaseRsp<EstimateInstance> createEstimateInstance(
			@ApiParam("评估编号") @RequestParam("estimate_id") String estimateId,
			@ApiParam("场景编号") @RequestParam("scene_id") String sceneId) {

		EstimateInstanceContext estimateInstanceContext = new EstimateInstanceContext();
		estimateInstanceContext.setEstimateId(estimateId);
		estimateInstanceContext.setSceneId(sceneId);
		EstimateInstance estimateInstance = estimateInstanceService
				.createEstimateInstance(estimateInstanceContext);
		BaseRsp<EstimateInstance> rsp = new BaseRsp<EstimateInstance>();
		rsp.setData(estimateInstance);
		return rsp;
	}

	/**
	 * 停止采集数据
	 * */
	@GetMapping("/stop")
	@ApiOperation("停止采集数据")
	public BaseRsp
			stopEstimateInstance(@ApiParam("实例编号") @RequestParam("instance_id") String instanceId) {
		estimateInstanceService.stopEstimateInstance(instanceId);
		return new BaseRsp();
	}

	/**
	 * 手动保存评估实例结果
	 * */
	@PostMapping("/save")
	@ApiOperation("手动保存评估实例结果")
	public BaseRsp<EstimateInstance>
			saveEstimateInstanceResult(@RequestBody EstimateInstanceBody estimateInstanceBody) {
		EstimateInstanceContext estimateInstanceContext = new EstimateInstanceContext();
		estimateInstanceContext.setEstimateInstanceId(estimateInstanceBody.getInstanceId());
		estimateInstanceContext.setInstanceName(estimateInstanceBody.getInstanceName());
		estimateInstanceContext.setInstanceDesc(estimateInstanceBody.getInstanceDesc());
		EstimateInstance estimateInstance = estimateInstanceService
				.saveEstimateInstanceResult(estimateInstanceContext);
		BaseRsp<EstimateInstance> rsp = new BaseRsp<>();
		rsp.setData(estimateInstance);
		return rsp;
	}

	@GetMapping("/abandon")
	@ApiOperation("不保存评估结果")
	public BaseRsp<EstimateInstance> destroyEstimateInstanceResult(
			@ApiParam("评估实例id") @RequestParam("instance_id") String instanceId) {
		EstimateInstanceContext estimateInstanceContext = new EstimateInstanceContext();
		estimateInstanceContext.setEstimateInstanceId(instanceId);
		EstimateInstance estimateInstance = estimateInstanceService
				.destroyEstimateInstanceResult(estimateInstanceContext);
		BaseRsp<EstimateInstance> rsp = new BaseRsp<>();
		rsp.setData(estimateInstance);
		return rsp;
	}

	/**
	 * 根据评估id，打开评估页面
	 * */
	@GetMapping("/get")
	@ApiOperation("查询评估对应的正在进行中的实例")
	public BaseRsp<EstimateInstanceViewBO> getEstimateInstance(
			@ApiParam("评估实例编号") @RequestParam("estimate_id") String estimateId) {
		EstimateInstanceViewBO estimateInstanceViewBO = estimateInstanceService
				.getEstimateInstance(estimateId);
		BaseRsp<EstimateInstanceViewBO> rsp = new BaseRsp<EstimateInstanceViewBO>();
		rsp.setData(estimateInstanceViewBO);
		return rsp;
	}

	/**
	 * 查看评估实例的变量项格式化数据
	 * 1、根据评估实例id查到评估id；
	 * 2、根据评估id查询到变量项；
	 * 3、根据1、2的信息查到变量项格式化数据
	 * */
	@GetMapping("/review/formal/data")
	@ApiOperation("查看评估实例的变量项格式化数据")
	public BaseRsp<List<EstimateReviewBo>> reviewEstimateInstanceFormalData(
			@ApiParam("评估实例id") @RequestParam("instance_id") String instanceId) {

		List<EstimateReviewBo> retDate = estimateInstanceService
				.reviewEstimateInstanceFormalData(instanceId);

		BaseRsp<List<EstimateReviewBo>> rsp = new BaseRsp<>();
		rsp.setData(retDate);
		return rsp;
	}

	@GetMapping("/review/model/data")
	@ApiOperation("查看某变量项详细的匹配数据")
	public BaseRsp<EstimateReviewBo> reviewEstimateInstanceModelData(
			@ApiParam("评估实例id") @RequestParam("instance_id") String instanceId,
			@ApiParam("变量项id") @RequestParam("variable_id") String variableId) {

		EstimateReviewBo retDate = estimateInstanceService
				.reviewEstimateInstanceModelData(instanceId, variableId);

		BaseRsp<EstimateReviewBo> rsp = new BaseRsp<>();
		rsp.setData(retDate);
		return rsp;
	}

	@GetMapping("/review/origin/data")
	@ApiOperation("查看某变量项详细的原始数据")
	public BaseRsp<ListRsp<ProbeOriginData>>
			reviewEstimateInstanceOriginData(@Valid EstimateOriginDataReq estimateOriginDataReq) {

		Page page = estimateOriginDataReq.toPageParam();
		ListRsp<ProbeOriginData> retDate = estimateInstanceService.reviewEstimateInstanceOriginData(
				estimateOriginDataReq.getInstanceId(), estimateOriginDataReq.getVariableId(), page);

		return new BaseRsp<>(retDate);
	}

	/**
	 * 查看评估记录
	 * 根据评估id找到所有对应的评估实例测试记录；
	 * */
	@GetMapping("/results")
	@ApiOperation("查看评估结果历史记录")
	public BaseRsp<ListRsp<EstimateInstanceBO>>
			reviewSavedResults(@Valid EstimateInstanceResultReq resultReq) {
		Page pageBase = resultReq.toPageParam();
		ListRsp<EstimateInstanceBO> pageInfoList = estimateInstanceService
				.reviewEstimateInstanceResults(resultReq.getEstimateId(), pageBase);

		return new BaseRsp<>(pageInfoList);
	}

	/**
	 * 计算评估结果
	 * */
	@ApiOperation("计算评估结果")
	@GetMapping("/calculate")
	public BaseRsp<String>
			calculateAndSave(@ApiParam("评估实例编号") @RequestParam("instance_id") String instanceId) {
		estimateInstanceService.calculateAndSave(instanceId);
		return new BaseRsp();
	}
}
